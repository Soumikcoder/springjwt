package com.example.demo.Exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    HttpStatus httpStatus;
    LocalDate timestamp;
    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.timestamp = LocalDate.now();
    }
}
