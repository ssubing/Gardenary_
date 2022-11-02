package com.gardenary.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AvatarErrorCode implements ErrorCode {
    AVATAR_NOT_FOUND(8000, HttpStatus.NOT_FOUND, "Avatar is Not Found"),
    MYAVATAR_NOT_FOUND(8001, HttpStatus.NOT_FOUND, "MyAvatar is Not Found");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
