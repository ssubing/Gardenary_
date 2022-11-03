package com.gardenary.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ItemErrorCode implements ErrorCode {
    ITEM_NOT_FOUND(1000, HttpStatus.NOT_FOUND, "Item is Not Found");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
