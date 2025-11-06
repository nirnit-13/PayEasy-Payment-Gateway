package com.paymentgateway.service;

import com.paymentgateway.model.User;
import com.paymentgateway.model.Wallet;
import com.paymentgateway.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Wallet Service
 * Business logic for wallet operations
 */
@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    /**
     * Get wallet by user ID
     */
    public Wallet getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    /**
     * Get wallet balance
     */
    public BigDecimal getBalance(Long userId) {
        Wallet wallet = getWalletByUserId(userId);
        return wallet.getBalance();
    }

    /**
     * Deduct amount from wallet
     */
    @Transactional
    public void deductAmount(Long userId, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        BigDecimal newBalance = wallet.getBalance().subtract(amount);
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }

    /**
     * Add amount to wallet (for cashback)
     */
    @Transactional
    public void addAmount(Long userId, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);
        BigDecimal newBalance = wallet.getBalance().add(amount);
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }

    /**
     * Update wallet balance
     */
    @Transactional
    public Wallet updateBalance(Long userId, BigDecimal newBalance) {
        Wallet wallet = getWalletByUserId(userId);
        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }
}