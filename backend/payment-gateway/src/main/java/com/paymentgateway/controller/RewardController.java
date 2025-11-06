package com.paymentgateway.controller;

import com.paymentgateway.model.Reward;
import com.paymentgateway.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reward Controller
 * REST API endpoints for reward operations
 */
@RestController
@RequestMapping("/api/rewards")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    /**
     * Get all rewards for user
     * GET /api/rewards/all/{userId}
     */
    @GetMapping("/all/{userId}")
    public ResponseEntity<Map<String, Object>> getAllRewards(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("=== GET ALL REWARDS CALLED ===");
            System.out.println("User ID: " + userId);

            List<Reward> rewards = rewardService.getUserRewards(userId);

            System.out.println("Rewards found: " + rewards.size());

            response.put("success", true);
            response.put("rewards", rewards);
            response.put("count", rewards.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error getting rewards: " + e.getMessage());
            e.printStackTrace();

            response.put("success", false);
            response.put("message", e.getMessage());
            response.put("rewards", List.of());
            response.put("count", 0);

            return ResponseEntity.ok(response);
        }
    }

    /**
     * Get unused rewards for user
     * GET /api/rewards/unused/{userId}
     */
    @GetMapping("/unused/{userId}")
    public ResponseEntity<Map<String, Object>> getUnusedRewards(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Reward> rewards = rewardService.getUnusedRewards(userId);

            response.put("success", true);
            response.put("rewards", rewards);
            response.put("count", rewards.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            response.put("rewards", List.of());
            response.put("count", 0);

            return ResponseEntity.ok(response);
        }
    }

    /**
     * Use/redeem reward
     * POST /api/rewards/use/{rewardId}
     */
    @PostMapping("/use/{rewardId}")
    public ResponseEntity<Map<String, Object>> useReward(@PathVariable Long rewardId) {
        Map<String, Object> response = new HashMap<>();

        try {
            Reward reward = rewardService.useReward(rewardId);

            response.put("success", true);
            response.put("message", "Reward used successfully");
            response.put("reward", reward);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}