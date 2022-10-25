package com.gardenary.global.error.model;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    int getCode();
    HttpStatus getHttpStatus();
    String getMessage();
}
