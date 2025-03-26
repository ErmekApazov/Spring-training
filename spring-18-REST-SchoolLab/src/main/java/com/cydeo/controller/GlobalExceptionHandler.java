package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseWrapper<String>> handleException(Exception ex) {
        ResponseWrapper<String> errorResponse = new ResponseWrapper<>(
                false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
