package com.adarsh.tradingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioStockResponse {

    private String stockSymbol;

    private Integer quantity;

    private BigDecimal averagePrice;

    private BigDecimal currentPrice;

    private BigDecimal investedAmount;

    private BigDecimal currentValue;

    private BigDecimal profitLoss;
}