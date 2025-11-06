package com.paymentgateway.repository;

import com.paymentgateway.model.SavedCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SavedCardRepository extends JpaRepository<SavedCard, Long> {
    List<SavedCard> findByUserIdOrderByAddedDateDesc(Long userId);
    List<SavedCard> findByUserIdAndIsDefaultTrue(Long userId);
}