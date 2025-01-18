package com.example.zajecia2.exceptions;

public class CantDeleteAuto_NotFoundException extends RuntimeException{
    public CantDeleteAuto_NotFoundException(String message) {
        super(message);
    }

    public CantDeleteAuto_NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
