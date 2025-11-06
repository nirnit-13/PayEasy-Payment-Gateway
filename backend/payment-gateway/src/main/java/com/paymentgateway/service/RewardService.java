package com.paymentgateway.service;

import com.paymentgateway.model.Reward;
import com.paymentgateway.model.User;
import com.paymentgateway.repository.RewardRepository;
import com.paymentgateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Reward Service
 * Business logic for reward/coupon operations
 */
@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String[] REWARD_TITLES = {
            "Flat ₹50 Off",
            "10% Cashback",
            "₹100 Cashback",
            "15% Off on Next Transaction",
            "Free Transaction",
            "₹200 Instant Discount"
    };

    private static final String[] REWARD_DESCRIPTIONS = {
            "Valid on transactions above ₹500",
            "Maximum cashback ₹150",
            "Use within 30 days",
            "Applicable on all merchants",
            "Limited period offer",
            "Valid on minimum ₹1000 transaction"
    };

    /**
     * Generate random reward after transaction
     */
    @Transactional
    public Reward generateReward(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Random random = new Random();

        // Generate random coupon code
        String couponCode = generateCouponCode();

        // Select random reward
        String rewardTitle = REWARD_TITLES[random.nextInt(REWARD_TITLES.length)];
        String rewardDescription = REWARD_DESCRIPTIONS[random.nextInt(REWARD_DESCRIPTIONS.length)];

        // Random discount percent (5% to 20%)
        Integer discountPercent = 5 + random.nextInt(16);

        Reward reward = new Reward(user, couponCode, rewardTitle,
                rewardDescription, discountPercent);

        return rewardRepository.save(reward);
    }

    /**
     * Get all rewards for a user
     */
    public List<Reward> getUserRewards(Long userId) {
        return rewardRepository.findByUserIdOrderByEarnedDateDesc(userId);
    }

    /**
     * Get unused rewards for a user
     */
    public List<Reward> getUnusedRewards(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return rewardRepository.findByUserAndIsUsedFalseOrderByEarnedDateDesc(user);
    }

    /**
     * Mark reward as used
     */
    @Transactional
    public Reward useReward(Long rewardId) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RuntimeException("Reward not found"));

        if (reward.getIsUsed()) {
            throw new RuntimeException("Reward already used");
        }

        reward.setIsUsed(true);
        return rewardRepository.save(reward);
    }

    /**
     * Generate unique coupon code
     */
    private String generateCouponCode() {
        return "COUP" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}