package com.adarsh.tradingapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SellStockRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String stockSymbol;

    @Min(1)
    private Integer quantity;
}