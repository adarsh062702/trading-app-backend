package com.adarsh.tradingapp.repository;

import com.adarsh.tradingapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserEmail(String userEmail);
}