package com.gardenary.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TreeErrorCode implements ErrorCode {
    MY_TREE_NOT_FOUND(7000, HttpStatus.NOT_FOUND, "My Tree is Not Found"),
    TREE_NOT_FOUND(7001, HttpStatus.NOT_FOUND, "Tree is Not Found"),
    DIARY_NOT_FOUND(7002, HttpStatus.NOT_FOUND, "Diary is Not Found"),
    NOT_ENOUGH_EXP(7003, HttpStatus.BAD_REQUEST,"Exp is Not Enough");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
