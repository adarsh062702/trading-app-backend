package com.adarsh.tradingapp.controller;

import com.adarsh.tradingapp.entity.Transaction;
import com.adarsh.tradingapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor

public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public List<Transaction> getTransactions(@RequestParam String email) {

        return transactionService.getTransactions(email);
    }
}