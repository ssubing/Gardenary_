package com.gardenary.global.error.exception;

import com.gardenary.domain.current.entity.GrowingPlant;
import com.gardenary.global.error.model.ErrorCode;
import lombok.Getter;

@Getter
public class GrowingPlantApiException extends RuntimeException{
    private final ErrorCode errorCode;
    public GrowingPlantApiException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
