package com.paymentgateway.controller;

import com.paymentgateway.model.Wallet;
import com.paymentgateway.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Wallet Controller
 * REST API endpoints for wallet operations
 */
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    /**
     * Get wallet balance
     * GET /api/wallet/balance/{userId}
     */
    @GetMapping("/balance/{userId}")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            Wallet wallet = walletService.getWalletByUserId(userId);

            response.put("success", true);
            response.put("balance", wallet.getBalance());
            response.put("walletId", wallet.getId());
            response.put("updatedAt", wallet.getUpdatedAt().toString());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Add money to wallet (for testing/demo purposes)
     * POST /api/wallet/add
     */
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addMoney(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Long userId = Long.parseLong(request.get("userId").toString());
            BigDecimal amount = new BigDecimal(request.get("amount").toString());

            walletService.addAmount(userId, amount);
            BigDecimal newBalance = walletService.getBalance(userId);

            response.put("success", true);
            response.put("message", "Money added successfully");
            response.put("newBalance", newBalance);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}