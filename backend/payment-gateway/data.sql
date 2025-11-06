-- Optional: Sample data for testing
-- This file can be placed in backend/src/main/resources/data.sql
-- Spring Boot will automatically execute it on startup if spring.jpa.hibernate.ddl-auto=create or create-drop

-- Note: Uncomment and use only if you want sample data
-- In production, you should use proper migrations like Flyway or Liquibase

-- Sample Users (passwords are plain text - not recommended for production)
-- INSERT INTO users (full_name, email, password, phone_number, created_at) VALUES
-- ('John Doe', 'john@example.com', 'password123', '9876543210', NOW()),
-- ('Jane Smith', 'jane@example.com', 'password123', '9876543211', NOW()),
-- ('Mike Wilson', 'mike@example.com', 'password123', '9876543212', NOW());

-- Sample Wallets (will be auto-created on signup in actual app)
-- INSERT INTO wallets (user_id, balance, created_at, updated_at) VALUES
-- (1, 35000.00, NOW(), NOW()),
-- (2, 42000.00, NOW(), NOW()),
-- (3, 28000.00, NOW(), NOW());

-- Sample Transactions
-- INSERT INTO transactions (user_id, recipient_name, recipient_upi, amount, cashback, transaction_id, status, transaction_date, description) VALUES
-- (1, 'Amazon', 'amazon@upi', 1500.00, 45.00, 'TXN12345678', 'SUCCESS', NOW(), 'Shopping'),
-- (1, 'Swiggy', 'swiggy@paytm', 500.00, 15.00, 'TXN87654321', 'SUCCESS', NOW(), 'Food Order'),
-- (2, 'Uber', 'uber@oksbi', 300.00, 12.00, 'TXN11223344', 'SUCCESS', NOW(), 'Cab Ride');

-- Sample Rewards
-- INSERT INTO rewards (user_id, coupon_code, reward_title, reward_description, discount_percent, earned_date, expiry_date, is_used) VALUES
-- (1, 'COUP12345678', 'Flat ₹50 Off', 'Valid on transactions above ₹500', 10, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), FALSE),
-- (1, 'COUP87654321', '10% Cashback', 'Maximum cashback ₹150', 10, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), FALSE),
-- (2, 'COUP11223344', '₹100 Cashback', 'Use within 30 days', 15, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), FALSE);

-- To use this file:
-- 1. Uncomment the INSERT statements above
-- 2. Make sure spring.jpa.hibernate.ddl-auto is set to 'create' or 'create-drop' in application.properties
-- 3. Restart the Spring Boot application
-- 4. Database will be recreated with sample data

-- For production use:
-- 1. Set spring.jpa.hibernate.ddl-auto=update
-- 2. Don't use this file
-- 3. Data will persist across restarts