package com.alanramrz.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportTransactionRowResponseDTO {
    private Long userId;
    private String weekStart;
    private String weekFinish;
    private Integer quantity;
    private Double amount;
    private Double totalAmount;

    public ReportTransactionRowResponseDTO(Long userId, LocalDate weekStart, LocalDate weekFinish, Integer quantity, Double amount, Double totalAmount) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd EEEE");
        this.userId = userId;
        this.weekStart = formatter.format(weekStart);
        this.weekFinish = formatter.format(weekFinish);
        this.quantity = quantity;
        this.amount = amount;
        this.totalAmount = totalAmount;
    }
}
