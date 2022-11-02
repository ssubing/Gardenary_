package com.gardenary.domain.garden.api;

import com.gardenary.domain.flower.response.QuestionAnswerListResponseDto;
import com.gardenary.domain.garden.response.GardenListResponseDto;
import com.gardenary.domain.garden.service.GardenService;
import com.gardenary.domain.user.entity.Role;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.properties.ResponseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/garden")
@RequiredArgsConstructor
public class GardenApi {

    private final GardenService gardenService;
    private final ResponseProperties responseProperties;

    @GetMapping("")
    public ResponseEntity<DtoResponse<GardenListResponseDto>> getGardenInfo(@RequestParam String userId) {
        GardenListResponseDto result = gardenService.getGardenInfo(userId);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
        }
    }
}
