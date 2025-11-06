// login.js - Login page functionality

const API_BASE_URL = 'http://localhost:8080/api';

document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const errorMessage = document.getElementById('errorMessage');
    const loader = document.getElementById('loader');
    
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        // Get form data
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;
        
        // Validate inputs
        if (!email || !password) {
            showError('Please fill in all fields');
            return;
        }
        
        // Show loader
        loader.classList.remove('hidden');
        hideError();
        
        try {
            // Send login request
            const response = await fetch(`${API_BASE_URL}/users/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password })
            });
            
            const data = await response.json();
            
            if (data.success) {
                // Save user data to session storage
                sessionStorage.setItem('userData', JSON.stringify({
                    userId: data.userId,
                    email: data.email,
                    fullName: data.fullName,
                    phoneNumber: data.phoneNumber
                }));
                
                // Redirect to home page
                window.location.href = 'home.html';
            } else {
                showError(data.message || 'Login failed. Please check your credentials.');
            }
        } catch (error) {
            console.error('Login error:', error);
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
});