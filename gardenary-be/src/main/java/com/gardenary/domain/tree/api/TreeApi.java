package com.gardenary.domain.tree.api;

import com.gardenary.domain.tree.dto.request.DiaryRequestDto;
import com.gardenary.domain.tree.dto.request.LocalDateTimeDto;
import com.gardenary.domain.tree.dto.response.*;
import com.gardenary.domain.tree.service.TreeService;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.common.response.MessageResponse;
import com.gardenary.global.config.security.UserDetail;
import com.gardenary.global.properties.ResponseProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tree")
@RequiredArgsConstructor
@Slf4j
public class TreeApi {
    private final TreeService treeService;
    private final ResponseProperties responseProperties;

    @GetMapping("")
    public ResponseEntity<DtoResponse<List<TreeResponseDto>>> getMyTreeList(
            @AuthenticationPrincipal UserDetail userDetail) {
        List<TreeResponseDto> result = treeService.getMyTreeList(userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }

    @PostMapping("/diary/date")
    public ResponseEntity<DtoResponse<List<DiaryResponseDto>>> getDiary(
            @RequestBody LocalDateTimeDto localDateTimeDto,
            @AuthenticationPrincipal UserDetail userDetail) {
        List<DiaryResponseDto> result = treeService.getDateDiaryList(localDateTimeDto.getDate(), userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }

    @GetMapping("/diary/{myTreeId}")
    public ResponseEntity<DtoResponse<DiaryListResponseDto>> getDiaryList(
            @PathVariable("myTreeId") int myTreeId,
            @AuthenticationPrincipal UserDetail userDetail) {
        DiaryListResponseDto result = treeService.getDiaryList(myTreeId, userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }

    @PostMapping("/diary")
    public ResponseEntity<DtoResponse<MakeDiaryResponseDto>> createDiary(
            @AuthenticationPrincipal UserDetail userDetail,
            @RequestBody DiaryRequestDto diaryRequestDto) {
        MakeDiaryResponseDto result = treeService.createDiary(userDetail.getUser(), diaryRequestDto);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }

    @PostMapping("")
    public ResponseEntity<DtoResponse<CompleteTreeInfoResponseDto>> createMyTree(
            @AuthenticationPrincipal UserDetail userDetail) {
        CompleteTreeInfoResponseDto result = treeService.createMyTree(userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }

}
