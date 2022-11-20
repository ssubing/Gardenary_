package com.gardenary.domain.current.api;

import com.gardenary.domain.current.dto.response.GrowingPlantResponseDto;
import com.gardenary.domain.current.service.CurrentService;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.config.security.UserDetail;
import com.gardenary.global.properties.ResponseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/current")
@RequiredArgsConstructor
public class CurrentApi {

    private final CurrentService currentService;
    private final ResponseProperties responseProperties;

    @GetMapping("")
    public ResponseEntity<DtoResponse<GrowingPlantResponseDto>> getCurrentInfo(@AuthenticationPrincipal UserDetail userDetail) {
        GrowingPlantResponseDto result = currentService.getCurrentInfo(userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
        }
    }
    @GetMapping("/test")
    public void test(@AuthenticationPrincipal UserDetail userDetail) {
        currentService.test(userDetail.getUser());
        return;

    }
}
