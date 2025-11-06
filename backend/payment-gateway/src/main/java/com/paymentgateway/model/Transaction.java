package com.paymentgateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction Entity - FIXED
 * Added @JsonIgnore to prevent circular reference with User entity
 */
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore  // CRITICAL FIX: Prevents Jackson serialization issues
    private User user;

    @Column(nullable = false)
    private String recipientName;

    @Column(nullable = false)
    private String recipientUPI;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cashback;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String paymentMethod; // UPI, CARD, BANK, QR, PHONE

    @Column(nullable = false)
    private String status; // SUCCESS, FAILED, PENDING

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    private String description;

    // Constructors
    public Transaction() {
        this.transactionDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Transaction(User user, String recipientName, String recipientUPI,
                       BigDecimal amount, BigDecimal cashback, String transactionId, String paymentMethod) {
        this.user = user;
        this.recipientName = recipientName;
        this.recipientUPI = recipientUPI;
        this.amount = amount;
        this.cashback = cashback;
        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
        this.transactionDate = LocalDateTime.now();
        this.status = "SUCCESS";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientUPI() {
        return recipientUPI;
    }

    public void setRecipientUPI(String recipientUPI) {
        this.recipientUPI = recipientUPI;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCashback() {
        return cashback;
    }

    public void setCashback(BigDecimal cashback) {
        this.cashback = cashback;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}