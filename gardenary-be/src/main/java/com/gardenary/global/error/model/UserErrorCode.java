package com.gardenary.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(2000, HttpStatus.NOT_FOUND, "User is Not Found");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
