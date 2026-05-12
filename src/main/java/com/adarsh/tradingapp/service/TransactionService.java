package com.adarsh.tradingapp.service;

import com.adarsh.tradingapp.entity.Transaction;
import com.adarsh.tradingapp.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getTransactions(String email) {

        return transactionRepository.findByUserEmail(email);
    }
}