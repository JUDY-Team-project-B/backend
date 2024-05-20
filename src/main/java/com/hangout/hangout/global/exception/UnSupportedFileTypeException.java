package com.hangout.hangout.global.exception;

import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.BaseException;

public class UnSupportedFileTypeException extends BaseException {
    public UnSupportedFileTypeException(ResponseType responseType) {
        super(responseType);
    }
}
