package com.novica.Connect4Service.aspect;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(com.novica.Connect4Service.exception.Alert.class)
    public org.springframework.http.ResponseEntity<?> handleAlert(com.novica.Connect4Service.exception.Alert e) {
        return org.springframework.http.ResponseEntity.badRequest().body(e.getMessage());
    }
}
