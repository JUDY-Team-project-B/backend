package com.hangout.hangout.global.exception;

import com.hangout.hangout.global.error.ResponseType;

public class InvalidFormatException extends BaseException {

    public InvalidFormatException(ResponseType responseType) {
        super(responseType);
    }


}
