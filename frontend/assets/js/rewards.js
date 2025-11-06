// rewards.js - Rewards page functionality

const API_BASE_URL = 'http://localhost:8080/api';

// Check if user is logged in
const userData = JSON.parse(sessionStorage.getItem('userData') || '{}');
if (!userData.userId) {
    window.location.href = 'login.html';
}

let allRewards = [];
let currentFilter = 'all';

document.addEventListener('DOMContentLoaded', () => {
    // Load rewards
    loadRewards();
    
    // Logout button
    document.getElementById('logoutBtn').addEventListener('click', () => {
        sessionStorage.clear();
        window.location.href = 'index.html';
    });
    
    // Refresh button
    document.getElementById('refreshBtn').addEventListener('click', loadRewards);
    
    // Filter tabs
    const tabButtons = document.querySelectorAll('.tab-btn');
    tabButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            // Update active tab
            tabButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            
            // Filter rewards
            currentFilter = btn.getAttribute('data-filter');
            filterAndDisplayRewards();
        });
    });
});

// Load all rewards
async function loadRewards() {
    try {
        const response = await fetch(`${API_BASE_URL}/rewards/all/${userData.userId}`);
        const data = await response.json();
        
        if (data.success && data.rewards) {
            allRewards = data.rewards;
            updateStats();
            filterAndDisplayRewards();
        } else {
            displayNoRewards();
        }
    } catch (error) {
        console.error('Error loading rewards:', error);
        document.getElementById('rewardsList').innerHTML = 
            '<p class="no-data">Failed to load rewards</p>';
    }
}

// Update statistics
function updateStats() {
    const total = allRewards.length;
    const active = allRewards.filter(r => !r.isUsed).length;
    
    document.getElementById('totalRewards').textContent = total;
    document.getElementById('activeRewards').textContent = active;
}

// Filter and display rewards
function filterAndDisplayRewards() {
    let filteredRewards = allRewards;
    
    if (currentFilter === 'unused') {
        filteredRewards = allRewards.filter(r => !r.isUsed);
    } else if (currentFilter === 'used') {
        filteredRewards = allRewards.filter(r => r.isUsed);
    }
    
    displayRewards(filteredRewards);
}

// Display rewards
function displayRewards(rewards) {
    const rewardsList = document.getElementById('rewardsList');
    
    if (rewards.length === 0) {
        displayNoRewards();
        return;
    }
    
    rewardsList.innerHTML = rewards.map(reward => `
        <div class="reward-card ${reward.isUsed ? 'used' : ''}">
            <div class="reward-header">
                <div class="reward-icon">🎁</div>
                <div class="reward-badge">${reward.isUsed ? 'USED' : 'ACTIVE'}</div>
            </div>
            
            <div class="reward-content">
                <h3 class="reward-title">${reward.rewardTitle}</h3>
                <p class="reward-description">${reward.rewardDescription}</p>
                <div class="reward-discount">${reward.discountPercent}% OFF</div>
            </div>
            
            <div class="coupon-code">
                <p class="coupon-label">Coupon Code</p>
                <p class="coupon-value">${reward.couponCode}</p>
            </div>
            
            <div class="reward-footer">
                <div class="expiry-date">
                    <span>⏰</span>
                    <span>Expires: ${formatDate(reward.expiryDate)}</span>
                </div>
                ${!reward.isUsed ? 
                    `<button class="btn-use-reward" onclick="useReward(${reward.id})">Use Now</button>` 
                    : '<span>✓ Redeemed</span>'
                }
            </div>
        </div>
    `).join('');
}

// Display no rewards message
function displayNoRewards() {
    const rewardsList = document.getElementById('rewardsList');
    rewardsList.innerHTML = `
        <div class="no-rewards">
            <div class="no-rewards-icon">🎁</div>
            <h4>No Rewards Yet</h4>
            <p>Complete transactions to earn exciting rewards and coupons!</p>
            <a href="transaction.html" class="btn-primary">Make a Payment</a>
        </div>
    `;
}

// Use/redeem reward
async function useReward(rewardId) {
    if (!confirm('Are you sure you want to use this reward?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/rewards/use/${rewardId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        });
        
        const data = await response.json();
        
        if (data.success) {
            alert('Reward used successfully!');
            loadRewards(); // Reload rewards
        } else {
            alert(data.message || 'Failed to use reward');
        }
    } catch (error) {
        console.error('Error using reward:', error);
        alert('Failed to use reward. Please try again.');
    }
}

// Format date
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-IN', {
        day: '2-digit',
        month: 'short',
        year: 'numeric'
    });
}

// Make useReward function globally accessible
window.useReward = useReward;