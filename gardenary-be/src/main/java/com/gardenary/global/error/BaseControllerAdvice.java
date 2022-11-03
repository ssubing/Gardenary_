package com.gardenary.global.error;

import com.gardenary.global.common.response.ErrorResponse;
import com.gardenary.global.common.response.MessageResponse;
import com.gardenary.global.error.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class BaseControllerAdvice {

    @ExceptionHandler(FriendApiException.class)
    public ResponseEntity<ErrorResponse> FriendApiException(FriendApiException e, HttpServletRequest req) {
        log.error(req.getRequestURI());
        log.error(e.getClass().getCanonicalName());
        e.printStackTrace();
        log.error(e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(e.getErrorCode()));
    }

    @ExceptionHandler(AvatarApiException.class)
    public ResponseEntity<ErrorResponse> AvatarApiException(AvatarApiException e, HttpServletRequest req) {
        log.error(req.getRequestURI());
        log.error(e.getClass().getCanonicalName());
        e.printStackTrace();
        log.error(e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(e.getErrorCode()));
    }

    @ExceptionHandler(UserApiException.class)
    public ResponseEntity<ErrorResponse> userApiException(UserApiException e, HttpServletRequest req) {
        log.error(req.getRequestURI());
        log.error(e.getClass().getCanonicalName());
        e.printStackTrace();
        log.error(e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(e.getErrorCode()));
    }

    @ExceptionHandler(TreeApiException.class)
    public ResponseEntity<ErrorResponse> treeApiException(TreeApiException e, HttpServletRequest req) {
        log.error(req.getRequestURI());
        log.error(e.getClass().getCanonicalName());
        e.printStackTrace();
        log.error(e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(e.getErrorCode()));
    }

    @ExceptionHandler(ProfileApiException.class)
    public ResponseEntity<ErrorResponse> profileApiException(ProfileApiException e, HttpServletRequest req) {
        log.error(req.getRequestURI());
        log.error(e.getClass().getCanonicalName());
        e.printStackTrace();
        log.error(e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(e.getErrorCode()));
    }

    @ExceptionHandler(GrowingPlantApiException.class)
    public ResponseEntity<ErrorResponse> growingPlantApiException(GrowingPlantApiException e, HttpServletRequest req) {
        log.error(req.getRequestURI());
        log.error(e.getClass().getCanonicalName());
        e.printStackTrace();
        log.error(e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(e.getErrorCode()));
    }

    @ExceptionHandler(FlowerApiException.class)
    public ResponseEntity<ErrorResponse> flowerApiException(FlowerApiException e, HttpServletRequest req) {
        log.error(req.getRequestURI());
        log.error(e.getClass().getCanonicalName());
        e.printStackTrace();
        log.error(e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(e.getErrorCode()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MessageResponse> dataIntegrityViolationException(Exception e, HttpServletRequest req) {
        log.debug("Data Integrity Violation");
        log.error(req.getRequestURI());
        log.error(e.getClass().getCanonicalName());
        e.printStackTrace();
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MessageResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Data Integrity Violation"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<MessageResponse> missingServletRequestParameterException(Exception e, HttpServletRequest req) {
        log.debug("Missing Parameter");
        log.error(req.getRequestURI());
        log.error(e.getClass().getCanonicalName());
        e.printStackTrace();
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageResponse.of(HttpStatus.BAD_REQUEST, "Missing Parameter"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageResponse> httpMessageNotReadableException(Exception e, HttpServletRequest req) {
        log.debug("HttpMessageNotReadableException");
        log.error(req.getRequestURI());
        log.error(e.getClass().getCanonicalName());
        e.printStackTrace();
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageResponse.of(HttpStatus.BAD_REQUEST, "Bad Request"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> unknownException(Exception e, HttpServletRequest req) {
        log.debug("UNKNOWN ERROR");
        log.error(req.getRequestURI());
        log.error(e.getClass().getCanonicalName());
        e.printStackTrace();
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MessageResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Error"));
    }

}

