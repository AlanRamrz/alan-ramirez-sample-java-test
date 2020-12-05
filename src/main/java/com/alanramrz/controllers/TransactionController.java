package com.alanramrz.controllers;

import com.alanramrz.configuration.exceptions.BaseError;
import com.alanramrz.dtos.requests.TransactionRequestDTO;
import com.alanramrz.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(@RequestBody @Valid TransactionRequestDTO transactionRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(transactionRequestDTO));
    }

    @GetMapping("/transactions/random")
    public ResponseEntity<Object> getRandomTransaction() throws BaseError {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getRandomTransaction());
    }

    @GetMapping("/users/{userId}/transactions")
    public ResponseEntity<Object> getTransactionsByUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactionsByUser(userId));
    }

    @GetMapping("/users/{userId}/transactions/{transactionId}")
    public ResponseEntity<Object> showTransactionByUser(
        @PathVariable Long userId,
        @PathVariable String transactionId
    ) throws BaseError {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.showTransactionByUser(transactionId, userId));
    }

    @GetMapping("/users/{userId}/sum-transactions")
    public ResponseEntity<Object> sumTransactionsByUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.sumTransactionsByUser(userId));
    }

    @GetMapping("/users/{userId}/report-transactions")
    public ResponseEntity<Object> reportTransactionsByUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.reportTransactionsByUser(userId));
    }
}
