package com.personalapp.exception;

public class NikAlreadyExistException extends RuntimeException{
    public NikAlreadyExistException(String message) {
        super(message);
    }

    public NikAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
