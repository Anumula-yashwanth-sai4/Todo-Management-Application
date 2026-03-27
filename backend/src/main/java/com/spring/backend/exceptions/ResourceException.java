package com.spring.backend.exceptions;

public class ResourceException extends RuntimeException {
    public ResourceException(String message) {
        super(message);
    }
}
