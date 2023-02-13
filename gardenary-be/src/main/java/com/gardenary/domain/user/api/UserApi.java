package com.gardenary.domain.user.api;

import com.gardenary.domain.auth.dto.LoginRequestDto;
import com.gardenary.domain.auth.dto.response.AuthResponseDto;
import com.gardenary.domain.auth.service.AuthService;
import com.gardenary.domain.user.entity.User;
import com.gardenary.domain.user.service.SocialService;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.properties.ResponseProperties;
import com.gardenary.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserApi {

    private final ResponseProperties responseProperties;
    private final SocialService socialService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<DtoResponse<AuthResponseDto>> refreshToken(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpResponse) {
        User user = socialService.checkSignUp(loginRequestDto.getKakaoId());
        AuthResponseDto authResponseDto = authService.signIn(user);
        CookieUtil.setRefreshTokenCookie(httpResponse, authResponseDto.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), authResponseDto));
    }

}
