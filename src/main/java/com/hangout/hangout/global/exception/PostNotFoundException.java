package com.hangout.hangout.global.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super();
    }

    public PostNotFoundException(String message) {
        super(message);
    }
    public PostNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
}
