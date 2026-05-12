package com.adarsh.tradingapp.controller;

import com.adarsh.tradingapp.dto.AddStockRequest;
import com.adarsh.tradingapp.dto.BuyStockRequest;
import com.adarsh.tradingapp.entity.Stock;
import com.adarsh.tradingapp.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.adarsh.tradingapp.dto.SellStockRequest;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/add")
    public ResponseEntity<String> addStock(@RequestBody AddStockRequest request) {

        return ResponseEntity.ok(stockService.addStock(request));
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {

        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @PostMapping("/buy-stock")
    public ResponseEntity<String> buyStock(@RequestBody BuyStockRequest request) {

        return ResponseEntity.ok(stockService.buyStock(request));
    }
    @PostMapping("/sell-stock")
    public ResponseEntity<String> sellStock(
            @RequestBody SellStockRequest request
    ) {

        return ResponseEntity.ok(
                stockService.sellStock(request)
        );
    }
}