package com.hangout.hangout.global.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    protected org.springframework.http.ResponseEntity<String> handleException(BaseException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getResponseType().getStatus()).body(e.getResponseType().getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public org.springframework.http.ResponseEntity<String> fileSizeLimitExceededException(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

}
