package com.paymentgateway.repository;

import com.paymentgateway.model.Transaction;
import com.paymentgateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Transaction Repository
 * Data access layer for Transaction entity
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find all transactions by user
    List<Transaction> findByUserOrderByTransactionDateDesc(User user);

    // Find transaction by transaction ID
    Optional<Transaction> findByTransactionId(String transactionId);

    // Find all transactions by user ID
    List<Transaction> findByUserIdOrderByTransactionDateDesc(Long userId);
}