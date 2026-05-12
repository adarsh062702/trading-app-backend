package com.adarsh.tradingapp.controller;

import com.adarsh.tradingapp.dto.PortfolioAnalyticsResponse;
import com.adarsh.tradingapp.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/{email}")
    public ResponseEntity<PortfolioAnalyticsResponse> getPortfolio(
            @PathVariable String email
    ) {

        return ResponseEntity.ok(
                portfolioService.getPortfolioAnalytics(email)
        );
    }
}