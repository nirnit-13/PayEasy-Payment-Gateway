const API_BASE_URL = 'http://localhost:8080/api';
const userData = JSON.parse(sessionStorage.getItem('userData') || '{}');

if (!userData.userId) window.location.href = 'login.html';

document.addEventListener('DOMContentLoaded', () => {
    loadBalance();
    document.getElementById('logoutBtn').addEventListener('click', () => {
        sessionStorage.clear();
        window.location.href = 'index.html';
    });
    setupSwipeToPay();
});

async function loadBalance() {
    try {
        const response = await fetch(`${API_BASE_URL}/wallet/balance/${userData.userId}`);
        const data = await response.json();
        if (data.success) {
            document.getElementById('availableBalance').textContent = '₹' + parseFloat(data.balance).toFixed(2);
        }
    } catch (error) {
        console.error('Error loading balance:', error);
    }
}

function setupSwipeToPay() {
    const swipeContainer = document.querySelector('.swipe-container');
    const swipeButton = document.getElementById('swipeButton');
    let isDragging = false;
    let startX = 0;
    let currentX = 0;
    const maxSwipe = swipeContainer.offsetWidth - swipeButton.offsetWidth - 10;

    swipeButton.addEventListener('mousedown', startDrag);
    document.addEventListener('mousemove', drag);
    document.addEventListener('mouseup', endDrag);
    swipeButton.addEventListener('touchstart', startDrag);
    document.addEventListener('touchmove', drag);
    document.addEventListener('touchend', endDrag);

    function startDrag(e) {
        isDragging = true;
        startX = e.type === 'touchstart' ? e.touches[0].clientX : e.clientX;
        swipeButton.style.transition = 'none';
    }

    function drag(e) {
        if (!isDragging) return;
        e.preventDefault();
        const clientX = e.type === 'touchmove' ? e.touches[0].clientX : e.clientX;
        currentX = clientX - startX;
        if (currentX < 0) currentX = 0;
        if (currentX > maxSwipe) currentX = maxSwipe;
        swipeButton.style.left = (5 + currentX) + 'px';
    }

    function endDrag() {
        if (!isDragging) return;
        isDragging = false;
        swipeButton.style.transition = 'left 0.3s';
        
        if (currentX > maxSwipe * 0.8) {
            swipeButton.style.left = (maxSwipe + 5) + 'px';
            swipeContainer.classList.add('completed');
            setTimeout(() => processPayment(), 300);
        } else {
            swipeButton.style.left = '5px';
            currentX = 0;
        }
    }
}

async function processPayment() {
    const upiId = document.getElementById('upiId').value.trim();
    const amount = document.getElementById('amount').value.trim();
    const description = document.getElementById('description').value.trim() || 'UPI Payment';
    const pin = document.getElementById('pin').value.trim();

    console.log('=== PAYMENT FORM DEBUG ===');
    console.log('UPI ID:', upiId);
    console.log('Amount:', amount);
    console.log('PIN entered:', pin);
    console.log('PIN length:', pin.length);
    console.log('========================');

    if (!upiId || !amount || !pin) {
        showError('Please fill all required fields');
        resetSwipe();
        return;
    }

    if (pin.length !== 4) {
        showError('PIN must be exactly 4 digits');
        resetSwipe();
        return;
    }

    try {
        console.log('Verifying PIN...');
        const pinResponse = await fetch(`${API_BASE_URL}/users/verify-pin`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ 
                userId: userData.userId, 
                pin: pin
            })
        });
        
        const pinData = await pinResponse.json();
        console.log('PIN verification response:', pinData);

        if (!pinData.success) {
            showError('Invalid PIN. Please try again.');
            resetSwipe();
            return;
        }

        console.log('PIN verified successfully. Processing payment...');
        const response = await fetch(`${API_BASE_URL}/transactions/pay`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                userId: userData.userId,
                recipientName: 'UPI User',
                recipientUPI: upiId,
                amount: parseFloat(amount),
                description,
                paymentMethod: 'UPI'
            })
        });

        const data = await response.json();
        console.log('Payment response:', data);

        if (data.success) {
            sessionStorage.setItem('lastTransaction', JSON.stringify(data));
            window.location.href = 'success.html';
        } else {
            showError(data.message || 'Payment failed');
            resetSwipe();
        }
    } catch (error) {
        console.error('Payment error:', error);
        showError('Payment failed. Please try again.');
        resetSwipe();
    }
}

function showError(message) {
    const errorMessage = document.getElementById('errorMessage');
    errorMessage.textContent = message;
    errorMessage.style.display = 'block';
    setTimeout(() => errorMessage.style.display = 'none', 5000);
}

function resetSwipe() {
    const swipeContainer = document.querySelector('.swipe-container');
    const swipeButton = document.getElementById('swipeButton');
    swipeContainer.classList.remove('completed');
    swipeButton.style.left = '5px';
}