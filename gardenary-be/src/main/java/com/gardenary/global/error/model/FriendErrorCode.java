package com.gardenary.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FriendErrorCode implements ErrorCode {
    FRIEND_NOT_FOUND(4000, HttpStatus.NOT_FOUND, "Friend is Not Found");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
