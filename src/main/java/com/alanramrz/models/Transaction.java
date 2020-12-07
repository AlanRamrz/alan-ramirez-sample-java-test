package com.alanramrz.models;
import com.alanramrz.dtos.requests.TransactionRequestDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    private String id;
    private Double amount;
    private LocalDate date;
    private String description;
    private Long userId;

    public Transaction(TransactionRequestDTO transactionRequestDTO) {
        this.id = UUID.randomUUID().toString();
        this.amount = transactionRequestDTO.getAmount();
        this.date = transactionRequestDTO.getDate();
        this.description = transactionRequestDTO.getDescription();
        this.userId = transactionRequestDTO.getUserId();
    }
}
