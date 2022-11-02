package com.gardenary.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProfileErrorCode implements ErrorCode {
    PROFILE_NOT_FOUND(3000, HttpStatus.NOT_FOUND, "Profile is Not Found");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
