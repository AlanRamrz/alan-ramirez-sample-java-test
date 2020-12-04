package com.alanramrz.services;

import com.alanramrz.configuration.exceptions.BaseError;
import com.alanramrz.dtos.requests.TransactionRequestDTO;
import com.alanramrz.models.Transaction;
import com.alanramrz.repositories.TransactionRepository;
import com.alanramrz.utils.Constants;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(TransactionRequestDTO transactionRequestDTO) {
        Transaction transaction = new Transaction(transactionRequestDTO);
        transaction = transactionRepository.save(transaction);

        return transaction;
    }

    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserIdOrderByDateAsc(userId);
    }

    public Transaction showTransactionByUser(String transactionId, Long userId) throws BaseError {
        Optional<Transaction> opt = transactionRepository.findById(transactionId);
        if (!opt.isPresent()) {
            throw new BaseError(HttpStatus.NOT_FOUND, Constants.NOT_FOUND, Constants.TRANSACTION_DOESNT_EXIST);
        }

        Transaction transaction = opt.get();
        if (transaction.getUserId() != userId) {
            throw new BaseError(HttpStatus.NOT_FOUND, Constants.NOT_FOUND, Constants.TRANSACTION_DOESNT_EXIST);
        }

        return transaction;
    }
}
