package com.namecard.exception;

public class DuplicateTagNameException extends RuntimeException {
    public DuplicateTagNameException(String message) {
        super(message);
    }

    public DuplicateTagNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
