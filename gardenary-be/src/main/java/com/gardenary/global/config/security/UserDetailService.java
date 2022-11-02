package com.gardenary.global.config.security;

import com.gardenary.domain.user.entity.User;
import com.gardenary.domain.user.repository.UserRepository;
import com.gardenary.global.error.exception.UserApiException;
import com.gardenary.global.error.model.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetail loadUserByUsername(String kakaoId) throws UserApiException {
        final User user = userRepository.findByKakaoId(kakaoId).orElseThrow(
                () -> new UserApiException(UserErrorCode.USER_NOT_FOUND)
        );
        return new UserDetail(user);
    }
}

