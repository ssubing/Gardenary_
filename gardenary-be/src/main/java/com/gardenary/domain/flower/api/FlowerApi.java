package com.gardenary.domain.flower.api;

import com.gardenary.domain.flower.dto.*;
import com.gardenary.domain.flower.response.AnswerCompleteResponseDto;
import com.gardenary.domain.flower.response.FlowerListResponseDto;
import com.gardenary.domain.flower.response.MyFlowerOnlyIdResponseDto;
import com.gardenary.domain.flower.response.QuestionAnswerListResponseDto;
import com.gardenary.domain.flower.service.FlowerService;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.config.security.UserDetail;
import com.gardenary.global.properties.ResponseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flower")
@RequiredArgsConstructor
public class FlowerApi {

    private final FlowerService flowerService;
    private final ResponseProperties responseProperties;


    //추후 user쪽 추가, 경험치 return으로 변겅
    @PostMapping("/answer")
    public ResponseEntity<DtoResponse> createAnswer(@RequestBody QuestionAnswerDto questionAnswerDto, @AuthenticationPrincipal UserDetail userDetail) {
        AnswerCompleteResponseDto result = flowerService.createAnswer(userDetail.getUser(), questionAnswerDto);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
        }
    }
    @GetMapping("/answer/{myFlowerId}")
    public ResponseEntity<DtoResponse<QuestionAnswerListResponseDto>> getOneFlowerAnswerList(@PathVariable int myFlowerId, @AuthenticationPrincipal UserDetail userDetail) {;
        QuestionAnswerListResponseDto result = flowerService.getOneFlowerAnswerList(userDetail.getUser(), myFlowerId);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<DtoResponse<QuestionAnswerListResponseDto>> getAllFlowerAnswerList(@AuthenticationPrincipal UserDetail userDetail) {
        QuestionAnswerListResponseDto result = flowerService.getAllFlowerAnswerList(userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
        }
    }

    @PostMapping("")
    public ResponseEntity<DtoResponse> createNewFlower(@AuthenticationPrincipal UserDetail userDetail) {
        MyFlowerOnlyIdResponseDto result = flowerService.createNewFlower(userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
        }
    }

    @GetMapping("")
    public ResponseEntity<DtoResponse<FlowerListResponseDto>> getFlowerList (@AuthenticationPrincipal UserDetail userDetail) {
        FlowerListResponseDto result = flowerService.getFlowerList(userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
        }
    }
}
