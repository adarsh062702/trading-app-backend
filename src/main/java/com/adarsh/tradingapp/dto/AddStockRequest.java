package com.adarsh.tradingapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddStockRequest {

    private String symbol;

    private String companyName;

    private BigDecimal price;

    private Integer availableQuantity;
}