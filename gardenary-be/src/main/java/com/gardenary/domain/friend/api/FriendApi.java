package com.gardenary.domain.friend.api;

import com.gardenary.domain.friend.dto.response.FriendResponseDto;
import com.gardenary.domain.friend.service.FriendService;
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

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
@Slf4j
public class FriendApi {
    private final FriendService friendService;
    private final ResponseProperties responseProperties;

    @PostMapping("")
    public ResponseEntity<MessageResponse> createFriend(
            @AuthenticationPrincipal UserDetail userDetail,
            @RequestParam(name = "encryptUserId") String encryptUserId) {
        boolean result = friendService.createFriend(userDetail.getUser(), encryptUserId);
        String str = result ? responseProperties.getSuccess() : responseProperties.getFail();

        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of(HttpStatus.OK, str));
    }

    @DeleteMapping("")
    public ResponseEntity<MessageResponse> deleteFriend(
            @AuthenticationPrincipal UserDetail userDetail,
            @RequestParam(name = "friendId") int friendId) {
        boolean result = friendService.deleteFriend(userDetail.getUser(), friendId);
        String str = result ? responseProperties.getSuccess() : responseProperties.getFail();

        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of(HttpStatus.OK, str));
    }

    @GetMapping("/search")
    public ResponseEntity<DtoResponse<FriendResponseDto>> searchFriend(
            @AuthenticationPrincipal UserDetail userDetail,
            @RequestParam(name = "nickname") String nickname) {
        FriendResponseDto result = friendService.findUser(userDetail.getUser(), nickname);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }

    @GetMapping("/following")
    public ResponseEntity<DtoResponse<List<FriendResponseDto>>> getFollowingList(
            @AuthenticationPrincipal UserDetail userDetail) {
        List<FriendResponseDto> result = friendService.getFollowingList(userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
    }

    @GetMapping("/follower")
    public ResponseEntity<DtoResponse<List<FriendResponseDto>>> getFollowerList(
            @AuthenticationPrincipal UserDetail userDetail) {
        List<FriendResponseDto> result = friendService.getFollowerList(userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
    }

}
