package com.adarsh.tradingapp.service;

import com.adarsh.tradingapp.dto.PortfolioAnalyticsResponse;
import com.adarsh.tradingapp.dto.PortfolioStockResponse;
import com.adarsh.tradingapp.entity.Portfolio;
import com.adarsh.tradingapp.entity.Stock;
import com.adarsh.tradingapp.repository.PortfolioRepository;
import com.adarsh.tradingapp.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.adarsh.tradingapp.dto.PortfolioAnalyticsResponse;
import com.adarsh.tradingapp.dto.PortfolioStockResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;

    public PortfolioAnalyticsResponse getPortfolioAnalytics(String email) {

        List<Portfolio> portfolios =
                portfolioRepository.findByUserEmail(email);

        List<PortfolioStockResponse> stockResponses =
                new ArrayList<>();

        BigDecimal totalInvestment = BigDecimal.ZERO;
        BigDecimal currentValue = BigDecimal.ZERO;

        for (Portfolio portfolio : portfolios) {

            Stock stock = stockRepository
                    .findBySymbol(portfolio.getStockSymbol())
                    .orElseThrow(() ->
                            new RuntimeException("Stock not found"));

            BigDecimal investedAmount =
                    portfolio.getAveragePrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            portfolio.getQuantity()
                                    )
                            );

            BigDecimal currentStockValue =
                    stock.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            portfolio.getQuantity()
                                    )
                            );

            BigDecimal profitLoss =
                    currentStockValue.subtract(investedAmount);

            totalInvestment =
                    totalInvestment.add(investedAmount);

            currentValue =
                    currentValue.add(currentStockValue);

            PortfolioStockResponse response =
                    PortfolioStockResponse.builder()
                            .stockSymbol(portfolio.getStockSymbol())
                            .quantity(portfolio.getQuantity())
                            .averagePrice(portfolio.getAveragePrice())
                            .currentPrice(stock.getPrice())
                            .investedAmount(investedAmount)
                            .currentValue(currentStockValue)
                            .profitLoss(profitLoss)
                            .build();

            stockResponses.add(response);
        }

        BigDecimal totalProfitLoss =
                currentValue.subtract(totalInvestment);

        return PortfolioAnalyticsResponse.builder()
                .totalInvestment(totalInvestment)
                .currentValue(currentValue)
                .totalProfitLoss(totalProfitLoss)
                .stocks(stockResponses)
                .build();
    }
}