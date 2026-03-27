package com.spring.backend.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<ResponseMessage<Void>> handle(ResourceException e) {
        return ResponseEntity.status(400).body(new ResponseMessage<>(e.getMessage(),400,null ));
    }
}