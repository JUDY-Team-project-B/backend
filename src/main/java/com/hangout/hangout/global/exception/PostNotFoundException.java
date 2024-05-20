package com.hangout.hangout.global.exception;

import com.hangout.hangout.global.error.ResponseType;

public class PostNotFoundException extends BaseException {

    public PostNotFoundException(ResponseType responseType) {
        super(responseType);
    }

}
