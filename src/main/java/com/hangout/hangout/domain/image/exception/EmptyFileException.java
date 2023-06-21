package com.hangout.hangout.domain.image.exception;

import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.BaseException;

public class EmptyFileException extends BaseException {
    public EmptyFileException(ResponseType responseType) {
        super(responseType);
    }
}
