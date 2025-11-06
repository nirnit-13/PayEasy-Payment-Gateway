const API_BASE_URL = 'http://localhost:8080/api';
const userData = JSON.parse(sessionStorage.getItem('userData') || '{}');

// Check if coming from payment flow
const fromPayment = sessionStorage.getItem('fromPaymentFlow') === 'true';

if (!userData.userId) {
    window.location.href = 'login.html';
}

let selectedCardData = null;

document.addEventListener('DOMContentLoaded', () => {
    loadCards();
    
    document.getElementById('logoutBtn').addEventListener('click', () => {
        sessionStorage.clear();
        window.location.href = 'index.html';
    });
    
    const modal = document.getElementById('addCardModal');
    const addBtn = document.getElementById('addCardBtn');
    const closeBtn = document.querySelector('.close');
    
    addBtn.addEventListener('click', () => {
        modal.classList.remove('hidden');
        modal.classList.add('show');
    });
    
    closeBtn.addEventListener('click', () => {
        modal.classList.add('hidden');
        modal.classList.remove('show');
    });
    
    window.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.classList.add('hidden');
            modal.classList.remove('show');
        }
        if (e.target.id === 'cardActionPopup') {
            closeCardActionPopup();
        }
    });
    
    // Card number validation - only digits
    document.getElementById('cardNumber').addEventListener('input', (e) => {
        e.target.value = e.target.value.replace(/\D/g, '');
    });
    
    document.getElementById('addCardForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        await saveCard();
    });
});

async function loadCards() {
    try {
        const response = await fetch(`${API_BASE_URL}/cards/user/${userData.userId}`);
        const data = await response.json();
        
        if (data.success && data.cards.length > 0) {
            displayCards(data.cards);
        } else {
            document.getElementById('cardsContainer').innerHTML = 
                '<p class="no-data">No saved cards. Add your first card!</p>';
        }
    } catch (error) {
        console.error('Error loading cards:', error);
    }
}

function displayCards(cards) {
    const container = document.getElementById('cardsContainer');
    container.innerHTML = cards.map(card => `
        <div class="card-item" data-brand="${card.cardBrand}" onclick="handleCardClick(${card.id}, '${card.cardBrand}', '${card.cardNumber}', '${card.cardHolderName}', '${card.cardType}')">
            <div class="card-type-badge">${card.cardType}</div>
            <div class="card-chip"></div>
            <div class="card-brand">${card.cardBrand}</div>
            <div class="card-number">**** **** **** ${card.cardNumber}</div>
            <div class="card-details">
                <div class="card-holder">
                    <small>Cardholder</small>
                    <strong>${card.cardHolderName}</strong>
                </div>
                <div class="card-expiry">
                    <small>Expires</small>
                    <strong>${card.expiryMonth}/${card.expiryYear}</strong>
                </div>
            </div>
        </div>
    `).join('');
}

// Handle card click - Show popup for saved cards page, direct payment for payment flow
function handleCardClick(cardId, cardBrand, cardNumber, cardHolderName, cardType) {
    selectedCardData = {
        cardId: cardId,
        cardBrand: cardBrand,
        cardNumber: cardNumber,
        cardHolderName: cardHolderName,
        cardType: cardType
    };
    
    if (fromPayment) {
        // Coming from payment flow - directly go to payment
        payWithSelectedCard();
    } else {
        // On saved cards page - show action popup
        showCardActionPopup();
    }
}

function showCardActionPopup() {
    if (!selectedCardData) return;
    
    // Update popup content
    document.getElementById('actionCardBrand').textContent = selectedCardData.cardBrand;
    document.getElementById('actionCardNumber').textContent = selectedCardData.cardNumber;
    document.getElementById('actionCardHolder').textContent = selectedCardData.cardHolderName;
    
    // Set card color for preview
    const preview = document.getElementById('actionCardPreview');
    switch(selectedCardData.cardBrand) {
        case 'VISA':
            preview.style.background = 'linear-gradient(135deg, #1a1f71 0%, #2e3192 100%)';
            break;
        case 'MASTERCARD':
            preview.style.background = 'linear-gradient(135deg, #eb001b 0%, #f79e1b 100%)';
            break;
        case 'RUPAY':
            preview.style.background = 'linear-gradient(135deg, #097939 0%, #0a4d92 100%)';
            break;
        default:
            preview.style.background = 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)';
    }
    
    // Show popup
    document.getElementById('cardActionPopup').classList.add('show');
}

function closeCardActionPopup() {
    document.getElementById('cardActionPopup').classList.remove('show');
    selectedCardData = null;
}

function payWithSelectedCard() {
    if (!selectedCardData) return;
    
    // Store selected card info in session
    sessionStorage.setItem('selectedCard', JSON.stringify(selectedCardData));
    
    // Clear payment flow flag
    sessionStorage.removeItem('fromPaymentFlow');
    
    // Redirect to payment page
    window.location.href = 'pay-with-saved-card.html';
}

function deleteSelectedCard() {
    if (!selectedCardData) return;
    
    if (!confirm(`Delete ${selectedCardData.cardBrand} card ending in ${selectedCardData.cardNumber}?`)) {
        return;
    }
    
    deleteCard(selectedCardData.cardId);
    closeCardActionPopup();
}

async function saveCard() {
    const cardNumber = document.getElementById('cardNumber').value.trim();
    
    // Validate card number is exactly 16 digits
    if (cardNumber.length !== 16) {
        alert('Card number must be exactly 16 digits');
        return;
    }
    
    if (!/^\d{16}$/.test(cardNumber)) {
        alert('Card number must contain only digits');
        return;
    }
    
    try {
        const cardData = {
            userId: userData.userId,
            cardHolderName: document.getElementById('cardHolderName').value,
            cardNumber: cardNumber,
            cardType: document.getElementById('cardType').value,
            cardBrand: document.getElementById('cardBrand').value,
            expiryMonth: document.getElementById('expiryMonth').value,
            expiryYear: document.getElementById('expiryYear').value
        };
        
        const response = await fetch(`${API_BASE_URL}/cards/save`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cardData)
        });
        
        const data = await response.json();
        
        if (data.success) {
            alert('Card saved successfully!');
            document.getElementById('addCardModal').classList.add('hidden');
            document.getElementById('addCardModal').classList.remove('show');
            document.getElementById('addCardForm').reset();
            loadCards();
        } else {
            alert(data.message);
        }
    } catch (error) {
        console.error('Error saving card:', error);
        alert('Failed to save card');
    }
}

async function deleteCard(cardId) {
    try {
        const response = await fetch(`${API_BASE_URL}/cards/${cardId}`, {
            method: 'DELETE'
        });
        
        const data = await response.json();
        
        if (data.success) {
            alert('Card deleted successfully');
            loadCards();
        }
    } catch (error) {
        console.error('Error deleting card:', error);
        alert('Failed to delete card');
    }
}

// Make functions globally accessible
window.handleCardClick = handleCardClick;
window.closeCardActionPopup = closeCardActionPopup;
window.payWithSelectedCard = payWithSelectedCard;
window.deleteSelectedCard = deleteSelectedCard;