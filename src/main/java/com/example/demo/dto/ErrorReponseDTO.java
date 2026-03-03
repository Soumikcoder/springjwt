package com.example.demo.dto;

import java.time.LocalDate;

public class ErrorReponseDTO {
    private String errorMessage;
    private final LocalDate timestamp = LocalDate.now();

    public ErrorReponseDTO() {
    }

    public ErrorReponseDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

}
