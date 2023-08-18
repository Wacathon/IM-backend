package com.namecard.exception;

public class QuestionLimitException extends RuntimeException {

    public QuestionLimitException(String message) {
        super(message);
    }

    public QuestionLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
