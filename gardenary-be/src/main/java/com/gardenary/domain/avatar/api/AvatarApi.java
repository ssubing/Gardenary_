package com.gardenary.domain.avatar.api;

import com.gardenary.domain.avatar.dto.response.AvatarListResponseDto;
import com.gardenary.domain.avatar.service.AvatarService;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.config.security.UserDetail;
import com.gardenary.global.properties.ResponseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avatar")
@RequiredArgsConstructor
public class AvatarApi {

    private final ResponseProperties responseProperties;
    private final AvatarService avatarService;

    @PostMapping("/{type}")
    public ResponseEntity<DtoResponse<AvatarListResponseDto>> getNewAvatar(@AuthenticationPrincipal UserDetail userDetail, @PathVariable int type) {
        AvatarListResponseDto result = avatarService.getNewAvatar(userDetail.getUser(), type);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
        }
    }
}
