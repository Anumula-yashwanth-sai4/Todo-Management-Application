package com.spring.backend.exceptions;

public class TaskNotFoundException extends ResourceException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
