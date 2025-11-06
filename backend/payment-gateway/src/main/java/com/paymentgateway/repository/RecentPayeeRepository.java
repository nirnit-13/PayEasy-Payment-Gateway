package com.paymentgateway.repository;

import com.paymentgateway.model.RecentPayee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecentPayeeRepository extends JpaRepository<RecentPayee, Long> {
    List<RecentPayee> findByUserIdOrderByLastPaymentDateDesc(Long userId);
    Optional<RecentPayee> findByUserIdAndPayeeIdentifier(Long userId, String payeeIdentifier);
}