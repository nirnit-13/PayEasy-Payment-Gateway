package com.paymentgateway.repository;

import com.paymentgateway.model.Reward;
import com.paymentgateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Reward Repository
 * Data access layer for Reward entity
 */
@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

    // Find all rewards by user
    List<Reward> findByUserOrderByEarnedDateDesc(User user);

    // Find unused rewards by user
    List<Reward> findByUserAndIsUsedFalseOrderByEarnedDateDesc(User user);

    // Find all rewards by user ID
    List<Reward> findByUserIdOrderByEarnedDateDesc(Long userId);
}