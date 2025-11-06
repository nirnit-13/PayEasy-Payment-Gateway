package com.paymentgateway.service;

import com.paymentgateway.model.RecentPayee;
import com.paymentgateway.model.User;
import com.paymentgateway.repository.RecentPayeeRepository;
import com.paymentgateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PayeeService {

    @Autowired
    private RecentPayeeRepository recentPayeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addOrUpdatePayee(Long userId, String payeeName, String payeeIdentifier, String payeeType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<RecentPayee> existing = recentPayeeRepository
                .findByUserIdAndPayeeIdentifier(userId, payeeIdentifier);

        if (existing.isPresent()) {
            // Update existing
            RecentPayee payee = existing.get();
            payee.setLastPaymentDate(LocalDateTime.now());
            payee.setPaymentCount(payee.getPaymentCount() + 1);
            recentPayeeRepository.save(payee);
        } else {
            // Create new
            RecentPayee payee = new RecentPayee(user, payeeName, payeeIdentifier, payeeType);
            recentPayeeRepository.save(payee);
        }
    }

    public List<RecentPayee> getRecentPayees(Long userId) {
        return recentPayeeRepository.findByUserIdOrderByLastPaymentDateDesc(userId);
    }
}