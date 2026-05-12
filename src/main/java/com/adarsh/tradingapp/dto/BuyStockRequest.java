package com.adarsh.tradingapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyStockRequest {

    private String email;

    private String stockSymbol;

    private Integer quantity;
}