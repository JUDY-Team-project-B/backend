package com.hangout.hangout.global.exception;

import com.hangout.hangout.global.error.ResponseType;

public class BadRequestException extends BaseException {

    public BadRequestException(ResponseType responseType) {
        super(responseType);
    }
}
