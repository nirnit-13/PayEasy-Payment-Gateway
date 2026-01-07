// Auto-detect environment and set API base URL
const isLocalHost = window.location.hostname === 'localhost' || 
                    window.location.hostname === '127.0.0.1' ||
                    window.location.hostname === '';

// API Configuration
const API_BASE_URL = isLocalHost 
    ? 'http://localhost:8080/api'  // Local development
    : 'https://payeasy-backend.onrender.com/api';  // Production - UPDATE THIS!

// Debug logging
console.log('=== PayEasy Configuration ===');
console.log('Environment:', isLocalHost ? 'Development' : 'Production');
console.log('API Base URL:', API_BASE_URL);
console.log('Frontend URL:', window.location.origin);
console.log('===========================');

// Export for use in other scripts
window.APP_CONFIG = {
    API_BASE_URL: API_BASE_URL,
    IS_PRODUCTION: !isLocalHost,
    VERSION: '1.0.0'
};

/**
 * DEPLOYMENT INSTRUCTIONS:
 * 
 * 1. After deploying backend to Render, get your backend URL
 * 2. Update line 13 with your actual Render URL:
 *    'https://YOUR-APP-NAME.onrender.com/api'
 * 
 * 3. This file should be included FIRST in all HTML files:
 *    <script src="assets/js/config.js"></script>
 * 
 * 4. Remove "const API_BASE_URL" declarations from individual JS files
 *    They will automatically use this global configuration
 */