// home.js - Home page functionality

const API_BASE_URL = 'http://localhost:8080/api';

// Check if user is logged in
const userData = JSON.parse(sessionStorage.getItem('userData') || '{}');
if (!userData.userId) {
    window.location.href = 'login.html';
}

document.addEventListener('DOMContentLoaded', () => {
    // Set user name
    document.getElementById('userName').textContent = userData.fullName || 'User';
    
    // Load wallet balance
    loadWalletBalance();
    
    // Load recent transactions
    loadRecentTransactions();
    
    // Refresh button
    document.getElementById('refreshBtn').addEventListener('click', () => {
        loadWalletBalance();
        loadRecentTransactions();
    });
    
    // Logout button
    document.getElementById('logoutBtn').addEventListener('click', () => {
        sessionStorage.clear();
        window.location.href = 'index.html';
    });
});

// Load wallet balance
async function loadWalletBalance() {
    try {
        const response = await fetch(`${API_BASE_URL}/wallet/balance/${userData.userId}`);
        const data = await response.json();
        
        if (data.success) {
            const balance = parseFloat(data.balance).toFixed(2);
            document.getElementById('walletBalance').textContent = '₹' + balance;
            
            const updatedDate = new Date(data.updatedAt);
            document.getElementById('lastUpdated').textContent = updatedDate.toLocaleString('en-IN');
        }
    } catch (error) {
        console.error('Error loading wallet balance:', error);
        document.getElementById('walletBalance').textContent = '₹--';
    }
}

// Load recent transactions (last 3)
async function loadRecentTransactions() {
    try {
        const response = await fetch(`${API_BASE_URL}/transactions/history/${userData.userId}`);
        const data = await response.json();
        
        const transactionsList = document.getElementById('recentTransactionsList');
        
        if (data.success && data.transactions && data.transactions.length > 0) {
            const recentTxns = data.transactions.slice(0, 3);
            displayTransactions(recentTxns, transactionsList);
        } else {
            transactionsList.innerHTML = '<p class="no-data">No transactions yet. Start sending money!</p>';
        }
    } catch (error) {
        console.error('Error loading transactions:', error);
        document.getElementById('recentTransactionsList').innerHTML = 
            '<p class="error-text">Failed to load transactions</p>';
    }
}

// Display transactions
function displayTransactions(transactions, container) {
    container.innerHTML = transactions.map(txn => `
        <div class="transaction-item">
            <div class="txn-icon">💸</div>
            <div class="txn-details">
                <h5>${txn.recipientName}</h5>
                <p>${txn.recipientUPI}</p>
                <p class="txn-date">${new Date(txn.transactionDate).toLocaleString('en-IN')}</p>
            </div>
            <div class="txn-amount">
                <p class="amount-paid">-₹${txn.amount}</p>
                <p class="cashback-earned">+₹${txn.cashback} cashback</p>
            </div>
        </div>
    `).join('');
}

// Load recent payees
async function loadRecentPayees() {
    try {
        const response = await fetch(`${API_BASE_URL}/payees/recent/${userData.userId}`);
        const data = await response.json();
        
        if (data.success && data.payees.length > 0) {
            const payees = data.payees.slice(0, 5);
            // Display logic here
        }
    } catch (error) {
        console.error('Error loading payees:', error);
    }
}