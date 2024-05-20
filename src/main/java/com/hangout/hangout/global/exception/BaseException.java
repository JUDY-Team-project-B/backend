package com.hangout.hangout.global.exception;

import com.hangout.hangout.global.error.ResponseType;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ResponseType responseType;

    public BaseException(ResponseType responseType) {
        this.responseType = responseType;
    }
}