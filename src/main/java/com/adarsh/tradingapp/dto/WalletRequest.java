package com.adarsh.tradingapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletRequest {

    private String email;

    private BigDecimal amount;
}