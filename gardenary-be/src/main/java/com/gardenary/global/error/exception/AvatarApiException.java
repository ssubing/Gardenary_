package com.gardenary.global.error.exception;

import com.gardenary.global.error.model.ErrorCode;
import lombok.Getter;

@Getter
public class AvatarApiException extends RuntimeException{
    private final ErrorCode errorCode;
    public AvatarApiException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
