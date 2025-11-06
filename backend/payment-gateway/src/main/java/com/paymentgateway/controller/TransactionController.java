package com.paymentgateway.controller;

import com.paymentgateway.model.Transaction;
import com.paymentgateway.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Transaction Controller - WITH CARD PAYMENT SUPPORT
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Process regular payment (deducts from wallet)
     */
    @PostMapping("/pay")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Long userId = Long.parseLong(request.get("userId").toString());
            String recipientName = request.get("recipientName").toString();
            String recipientUPI = request.get("recipientUPI").toString();
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            String description = request.getOrDefault("description", "Payment").toString();
            String paymentMethod = request.getOrDefault("paymentMethod", "UPI").toString();

            Transaction transaction = transactionService.processPayment(
                    userId, recipientName, recipientUPI, amount, description, paymentMethod
            );

            response.put("success", true);
            response.put("message", "Payment successful");
            response.put("transactionId", transaction.getTransactionId());
            response.put("amount", transaction.getAmount());
            response.put("cashback", transaction.getCashback());
            response.put("recipientName", transaction.getRecipientName());
            response.put("recipientUPI", transaction.getRecipientUPI());
            response.put("transactionDate", transaction.getTransactionDate().toString());
            response.put("status", transaction.getStatus());
            response.put("paymentMethod", transaction.getPaymentMethod());

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Payment processing failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * NEW: Process card payment (does NOT deduct from wallet, only adds cashback)
     */
    @PostMapping("/pay-with-card")
    public ResponseEntity<Map<String, Object>> processCardPayment(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("=== PROCESSING CARD PAYMENT ===");

            Long userId = Long.parseLong(request.get("userId").toString());
            Long cardId = Long.parseLong(request.get("cardId").toString());
            String recipientName = request.get("recipientName").toString();
            String recipientUPI = request.get("recipientUPI").toString();
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            String description = request.getOrDefault("description", "Card Payment").toString();
            String cardBrand = request.getOrDefault("cardBrand", "CARD").toString();

            System.out.println("Card Payment - User: " + userId + " | Card: " + cardId + " | Amount: ₹" + amount);

            // Process card payment (no wallet deduction, only cashback addition)
            Transaction transaction = transactionService.processCardPayment(
                    userId, cardId, recipientName, recipientUPI, amount, description, cardBrand
            );

            response.put("success", true);
            response.put("message", "Card payment successful");
            response.put("transactionId", transaction.getTransactionId());
            response.put("amount", transaction.getAmount());
            response.put("cashback", transaction.getCashback());
            response.put("recipientName", transaction.getRecipientName());
            response.put("recipientUPI", transaction.getRecipientUPI());
            response.put("transactionDate", transaction.getTransactionDate().toString());
            response.put("status", transaction.getStatus());
            response.put("paymentMethod", transaction.getPaymentMethod());

            System.out.println("✓ Card payment successful | Cashback: ₹" + transaction.getCashback());
            System.out.println("==============================");

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Card payment processing failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get transaction history - FIXED
     */
    @GetMapping("/history/{userId}")
    public ResponseEntity<Map<String, Object>> getTransactionHistory(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("==========================================");
            System.out.println("FETCHING TRANSACTION HISTORY");
            System.out.println("User ID: " + userId);
            System.out.println("==========================================");

            List<Transaction> transactions = transactionService.getUserTransactions(userId);
            System.out.println("Found " + transactions.size() + " transactions in service");

            // Convert transactions to Map to avoid Jackson serialization issues
            List<Map<String, Object>> transactionList = transactions.stream()
                    .map(txn -> {
                        Map<String, Object> txnMap = new HashMap<>();
                        txnMap.put("id", txn.getId());
                        txnMap.put("transactionId", txn.getTransactionId());
                        txnMap.put("recipientName", txn.getRecipientName());
                        txnMap.put("recipientUPI", txn.getRecipientUPI());
                        txnMap.put("amount", txn.getAmount());
                        txnMap.put("cashback", txn.getCashback());
                        txnMap.put("paymentMethod", txn.getPaymentMethod());
                        txnMap.put("status", txn.getStatus());
                        txnMap.put("transactionDate", txn.getTransactionDate().toString());
                        txnMap.put("description", txn.getDescription());
                        return txnMap;
                    })
                    .toList();

            System.out.println("Converted to " + transactionList.size() + " transaction maps");

            response.put("success", true);
            response.put("transactions", transactionList);
            response.put("count", transactionList.size());

            // Calculate totals
            BigDecimal totalAmount = transactions.stream()
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalCashback = transactions.stream()
                    .map(Transaction::getCashback)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            response.put("totalAmount", totalAmount);
            response.put("totalCashback", totalCashback);

            System.out.println("Total Amount: ₹" + totalAmount);
            System.out.println("Total Cashback: ₹" + totalCashback);
            System.out.println("Response ready to send");
            System.out.println("==========================================");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("==========================================");
            System.err.println("ERROR GETTING TRANSACTIONS");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.err.println("==========================================");

            response.put("success", false);
            response.put("message", "Failed to load transactions: " + e.getMessage());
            response.put("transactions", List.of());
            response.put("count", 0);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get transaction by ID - FIXED
     */
    @GetMapping("/{transactionId}")
    public ResponseEntity<Map<String, Object>> getTransaction(@PathVariable String transactionId) {
        Map<String, Object> response = new HashMap<>();

        try {
            Transaction transaction = transactionService.getTransactionById(transactionId);

            // Convert to Map
            Map<String, Object> txnMap = new HashMap<>();
            txnMap.put("id", transaction.getId());
            txnMap.put("transactionId", transaction.getTransactionId());
            txnMap.put("recipientName", transaction.getRecipientName());
            txnMap.put("recipientUPI", transaction.getRecipientUPI());
            txnMap.put("amount", transaction.getAmount());
            txnMap.put("cashback", transaction.getCashback());
            txnMap.put("paymentMethod", transaction.getPaymentMethod());
            txnMap.put("status", transaction.getStatus());
            txnMap.put("transactionDate", transaction.getTransactionDate().toString());
            txnMap.put("description", transaction.getDescription());

            response.put("success", true);
            response.put("transaction", txnMap);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Transaction not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}