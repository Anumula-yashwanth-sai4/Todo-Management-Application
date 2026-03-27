package com.spring.backend.exceptions;

public class TaskExistsException extends ResourceException {
    public TaskExistsException(String message) {
        super(message);
    }
}
