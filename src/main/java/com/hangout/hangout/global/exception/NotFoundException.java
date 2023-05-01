package com.hangout.hangout.global.exception;

import com.hangout.hangout.global.error.ResponseType;

public class NotFoundException extends BaseException {

    public NotFoundException(ResponseType responseType) {
        super(responseType);
    }

}