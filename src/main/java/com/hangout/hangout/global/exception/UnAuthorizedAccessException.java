package com.hangout.hangout.global.exception;

import com.hangout.hangout.global.error.ResponseType;

public class UnAuthorizedAccessException extends BaseException{
    public UnAuthorizedAccessException(ResponseType responseType) {
        super(responseType);
    }
}
