package com.paymentgateway.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * RecentPayee Entity
 * Stores recent payment recipients
 */
@Entity
@Table(name = "recent_payees")
public class RecentPayee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String payeeName;

    @Column(nullable = false)
    private String payeeIdentifier; // UPI ID, Phone, Account Number

    @Column(nullable = false)
    private String payeeType; // UPI, PHONE, BANK, QR

    @Column(nullable = false)
    private LocalDateTime lastPaymentDate;

    @Column(nullable = false)
    private Integer paymentCount;

    public RecentPayee() {
        this.lastPaymentDate = LocalDateTime.now();
        this.paymentCount = 1;
    }

    public RecentPayee(User user, String payeeName, String payeeIdentifier, String payeeType) {
        this.user = user;
        this.payeeName = payeeName;
        this.payeeIdentifier = payeeIdentifier;
        this.payeeType = payeeType;
        this.lastPaymentDate = LocalDateTime.now();
        this.paymentCount = 1;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getPayeeName() { return payeeName; }
    public void setPayeeName(String payeeName) { this.payeeName = payeeName; }

    public String getPayeeIdentifier() { return payeeIdentifier; }
    public void setPayeeIdentifier(String payeeIdentifier) { this.payeeIdentifier = payeeIdentifier; }

    public String getPayeeType() { return payeeType; }
    public void setPayeeType(String payeeType) { this.payeeType = payeeType; }

    public LocalDateTime getLastPaymentDate() { return lastPaymentDate; }
    public void setLastPaymentDate(LocalDateTime lastPaymentDate) { this.lastPaymentDate = lastPaymentDate; }

    public Integer getPaymentCount() { return paymentCount; }
    public void setPaymentCount(Integer paymentCount) { this.paymentCount = paymentCount; }
}