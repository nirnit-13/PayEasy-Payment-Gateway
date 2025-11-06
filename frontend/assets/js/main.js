// main.js - Landing page functionality

document.addEventListener('DOMContentLoaded', () => {
    console.log('PayEasy Payment Gateway Loaded');
    
    // Smooth scrolling for navigation links
    const navLinks = document.querySelectorAll('.nav a[href^="#"]');
    
    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const targetId = link.getAttribute('href').substring(1);
            const targetElement = document.getElementById(targetId);
            
            if (targetElement) {
                targetElement.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });
    
    // Animate phone mockup balance
    animateBalance();
});

// Animate the balance display in phone mockup
function animateBalance() {
    const balanceElement = document.querySelector('.mock-balance');
    if (!balanceElement) return;
    
    const targetBalance = 42350;
    let currentBalance = 0;
    const duration = 2000; // 2 seconds
    const steps = 60;
    const increment = targetBalance / steps;
    const stepDuration = duration / steps;
    
    const interval = setInterval(() => {
        currentBalance += increment;
        if (currentBalance >= targetBalance) {
            currentBalance = targetBalance;
            clearInterval(interval);
        }
        balanceElement.textContent = '₹' + currentBalance.toFixed(2);
    }, stepDuration);
}

// API Base URL
const API_BASE_URL = 'http://localhost:8080/api';