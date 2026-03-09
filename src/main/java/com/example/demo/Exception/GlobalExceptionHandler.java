package com.example.demo.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ErrorReponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppExceptions(AppException ex) {
        ErrorReponseDTO errorResponse = new ErrorReponseDTO();
        errorResponse.setErrorMessage(ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
}
