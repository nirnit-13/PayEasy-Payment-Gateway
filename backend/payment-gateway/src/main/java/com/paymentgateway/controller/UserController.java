package com.paymentgateway.controller;

import com.paymentgateway.model.User;
import com.paymentgateway.service.UserService;
import com.paymentgateway.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String fullName = request.get("fullName");
            String email = request.get("email");
            String password = request.get("password");
            String phoneNumber = request.get("phoneNumber");
            String transactionPin = request.get("transactionPin");

            User user = userService.registerUser(fullName, email, password, phoneNumber, transactionPin);

            response.put("success", true);
            response.put("message", "Registration successful");
            response.put("userId", user.getId());
            response.put("email", user.getEmail());
            response.put("fullName", user.getFullName());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String email = request.get("email");
            String password = request.get("password");

            User user = userService.loginUser(email, password);

            response.put("success", true);
            response.put("message", "Login successful");
            response.put("userId", user.getId());
            response.put("email", user.getEmail());
            response.put("fullName", user.getFullName());
            response.put("phoneNumber", user.getPhoneNumber());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserProfile(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            User user = userService.getUserById(userId);

            response.put("success", true);
            response.put("userId", user.getId());
            response.put("fullName", user.getFullName());
            response.put("email", user.getEmail());
            response.put("phoneNumber", user.getPhoneNumber());
            response.put("createdAt", user.getCreatedAt().toString());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/verify-pin")
    public ResponseEntity<Map<String, Object>> verifyPin(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Long userId = Long.parseLong(request.get("userId").toString());
            String pin = request.get("pin").toString().trim();

            boolean isValid = userService.verifyPin(userId, pin);

            response.put("success", isValid);
            response.put("message", isValid ? "PIN verified" : "Invalid PIN");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "PIN verification failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{userId}/qr")
    public ResponseEntity<Map<String, Object>> generateUserQR(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            User user = userService.getUserById(userId);

            String qrData = "upi://pay?pa=" + user.getPhoneNumber() + "@payeasy&pn=" + user.getFullName();
            String qrCodeBase64 = qrCodeService.generateQRCodeBase64(qrData);

            response.put("success", true);
            response.put("qrCode", "data:image/png;base64," + qrCodeBase64);
            response.put("qrData", qrData);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "QR generation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}