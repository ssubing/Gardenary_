package com.gardenary.domain.user.api;

import com.gardenary.domain.auth.dto.response.AuthResponseDto;
import com.gardenary.domain.auth.service.AuthService;
import com.gardenary.domain.avatar.dto.response.AvatarResponseDto;
import com.gardenary.domain.user.entity.User;
import com.gardenary.domain.user.service.SocialService;
import com.gardenary.domain.user.service.UserService;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.config.security.UserDetail;
import com.gardenary.global.properties.ResponseProperties;
import com.gardenary.global.properties.SocialProperties;
import com.gardenary.global.util.CookieUtil;
import com.github.scribejava.apis.KakaoApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserApi {

    private final SocialProperties socialProperties;
    private final ResponseProperties responseProperties;

    private final SocialService socialService;

    private final UserService userService;

    private final AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<DtoResponse<AuthResponseDto>> kakaoRedirect(@RequestParam("code") String code, HttpServletResponse httpResponse) throws IOException, ExecutionException, InterruptedException, ParseException {

        OAuth20Service kService = new ServiceBuilder(socialProperties.getKakaoClientId())
                .apiSecret(socialProperties.getKakaoClientSecret())
                .build(KakaoApi.instance());

        OAuth2AccessToken accessToken = kService.getAccessToken(code);
        OAuthRequest request = new OAuthRequest(Verb.GET, socialProperties.getKakaoApiUrl());
        kService.signRequest(accessToken, request);
        Response response = kService.execute(request);

        try {
            JSONParser parser = new JSONParser();
            JSONObject jobj = (JSONObject) parser.parse(response.getBody());

            String kakaoId = jobj.get("id").toString();

            User user = socialService.checkSignUp(kakaoId);
            AuthResponseDto authResponseDto = authService.signIn(user);
            CookieUtil.setRefreshTokenCookie(httpResponse, authResponseDto.getRefreshToken());

            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), authResponseDto));

        } catch (NullPointerException | org.json.simple.parser.ParseException e) {
            log.error("NullPointerException");
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        }
    }

    @GetMapping("")
    public ResponseEntity<DtoResponse<List<AvatarResponseDto>>> getMyPage(@AuthenticationPrincipal UserDetail userDetail){

        List<AvatarResponseDto> result = userService.getMyPage(userDetail.getUser());

        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }
}
