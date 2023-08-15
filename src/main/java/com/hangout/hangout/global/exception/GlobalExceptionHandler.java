package com.hangout.hangout.global.exception;

import static com.hangout.hangout.global.error.ResponseEntity.failureResponse;

import com.hangout.hangout.global.error.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<HttpStatus> handleException(BaseException e) {
        log.error(e.getMessage(), e);
        return failureResponse(e.getResponseType());
    }
}
