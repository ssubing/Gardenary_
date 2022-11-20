package com.gardenary.global.error.exception;

import com.gardenary.global.error.model.ErrorCode;
import lombok.Getter;

@Getter
public class FlowerApiException extends RuntimeException{
    private final ErrorCode errorCode;
    public FlowerApiException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
