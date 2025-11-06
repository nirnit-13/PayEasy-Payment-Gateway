package com.paymentgateway.service;

import com.paymentgateway.model.User;
import com.paymentgateway.model.Wallet;
import com.paymentgateway.repository.UserRepository;
import com.paymentgateway.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public User registerUser(String fullName, String email, String password, String phoneNumber, String transactionPin) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new RuntimeException("Phone number already registered");
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setTransactionPin(transactionPin.trim());
        user = userRepository.save(user);

        BigDecimal randomBalance = generateRandomBalance();
        Wallet wallet = new Wallet(user, randomBalance);
        walletRepository.save(wallet);

        return user;
    }

    public User loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean verifyPin(Long userId, String pin) {
        User user = getUserById(userId);
        String storedPin = user.getTransactionPin();
        String providedPin = pin.trim();

        System.out.println("=== PIN VERIFICATION DEBUG ===");
        System.out.println("User ID: " + userId);
        System.out.println("Stored PIN: [" + storedPin + "]");
        System.out.println("Provided PIN: [" + providedPin + "]");
        System.out.println("Stored PIN length: " + (storedPin != null ? storedPin.length() : "null"));
        System.out.println("Provided PIN length: " + providedPin.length());
        System.out.println("Match result: " + (storedPin != null && storedPin.equals(providedPin)));
        System.out.println("==============================");

        if (storedPin == null) {
            return false;
        }

        return storedPin.equals(providedPin);
    }

    private BigDecimal generateRandomBalance() {
        Random random = new Random();
        int minBalance = 20000;
        int maxBalance = 50000;
        double randomBalance = minBalance + (maxBalance - minBalance) * random.nextDouble();
        return BigDecimal.valueOf(Math.round(randomBalance * 100.0) / 100.0);
    }
}