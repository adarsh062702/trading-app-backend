package com.adarsh.tradingapp.service;

import com.adarsh.tradingapp.dto.WalletRequest;
import com.adarsh.tradingapp.entity.Transaction;
import com.adarsh.tradingapp.entity.wallet;
import com.adarsh.tradingapp.exception.ApiException;
import com.adarsh.tradingapp.repository.TransactionRepository;
import com.adarsh.tradingapp.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public BigDecimal getBalance(String email) {

        wallet wallet = walletRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        return wallet.getBalance();
    }

    public String addMoney(WalletRequest request) {

        wallet wallet = walletRepository.findByUserEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBalance(
                wallet.getBalance().add(request.getAmount())
        );

        walletRepository.save(wallet);

        Transaction transaction = Transaction.builder()
                .type("ADD")
                .amount(request.getAmount())
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        return "Money added successfully";
    }

    public String withdrawMoney(WalletRequest request) {

        wallet wallet = walletRepository.findByUserEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new ApiException("Insufficient balance");
        }

        wallet.setBalance(
                wallet.getBalance().subtract(request.getAmount())
        );

        walletRepository.save(wallet);

        Transaction transaction = Transaction.builder()
                .type("WITHDRAW")
                .amount(request.getAmount())
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        return "Money withdrawn successfully";
    }
}