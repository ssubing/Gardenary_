package com.gardenary.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GrowingPlantErrorCode implements ErrorCode {
    GROWING_PLANT_NOT_FOUND(6000, HttpStatus.NOT_FOUND, "Growing Plant is Not Found");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
