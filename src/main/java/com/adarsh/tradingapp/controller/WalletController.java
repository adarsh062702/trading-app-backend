package com.adarsh.tradingapp.controller;

import com.adarsh.tradingapp.dto.WalletRequest;
import com.adarsh.tradingapp.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/balance")
    public BigDecimal getBalance(@RequestParam String email) {

        return walletService.getBalance(email);
    }

    @PostMapping("/add-money")
    public String addMoney(@RequestBody WalletRequest request) {

        return walletService.addMoney(request);
    }

    @PostMapping("/withdraw")
    public String withdrawMoney(@RequestBody WalletRequest request) {

        return walletService.withdrawMoney(request);
    }
}