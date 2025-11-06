package com.paymentgateway.repository;

import com.paymentgateway.model.Wallet;
import com.paymentgateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Wallet Repository
 * Data access layer for Wallet entity
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    // Find wallet by user
    Optional<Wallet> findByUser(User user);

    // Find wallet by user ID
    Optional<Wallet> findByUserId(Long userId);
}