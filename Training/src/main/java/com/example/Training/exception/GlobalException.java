package com.example.Training.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionhandle(Exception ex){
        return ResponseEntity.status(500).body(ex.getMessage());
    }
}
