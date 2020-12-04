package com.alanramrz.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public ReportTransactionRowResponseDTO(Long userId, Date weekStart, Date weekFinish, Integer quantity, Double amount, Double totalAmount){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd EEEE");
        this.userId = userId;
        this.weekStart = formatter.format(weekStart);
        this.weekFinish = formatter.format(weekFinish);
        this.quantity = quantity;
        this.amount = amount;
        this.totalAmount = totalAmount;
    }
}
