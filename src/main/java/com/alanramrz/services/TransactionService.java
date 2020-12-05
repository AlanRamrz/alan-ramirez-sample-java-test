package com.alanramrz.services;

import com.alanramrz.configuration.exceptions.BaseError;
import com.alanramrz.dtos.requests.TransactionRequestDTO;
import com.alanramrz.dtos.responses.ReportTransactionRowResponseDTO;
import com.alanramrz.dtos.responses.SumTransactionsResponseDTO;
import com.alanramrz.models.Transaction;
import com.alanramrz.repositories.TransactionRepository;
import com.alanramrz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(TransactionRequestDTO transactionRequestDTO) {
        Transaction transaction = new Transaction(transactionRequestDTO);
        transaction = transactionRepository.save(transaction);

        return transaction;
    }

    public Transaction getRandomTransaction() throws BaseError {
        List<Transaction> transactions = transactionRepository.findAll();
        if (transactions.isEmpty()) {
            throw new BaseError(HttpStatus.CONFLICT, Constants.EMPTY_LIST, Constants.TRANSACTIONS_EMPTY_LIST);
        }

        Random random = new Random();

        return transactions.get(random.nextInt(transactions.size()));
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

    public SumTransactionsResponseDTO sumTransactionsByUser(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserIdOrderByDateAsc(userId);
        Double sum = transactions.isEmpty() ? 0.0 : transactions.stream().mapToDouble(it -> it.getAmount()).sum();

        return new SumTransactionsResponseDTO(userId, sum);
    }

    public List<ReportTransactionRowResponseDTO> reportTransactionsByUser(Long userId) {
        List<ReportTransactionRowResponseDTO> reportList = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findByUserIdOrderByDateAsc(userId);
        if(transactions.isEmpty()){
            return reportList;
        }

        Date startWeek = getStartWeek(transactions.get(0).getDate());
        Date endWeek = getEndWeek(transactions.get(0).getDate());
        Integer quantity = 0;
        Double amount = 0.0;
        Double totalAmount = 0.0;

        for(Transaction transaction : transactions) {
            if (transaction.getDate().getTime() >= startWeek.getTime() && transaction.getDate().getTime() <= endWeek.getTime()) {
                quantity++;
                amount += transaction.getAmount();
                continue;
            }

            reportList.add(new ReportTransactionRowResponseDTO(userId, startWeek, endWeek, quantity, amount, totalAmount));
            totalAmount += amount;
            startWeek = getStartWeek(transaction.getDate());
            endWeek = getEndWeek(transaction.getDate());
            quantity = 1;
            amount = transaction.getAmount();
        }
        reportList.add(new ReportTransactionRowResponseDTO(userId, startWeek, endWeek, quantity, amount, totalAmount));

        return reportList;
    }

    private Date getStartWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY){
            cal.add(Calendar.DAY_OF_YEAR, -1);
        }

        return cal.getTime();
    }

    private Date getEndWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY){
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        return cal.getTime();
    }
}
