package com.hangout.hangout.global.handler;


import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected org.springframework.http.ResponseEntity<String> handleException(BaseException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getResponseType().getStatus())
            .body(e.getResponseType().getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public org.springframework.http.ResponseEntity<String> fileSizeLimitExceededException(
        MaxUploadSizeExceededException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public com.hangout.hangout.global.error.ResponseEntity handleUserEmailException() {
        return com.hangout.hangout.global.error.ResponseEntity.failureResponse(
            ResponseType.USER_NOT_EXIST_EMAIL);
    }

    @ExceptionHandler(AuthenticationException.class)
    public com.hangout.hangout.global.error.ResponseEntity handleAuthentication(
        AuthenticationException e) {
        if (e.getClass().equals(BadCredentialsException.class)) {
            return com.hangout.hangout.global.error.ResponseEntity.failureResponse(
                ResponseType.INVALID_PASSWORD);
        } else {
            return com.hangout.hangout.global.error.ResponseEntity.failureResponse(
                HttpStatus.UNAUTHORIZED.toString(), e.getMessage()
            );
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public com.hangout.hangout.global.error.ResponseEntity handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        return com.hangout.hangout.global.error.ResponseEntity.failureResponse(
            HttpStatus.BAD_REQUEST.toString(),
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

}
