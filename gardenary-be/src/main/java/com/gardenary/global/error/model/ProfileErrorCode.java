package com.gardenary.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProfileErrorCode implements ErrorCode {
    PROFILE_NOT_FOUND(3000, HttpStatus.NOT_FOUND, "Profile is Not Found"),
    NICKNAME_NOT_FOUND(3001, HttpStatus.NO_CONTENT, "Nickname is Not Found"),
    NICKNAME_ALREADY_EXISTS(3002, HttpStatus.NOT_ACCEPTABLE, "Nickname already exists");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
