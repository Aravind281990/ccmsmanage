package com.ccms.service.exception;

public class InvalidRandomOperationException extends RuntimeException {

    // Constructor that accepts a message
    public InvalidRandomOperationException(String message) {
        super(message);
    }

    // Constructor that accepts a message and cause
    public InvalidRandomOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}