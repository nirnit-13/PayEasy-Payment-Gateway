// signup.js - Signup page functionality

const API_BASE_URL = 'http://localhost:8080/api';

document.addEventListener('DOMContentLoaded', () => {
    const signupForm = document.getElementById('signupForm');
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');
    const loader = document.getElementById('loader');
    
    signupForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        // Get form data
        const fullName = document.getElementById('fullName').value.trim();
        const email = document.getElementById('email').value.trim();
        const phoneNumber = document.getElementById('phoneNumber').value.trim();
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const transactionPin = document.getElementById('transactionPin').value;
        
        // Validate inputs
        if (!fullName || !email || !phoneNumber || !password || !confirmPassword || !transactionPin) {
            showError('Please fill in all fields');
            return;
        }
        
        if (password !== confirmPassword) {
            showError('Passwords do not match');
            return;
        }
        
        if (password.length < 6) {
            showError('Password must be at least 6 characters long');
            return;
        }
        
        if (!/^[0-9]{10}$/.test(phoneNumber)) {
            showError('Please enter a valid 10-digit phone number');
            return;
        }
        
        if (!/^[0-9]{4}$/.test(transactionPin)) {
            showError('Transaction PIN must be exactly 4 digits');
            return;
        }
        
        // Show loader
        loader.classList.remove('hidden');
        hideError();
        hideSuccess();
        
        try {
            // Send signup request
            const response = await fetch(`${API_BASE_URL}/users/signup`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ fullName, email, phoneNumber, password, transactionPin })
            });
            
            const data = await response.json();
            
            if (data.success) {
                showSuccess('Account created successfully! Redirecting to login...');
                
                // Redirect to login page after 2 seconds
                setTimeout(() => {
                    window.location.href = 'login.html';
                }, 2000);
            } else {
                showError(data.message || 'Signup failed. Please try again.');
            }
        } catch (error) {
            console.error('Signup error:', error);
            showError('Unable to connect to server. Please try again later.');
        } finally {
            loader.classList.add('hidden');
        }
    });
    
    function showError(message) {
        errorMessage.textContent = message;
        errorMessage.classList.add('show');
        errorMessage.style.display = 'block';
    }
    
    function hideError() {
        errorMessage.classList.remove('show');
        errorMessage.style.display = 'none';
    }
    
    function showSuccess(message) {
        successMessage.textContent = message;
        successMessage.classList.add('show');
        successMessage.style.display = 'block';
    }
    
    function hideSuccess() {
        successMessage.classList.remove('show');
        successMessage.style.display = 'none';
    }
});