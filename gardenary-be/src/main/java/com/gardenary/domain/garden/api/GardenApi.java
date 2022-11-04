package com.gardenary.domain.garden.api;

import com.gardenary.domain.garden.dto.GardenDto;
import com.gardenary.domain.garden.dto.GardenUserIdDto;
import com.gardenary.domain.garden.response.GardenListResponseDto;
import com.gardenary.domain.garden.service.GardenService;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.common.response.MessageResponse;
import com.gardenary.global.config.security.UserDetail;
import com.gardenary.global.properties.ResponseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/garden")
@RequiredArgsConstructor
public class GardenApi {

    private final GardenService gardenService;
    private final ResponseProperties responseProperties;

    @GetMapping("")
    public ResponseEntity<DtoResponse<GardenListResponseDto>> getGardenInfo(@RequestBody GardenUserIdDto gardenUserIdDto) {
        GardenListResponseDto result = gardenService.getGardenInfo(gardenUserIdDto);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
        }
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> modifyGarden(@AuthenticationPrincipal UserDetail userDetail, @RequestBody List<GardenDto> gardenDtoList) {
        User user = userDetail.getUser();
        boolean result = gardenService.modifyGarden(user, gardenDtoList);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of(HttpStatus.OK, responseProperties.getSuccess()));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of(HttpStatus.OK, responseProperties.getFail()));
        }
    }

}
