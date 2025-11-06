package com.paymentgateway.controller;

import com.paymentgateway.model.RecentPayee;
import com.paymentgateway.service.PayeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payees")
public class PayeeController {

    @Autowired
    private PayeeService payeeService;

    @GetMapping("/recent/{userId}")
    public ResponseEntity<Map<String, Object>> getRecentPayees(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<RecentPayee> payees = payeeService.getRecentPayees(userId);

            response.put("success", true);
            response.put("payees", payees);
            response.put("count", payees.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}