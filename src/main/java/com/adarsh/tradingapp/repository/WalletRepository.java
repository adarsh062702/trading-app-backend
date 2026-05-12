package com.adarsh.tradingapp.repository;

import com.adarsh.tradingapp.entity.wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<wallet, Long> {

    Optional<wallet> findByUserEmail(String email);
}