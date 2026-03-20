package com.sammyCee.exceptions;

public class GatePassDoesNotExistException extends RuntimeException {
    public GatePassDoesNotExistException(String message) {
        super(message);
    }
}
