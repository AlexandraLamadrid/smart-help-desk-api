package com.company.smarthelpdesk.domain.exception;
public final class InvalidValueException
        extends IllegalArgumentException {
    public InvalidValueException(String message) {
        super(message);
    }
}