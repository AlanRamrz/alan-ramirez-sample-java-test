package com.alanramrz.dtos.requests;

import lombok.Data;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TransactionRequestDTO {
    @NotNull
    @DecimalMin(value = "0.01", message = "'amount' should be bigger than zero")
    private Double amount;

    @NotNull(message = "Please provide 'date'")
    private LocalDate date;

    @NotNull(message = "Please provide 'description'")
    private String description;

    @NotNull(message = "Please provide 'userId'")
    private Long userId;
}
