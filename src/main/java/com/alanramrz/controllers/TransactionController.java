package com.alanramrz.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @GetMapping("/transactions")
    public ResponseEntity<Object> getTransactions() {
        return ResponseEntity.ok().build();
    }
}
