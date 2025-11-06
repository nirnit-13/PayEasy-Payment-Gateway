package com.paymentgateway.controller;

import com.paymentgateway.model.SavedCard;
import com.paymentgateway.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveCard(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Long userId = Long.parseLong(request.get("userId").toString());
            String cardHolderName = request.get("cardHolderName").toString();
            String cardNumber = request.get("cardNumber").toString();
            String cardType = request.get("cardType").toString();
            String cardBrand = request.get("cardBrand").toString();
            String expiryMonth = request.get("expiryMonth").toString();
            String expiryYear = request.get("expiryYear").toString();

            SavedCard card = cardService.saveCard(userId, cardHolderName, cardNumber,
                    cardType, cardBrand, expiryMonth, expiryYear);

            response.put("success", true);
            response.put("message", "Card saved successfully");
            response.put("card", card);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserCards(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<SavedCard> cards = cardService.getUserCards(userId);

            response.put("success", true);
            response.put("cards", cards);
            response.put("count", cards.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Map<String, Object>> deleteCard(@PathVariable Long cardId) {
        Map<String, Object> response = new HashMap<>();

        try {
            cardService.deleteCard(cardId);

            response.put("success", true);
            response.put("message", "Card deleted successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{cardId}/default")
    public ResponseEntity<Map<String, Object>> setDefaultCard(@PathVariable Long cardId,
                                                              @RequestBody Map<String, Long> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Long userId = request.get("userId");
            SavedCard card = cardService.setDefaultCard(cardId, userId);

            response.put("success", true);
            response.put("message", "Default card updated");
            response.put("card", card);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}