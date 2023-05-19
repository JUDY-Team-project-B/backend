package com.hangout.hangout.global.exception;

import com.hangout.hangout.global.error.ResponseType;

public class StatusNotFoundException extends BaseException {
    public StatusNotFoundException(ResponseType responseType) {
        super(responseType);
    }


}
