package com.gardenary.domain.auth.api;


import com.gardenary.domain.auth.dto.response.RefreshResponseDto;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;
    private final ResponseProperties responseProperties;

    @PostMapping("/refresh")
    public ResponseEntity<DtoResponse<RefreshResponseDto>> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        Enumeration headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String name = (String)headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + " : " + value);
        }

        String refreshToken = CookieUtil.searchCookie(request, "refreshToken");

        if (refreshToken != null && !refreshToken.equals("")) {
            RefreshResponseDto refreshResponseDto = authService.refresh(refreshToken);

            if (refreshResponseDto == null) {
                CookieUtil.deleteRefreshTokenCookie(response);
                return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), refreshResponseDto));
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        }
    }


    @DeleteMapping("")
    public ResponseEntity<MessageResponse> signOut(HttpServletRequest request, HttpServletResponse response) {
        authService.signOut(request);
        CookieUtil.deleteRefreshTokenCookie(response);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of(HttpStatus.OK, responseProperties.getSuccess()));
    }

}
