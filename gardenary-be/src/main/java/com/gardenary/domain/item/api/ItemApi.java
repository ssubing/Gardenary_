package com.gardenary.domain.item.api;

import com.gardenary.domain.item.dto.response.ItemResponseDto;
import com.gardenary.domain.item.service.ItemService;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.config.security.UserDetail;
import com.gardenary.global.properties.ResponseProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/item")
public class ItemApi {

    private final ResponseProperties responseProperties;
    private final ItemService itemService;


    @PostMapping("")
    public ResponseEntity<DtoResponse<ItemResponseDto>> createItem(@AuthenticationPrincipal UserDetail userDetail) {

        ItemResponseDto result = itemService.createItem(userDetail.getUser());

        if (result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }
}
