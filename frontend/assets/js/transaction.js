const API_BASE_URL = 'http://localhost:8080/api';
const userData = JSON.parse(sessionStorage.getItem('userData') || '{}');

if (!userData.userId) window.location.href = 'login.html';

document.addEventListener('DOMContentLoaded', () => {
    loadBalance();
    loadRecentPayees();
    
    document.getElementById('logoutBtn').addEventListener('click', () => {
        sessionStorage.clear();
        window.location.href = 'index.html';
    });
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

async function loadRecentPayees() {
    try {
        const response = await fetch(`${API_BASE_URL}/payees/recent/${userData.userId}`);
        const data = await response.json();
        
        if (data.success && data.payees && data.payees.length > 0) {
            displayRecentPayees(data.payees.slice(0, 5));
        } else {
            document.getElementById('recentPayeesList').innerHTML = '<p class="no-data">No recent payees</p>';
        }
    } catch (error) {
        console.error('Error loading payees:', error);
    }
}

function displayRecentPayees(payees) {
    const list = document.getElementById('recentPayeesList');
    list.innerHTML = payees.map(payee => `
        <div class="payee-item" onclick="selectPayee('${payee.payeeType}')">
            <div class="payee-avatar">${payee.payeeName.charAt(0).toUpperCase()}</div>
            <div class="payee-details">
                <h5>${payee.payeeName}</h5>
                <p>${payee.payeeIdentifier} • ${payee.paymentCount} payments</p>
            </div>
        </div>
    `).join('');
}

function selectPayee(payeeType) {
    const paymentMap = {
        'UPI': 'pay-upi.html',
        'PHONE': 'pay-phone.html',
        'CARD': 'pay-card.html',
        'BANK': 'pay-bank.html',
        'QR': 'pay-qr.html'
    };
    
    window.location.href = paymentMap[payeeType] || 'pay-upi.html';
}