package com.gardenary.domain.auth.service;


import com.gardenary.domain.auth.dto.response.AuthResponseDto;
import com.gardenary.domain.auth.dto.response.RefreshResponseDto;
import com.gardenary.domain.profile.entity.Profile;
import com.gardenary.domain.profile.repository.ProfileRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.domain.user.repository.UserRepository;
import com.gardenary.global.config.security.JwtProvider;
import com.gardenary.global.error.exception.UserApiException;
import com.gardenary.global.error.model.ProfileErrorCode;
import com.gardenary.global.error.model.UserErrorCode;
import com.gardenary.global.util.CookieUtil;
import com.gardenary.infra.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ProfileRepository profileRepository;

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final RedisService redisService;

    @Override
    public AuthResponseDto signIn(User user) {
        Profile profile = profileRepository.findByUser(user);

        if (profile == null) {
            throw new UserApiException(ProfileErrorCode.PROFILE_NOT_FOUND);
        }

        String nickname = profile.getNickname();
        String accessToken = jwtProvider.generateAccessToken(user.getKakaoId());
        String refreshToken = jwtProvider.generateRefreshToken(user.getKakaoId());

        return new AuthResponseDto(accessToken, refreshToken, nickname);
    }

    @Override
    public void signOut(HttpServletRequest req) {
        String refreshToken = CookieUtil.searchCookie(req, "refreshToken");
        String accessToken = jwtProvider.resolveToken(req);

        redisService.deleteKey(refreshToken);
        redisService.setStringValueAndExpire(accessToken, "blacklist", jwtProvider.getAccessTokenExpireTime());
    }


    @Override
    public RefreshResponseDto refresh(String refreshToken) {
        boolean validation = jwtProvider.validateRefreshToken(refreshToken);
        String kakaoId = redisService.getStringValue(refreshToken);
        if (validation && kakaoId != null) {
            userRepository.findByKakaoId(kakaoId).orElseThrow(
                    () -> new UserApiException(UserErrorCode.USER_NOT_FOUND)
            );
            return RefreshResponseDto.builder().accessToken(jwtProvider.generateAccessToken(kakaoId)).build();
        } else {
            return null;
        }
    }


}
