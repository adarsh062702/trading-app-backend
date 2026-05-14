package com.adarsh.tradingapp.service;

import com.adarsh.tradingapp.dto.WalletRequest;
import com.adarsh.tradingapp.entity.wallet;
import com.adarsh.tradingapp.repository.TransactionRepository;
import com.adarsh.tradingapp.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.adarsh.tradingapp.exception.ApiException;
import com.adarsh.tradingapp.entity.Transaction;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private WalletService walletService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddMoneySuccessfully() {

        wallet wallet = new wallet();
        wallet.setBalance(new BigDecimal("1000"));

        WalletRequest request = new WalletRequest();
        request.setEmail("test@gmail.com");
        request.setAmount(new BigDecimal("500"));

        when(walletRepository.findByUserEmail("test@gmail.com"))
                .thenReturn(Optional.of(wallet));

        String result = walletService.addMoney(request);

        assertEquals("Money added successfully", result);

        assertEquals(
                new BigDecimal("1500"
                ),
                wallet.getBalance()
        );

        verify(walletRepository, times(1)).save(wallet);
        verify(transactionRepository, times(1)).save(any());
    }@Test
    void shouldWithdrawMoneySuccessfully() {

        wallet wallet = new wallet();
        wallet.setBalance(new BigDecimal("1000"));

        WalletRequest request = new WalletRequest();
        request.setEmail("test@gmail.com");
        request.setAmount(new BigDecimal("300"));

        when(walletRepository.findByUserEmail("test@gmail.com"))
                .thenReturn(Optional.of(wallet));

        String result = walletService.withdrawMoney(request);

        assertEquals("Money withdrawn successfully", result);

        assertEquals(
                new BigDecimal("700"),
                wallet.getBalance()
        );

        verify(walletRepository).save(wallet);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsLow() {

        wallet wallet = new wallet();
        wallet.setBalance(new BigDecimal("200"));

        WalletRequest request = new WalletRequest();
        request.setEmail("test@gmail.com");
        request.setAmount(new BigDecimal("500"));

        when(walletRepository.findByUserEmail("test@gmail.com"))
                .thenReturn(Optional.of(wallet));

        ApiException exception = assertThrows(
                ApiException.class,
                () -> walletService.withdrawMoney(request)
        );

        assertEquals(
                "Insufficient balance",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenWalletNotFound() {

        WalletRequest request = new WalletRequest();
        request.setEmail("missing@gmail.com");
        request.setAmount(new BigDecimal("100"));

        when(walletRepository.findByUserEmail("missing@gmail.com"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> walletService.addMoney(request)
        );

        assertEquals(
                "Wallet not found",
                exception.getMessage()
        );
    }
}