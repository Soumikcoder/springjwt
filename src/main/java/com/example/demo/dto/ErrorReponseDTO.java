package com.example.demo.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ErrorReponseDTO {
    private String errorMessage;
    private final LocalDate timestamp= LocalDate.now();
}
