package com.gardenary.domain.friend.api;

import com.gardenary.domain.friend.service.FriendService;
import com.gardenary.global.common.properties.ResponseProperties;
import com.gardenary.global.common.response.MessageResponse;
import com.gardenary.global.config.security.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
@Slf4j
public class FriendApi {
    private final FriendService friendService;
    private final ResponseProperties responseProperties;

    @PostMapping("")
    public ResponseEntity<MessageResponse> createFriend(
            @AuthenticationPrincipal UserDetail userDetails) {
        return null;
    }
}
