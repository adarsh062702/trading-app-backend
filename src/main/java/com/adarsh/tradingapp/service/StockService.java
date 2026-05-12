package com.adarsh.tradingapp.service;

import com.adarsh.tradingapp.dto.AddStockRequest;
import com.adarsh.tradingapp.dto.BuyStockRequest;
import com.adarsh.tradingapp.entity.Portfolio;
import com.adarsh.tradingapp.entity.Stock;
import com.adarsh.tradingapp.entity.Transaction;
import com.adarsh.tradingapp.entity.wallet;
import com.adarsh.tradingapp.repository.PortfolioRepository;
import com.adarsh.tradingapp.repository.StockRepository;
import com.adarsh.tradingapp.repository.TransactionRepository;
import com.adarsh.tradingapp.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adarsh.tradingapp.dto.SellStockRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    private final WalletRepository walletRepository;

    private final PortfolioRepository portfolioRepository;

    private final TransactionRepository transactionRepository;

    public StockService(
            StockRepository stockRepository,
            WalletRepository walletRepository,
            PortfolioRepository portfolioRepository,
            TransactionRepository transactionRepository
    ) {
        this.stockRepository = stockRepository;
        this.walletRepository = walletRepository;
        this.portfolioRepository = portfolioRepository;
        this.transactionRepository = transactionRepository;
    }

    public String addStock(AddStockRequest request) {

        Stock stock = Stock.builder()
                .symbol(request.getSymbol())
                .companyName(request.getCompanyName())
                .price(request.getPrice())
                .availableQuantity(request.getAvailableQuantity())
                .build();

        stockRepository.save(stock);

        return "Stock added successfully";
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Transactional
    public String buyStock(BuyStockRequest request) {

        Stock stock = stockRepository.findBySymbol(request.getStockSymbol())
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        wallet wallet = walletRepository.findByUserEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        int quantity = request.getQuantity();

        if (stock.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        BigDecimal totalPrice =
                stock.getPrice().multiply(BigDecimal.valueOf(quantity));

        if (wallet.getBalance().compareTo(totalPrice) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        wallet.setBalance(
                wallet.getBalance().subtract(totalPrice)
        );

        stock.setAvailableQuantity(
                stock.getAvailableQuantity() - quantity
        );

        Optional<Portfolio> existingPortfolio =
                portfolioRepository.findByUserEmailAndStockSymbol(
                        request.getEmail(),
                        request.getStockSymbol()
                );

        if (existingPortfolio.isPresent()) {

            Portfolio portfolio = existingPortfolio.get();

            int newQuantity = portfolio.getQuantity() + quantity;

            BigDecimal oldInvestment =
                    portfolio.getAveragePrice()
                            .multiply(BigDecimal.valueOf(portfolio.getQuantity()));

            BigDecimal newInvestment =
                    stock.getPrice()
                            .multiply(BigDecimal.valueOf(quantity));

            BigDecimal newAveragePrice =
                    oldInvestment.add(newInvestment)
                            .divide(BigDecimal.valueOf(newQuantity));

            portfolio.setQuantity(newQuantity);

            portfolio.setAveragePrice(newAveragePrice);

            portfolioRepository.save(portfolio);

        } else {

            Portfolio portfolio = Portfolio.builder()
                    .userEmail(request.getEmail())
                    .stockSymbol(request.getStockSymbol())
                    .quantity(quantity)
                    .averagePrice(stock.getPrice())
                    .build();

            portfolioRepository.save(portfolio);
        }

        Transaction transaction = Transaction.builder()
                .userEmail(request.getEmail())
                .stockSymbol(request.getStockSymbol())
                .quantity(quantity)
                .amount(totalPrice)
                .type("BUY")
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        walletRepository.save(wallet);

        stockRepository.save(stock);

        return "Stock purchased successfully";
    }
    @Transactional
    public String sellStock(SellStockRequest request) {

        Stock stock = stockRepository.findBySymbol(request.getStockSymbol())
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        wallet wallet = walletRepository.findByUserEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Portfolio portfolio = portfolioRepository
                .findByUserEmailAndStockSymbol(
                        request.getEmail(),
                        request.getStockSymbol()
                )
                .orElseThrow(() ->
                        new RuntimeException("Portfolio stock not found"));

        int sellQuantity = request.getQuantity();

        if (portfolio.getQuantity() < sellQuantity) {
            throw new RuntimeException("Not enough stocks to sell");
        }

        BigDecimal sellAmount =
                stock.getPrice().multiply(BigDecimal.valueOf(sellQuantity));

        wallet.setBalance(
                wallet.getBalance().add(sellAmount)
        );

        stock.setAvailableQuantity(
                stock.getAvailableQuantity() + sellQuantity
        );

        int remainingQuantity =
                portfolio.getQuantity() - sellQuantity;

        if (remainingQuantity == 0) {

            portfolioRepository.delete(portfolio);

        } else {

            portfolio.setQuantity(remainingQuantity);

            portfolioRepository.save(portfolio);
        }

        Transaction transaction = Transaction.builder()
                .userEmail(request.getEmail())
                .stockSymbol(request.getStockSymbol())
                .quantity(sellQuantity)
                .amount(sellAmount)
                .type("SELL")
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        walletRepository.save(wallet);

        stockRepository.save(stock);

        return "Stock sold successfully";
    }
}