package com.gardenary.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FlowerErrorCode implements ErrorCode {
    FLOWER_NOT_FOUND(5000, HttpStatus.NOT_FOUND, "Flower is Not Found"),
    MY_FLOWER_NOT_FOUND(5001, HttpStatus.NOT_FOUND, "My Flower is Not Found"),
    ANSWER_NOT_FOUND(5002, HttpStatus.NOT_FOUND, "Answer is Not Found"),
    NOT_ENOUGH_EXP(5003, HttpStatus.BAD_REQUEST,"Exp is Not Enough"),
    TODAY_ALREADY_WRITE(5004, HttpStatus.ALREADY_REPORTED,"Today this user already wrote the answer"),
    CONTENT_LENGTH_IS_OVER(5005, HttpStatus.BAD_REQUEST,"Content's length is too long");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
