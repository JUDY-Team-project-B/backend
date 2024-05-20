package com.hangout.hangout.global.exception;

import com.hangout.hangout.global.error.ResponseType;

public class AuthException extends BaseException {
    public AuthException(ResponseType responseType) {
        super(responseType);
    }
}
