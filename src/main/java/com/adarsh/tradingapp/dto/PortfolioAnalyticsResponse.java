package com.adarsh.tradingapp.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PortfolioAnalyticsResponse {

    private BigDecimal totalInvestment;

    private BigDecimal currentValue;

    private BigDecimal totalProfitLoss;

    private List<PortfolioStockResponse> stocks;
}