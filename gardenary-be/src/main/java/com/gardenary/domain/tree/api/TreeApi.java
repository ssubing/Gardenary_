package com.gardenary.domain.tree.api;

import com.gardenary.domain.tree.service.TreeService;
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
@RequestMapping("/tree")
@RequiredArgsConstructor
@Slf4j
public class TreeApi {
    private final TreeService treeService;
    private final ResponseProperties responseProperties;

    @PostMapping("")
    public ResponseEntity<MessageResponse> createMyTree(
            @AuthenticationPrincipal UserDetail userDetails) {
        return null;
    }
}
