package com.codewithmosh.store.exceptions;

public class ForbiddenResourceException extends RuntimeException {
    public ForbiddenResourceException(String message) {
        super(message);
    }
}
