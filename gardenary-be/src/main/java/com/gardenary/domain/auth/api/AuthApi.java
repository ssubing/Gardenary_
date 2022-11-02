package com.gardenary.domain.auth.api;


import com.gardenary.domain.auth.dto.response.AuthResponseDto;
import com.gardenary.domain.auth.service.AuthService;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.common.response.MessageResponse;
import com.gardenary.global.properties.ResponseProperties;
import com.gardenary.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;
    private final ResponseProperties responseProperties;

    @PostMapping("/refresh")
    public ResponseEntity<DtoResponse<AuthResponseDto>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.searchCookie(request, "refreshToken");
        if(refreshToken != null && !refreshToken.equals("")) {
            AuthResponseDto authResponseDto = authService.refresh(refreshToken);

            if(authResponseDto == null) {
                CookieUtil.deleteRefreshTokenCookie(response);
                return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), authResponseDto));
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        }
    }


    @DeleteMapping("")
    public ResponseEntity<MessageResponse> signOut(HttpServletRequest request, HttpServletResponse response){
        authService.signOut(request);
        CookieUtil.deleteRefreshTokenCookie(response);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of(HttpStatus.OK, responseProperties.getSuccess()));
    }

}
