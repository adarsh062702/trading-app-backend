package com.adarsh.tradingapp.repository;

import com.adarsh.tradingapp.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository
        extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByUserEmailAndStockSymbol(
            String email,
            String stockSymbol
    );

    List<Portfolio> findByUserEmail(String email);
}