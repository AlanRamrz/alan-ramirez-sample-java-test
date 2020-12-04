package com.alanramrz.services;

import com.alanramrz.dtos.requests.TransactionRequestDTO;
import com.alanramrz.models.Transaction;
import com.alanramrz.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(TransactionRequestDTO transactionRequestDTO) {
        Transaction transaction = new Transaction(transactionRequestDTO);
        transaction = transactionRepository.save(transaction);

        return transaction;
    }
}
