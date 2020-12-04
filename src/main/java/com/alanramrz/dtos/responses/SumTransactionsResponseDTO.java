package com.alanramrz.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SumTransactionsResponseDTO {
    private Long userId;
    private Double sum;
}
