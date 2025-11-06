package com.paymentgateway.service;

import com.paymentgateway.model.Transaction;
import com.paymentgateway.model.User;
import com.paymentgateway.repository.TransactionRepository;
import com.paymentgateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

/**
 * Transaction Service - WITH CARD PAYMENT SUPPORT
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private PayeeService payeeService;

    private final Random random = new Random();

    /**
     * Process regular payment (deducts from wallet + adds cashback)
     */
    @Transactional
    public Transaction processPayment(Long userId, String recipientName,
                                      String recipientUPI, BigDecimal amount,
                                      String description, String paymentMethod) {

        System.out.println("=== PROCESSING WALLET PAYMENT ===");
        System.out.println("User: " + userId + " | Amount: ₹" + amount + " | Method: " + paymentMethod);

        // Validate amount
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal currentBalance = walletService.getBalance(userId);

        if (currentBalance.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance. Available: ₹" + currentBalance);
        }

        // Calculate cashback (1% to 5%)
        BigDecimal cashback = calculateCashback(amount);
        String transactionId = generateTransactionId();

        // Deduct amount and add cashback
        walletService.deductAmount(userId, amount);
        walletService.addAmount(userId, cashback);

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setRecipientName(recipientName);
        transaction.setRecipientUPI(recipientUPI);
        transaction.setAmount(amount);
        transaction.setCashback(cashback);
        transaction.setTransactionId(transactionId);
        transaction.setPaymentMethod(paymentMethod);
        transaction.setDescription(description);
        transaction.setStatus("SUCCESS");

        transaction = transactionRepository.save(transaction);

        // Update payee list
        String payeeType = determinePayeeType(paymentMethod);
        payeeService.addOrUpdatePayee(userId, recipientName, recipientUPI, payeeType);

        System.out.println("✓ Wallet payment processed | TXN: " + transactionId + " | Cashback: ₹" + cashback);
        System.out.println("==========================");

        return transaction;
    }

    /**
     * NEW: Process card payment (NO wallet deduction, only cashback addition)
     */
    @Transactional
    public Transaction processCardPayment(Long userId, Long cardId, String recipientName,
                                          String recipientUPI, BigDecimal amount,
                                          String description, String cardBrand) {

        System.out.println("=== PROCESSING CARD PAYMENT (NO WALLET DEDUCTION) ===");
        System.out.println("User: " + userId + " | Card: " + cardId + " | Amount: ₹" + amount);

        // Validate amount
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Calculate cashback (1% to 5%)
        BigDecimal cashback = calculateCashback(amount);
        String transactionId = generateTransactionId();

        // IMPORTANT: Only add cashback to wallet, DO NOT deduct payment amount
        // (Payment is charged to the card, not the wallet)
        walletService.addAmount(userId, cashback);

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setRecipientName(recipientName);
        transaction.setRecipientUPI(recipientUPI);
        transaction.setAmount(amount);
        transaction.setCashback(cashback);
        transaction.setTransactionId(transactionId);
        transaction.setPaymentMethod("CARD - " + cardBrand);
        transaction.setDescription(description + " (Paid via Card)");
        transaction.setStatus("SUCCESS");

        transaction = transactionRepository.save(transaction);

        // Update payee list
        payeeService.addOrUpdatePayee(userId, recipientName, recipientUPI, "CARD");

        System.out.println("✓ Card payment processed (wallet NOT deducted)");
        System.out.println("✓ Cashback ₹" + cashback + " added to wallet");
        System.out.println("✓ TXN: " + transactionId);
        System.out.println("====================================================");

        return transaction;
    }

    /**
     * Get user transactions ordered by date
     */
    public List<Transaction> getUserTransactions(Long userId) {
        return transactionRepository.findByUserIdOrderByTransactionDateDesc(userId);
    }

    /**
     * Get transaction by ID
     */
    public Transaction getTransactionById(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found: " + transactionId));
    }

    /**
     * Calculate cashback between 1% and 5%
     */
    private BigDecimal calculateCashback(BigDecimal amount) {
        double cashbackPercent = 1.0 + (4.0 * random.nextDouble()); // 1% to 5%
        BigDecimal cashback = amount.multiply(BigDecimal.valueOf(cashbackPercent / 100));

        // Minimum cashback ₹1
        if (cashback.compareTo(BigDecimal.ONE) < 0) {
            cashback = BigDecimal.ONE;
        }

        return cashback.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Generate unique transaction ID
     */
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + random.nextInt(1000);
    }

    /**
     * Determine payee type from payment method
     */
    private String determinePayeeType(String paymentMethod) {
        return switch (paymentMethod.toUpperCase()) {
            case "UPI" -> "UPI";
            case "CARD" -> "CARD";
            case "BANK" -> "BANK";
            case "QR" -> "QR";
            case "PHONE" -> "PHONE";
            default -> "UPI";
        };
    }
}