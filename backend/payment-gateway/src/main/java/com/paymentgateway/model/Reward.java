package com.paymentgateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Reward Entity - FIXED
 * Added @JsonIgnore to prevent circular reference with User entity
 */
@Entity
@Table(name = "rewards")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore  // CRITICAL FIX: Prevents circular reference
    private User user;

    @Column(nullable = false, unique = true)
    private String couponCode;

    @Column(nullable = false)
    private String rewardTitle;

    @Column(nullable = false)
    private String rewardDescription;

    @Column(nullable = false)
    private Integer discountPercent;

    @Column(nullable = false)
    private LocalDateTime earnedDate;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private Boolean isUsed;

    // Constructors
    public Reward() {
        this.earnedDate = LocalDateTime.now();
        this.expiryDate = LocalDateTime.now().plusDays(30);
        this.isUsed = false;
    }

    public Reward(User user, String couponCode, String rewardTitle,
                  String rewardDescription, Integer discountPercent) {
        this.user = user;
        this.couponCode = couponCode;
        this.rewardTitle = rewardTitle;
        this.rewardDescription = rewardDescription;
        this.discountPercent = discountPercent;
        this.earnedDate = LocalDateTime.now();
        this.expiryDate = LocalDateTime.now().plusDays(30);
        this.isUsed = false;
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

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getRewardTitle() {
        return rewardTitle;
    }

    public void setRewardTitle(String rewardTitle) {
        this.rewardTitle = rewardTitle;
    }

    public String getRewardDescription() {
        return rewardDescription;
    }

    public void setRewardDescription(String rewardDescription) {
        this.rewardDescription = rewardDescription;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public LocalDateTime getEarnedDate() {
        return earnedDate;
    }

    public void setEarnedDate(LocalDateTime earnedDate) {
        this.earnedDate = earnedDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }
}