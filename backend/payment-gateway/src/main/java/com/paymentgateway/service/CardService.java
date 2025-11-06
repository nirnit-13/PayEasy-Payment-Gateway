package com.paymentgateway.service;

import com.paymentgateway.model.SavedCard;
import com.paymentgateway.model.User;
import com.paymentgateway.repository.SavedCardRepository;
import com.paymentgateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private SavedCardRepository savedCardRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public SavedCard saveCard(Long userId, String cardHolderName, String cardNumber,
                              String cardType, String cardBrand, String expiryMonth, String expiryYear) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Store only last 4 digits
        String last4Digits = cardNumber.substring(cardNumber.length() - 4);

        SavedCard card = new SavedCard(user, cardHolderName, last4Digits,
                cardType, cardBrand, expiryMonth, expiryYear);

        return savedCardRepository.save(card);
    }

    public List<SavedCard> getUserCards(Long userId) {
        return savedCardRepository.findByUserIdOrderByAddedDateDesc(userId);
    }

    @Transactional
    public void deleteCard(Long cardId) {
        savedCardRepository.deleteById(cardId);
    }

    @Transactional
    public SavedCard setDefaultCard(Long cardId, Long userId) {
        // Remove default from all cards
        List<SavedCard> allCards = savedCardRepository.findByUserIdOrderByAddedDateDesc(userId);
        allCards.forEach(card -> {
            card.setIsDefault(false);
            savedCardRepository.save(card);
        });

        // Set new default
        SavedCard card = savedCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setIsDefault(true);
        return savedCardRepository.save(card);
    }
}