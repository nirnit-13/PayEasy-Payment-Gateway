# 💳 PayEasy - Payment Gateway Web Application

![Java](https://img.shields.io/badge/Java-17-orange?style=flat&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen?style=flat&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat&logo=mysql)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black)

A **full-stack simulated payment gateway** web application built for college project demonstration. PayEasy mimics real-world payment platforms like Google Pay and PhonePe, featuring wallet management, multiple payment methods, cashback rewards, and transaction tracking.

> **⚠️ Important**: This is a **simulation project** for educational purposes only. No real money transactions or live payment APIs are involved.

**🔗 GitHub Repository**: [https://github.com/nirnit-13/PayEasy-Payment-Gateway](https://github.com/nirnit-13/PayEasy-Payment-Gateway)

---

## 📋 Table of Contents

- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
- [Running the Application](#-running-the-application)
- [API Documentation](#-api-documentation)
- [User Guide](#-user-guide)
- [Screenshots](#-screenshots)
- [Security Notes](#-security-notes)
- [Troubleshooting](#-troubleshooting)
- [Contributors](#-contributors)
- [License](#-license)

---

## ✨ Features

### 🔐 User Management
- ✅ User registration with email and phone validation
- ✅ Secure login system
- ✅ 4-digit transaction PIN protection
- ✅ User profile with QR code generation

### 💰 Wallet System
- ✅ Auto-generated wallet on signup (₹20,000 - ₹50,000 random balance)
- ✅ Real-time balance updates
- ✅ Transaction history tracking
- ✅ Cashback rewards (1% - 5% on every transaction)

### 💳 Payment Methods
- ✅ **UPI Payment** - Pay to any UPI ID
- ✅ **Phone Number** - Send money via mobile number
- ✅ **Saved Cards** - Manage and pay with saved cards
- ✅ **Bank Transfer** - IFSC-based transfers
- ✅ **QR Code** - Upload and scan QR codes

### 🎁 Rewards System
- ✅ Automatic coupon generation (70% chance per transaction)
- ✅ Reward management (view, filter, redeem)
- ✅ Expiry tracking (30-day validity)
- ✅ Discount percentages (5% - 20%)

### 🎨 User Interface
- ✅ Modern gradient-based design
- ✅ Responsive layout (mobile-friendly)
- ✅ Interactive "Swipe to Pay" gesture
- ✅ Real-time notifications
- ✅ PDF receipt generation

### 🔒 Security Features
- ✅ PIN verification before transactions
- ✅ Card number masking (only last 4 digits stored)
- ✅ Session-based authentication
- ✅ CORS configuration for API security

---

## 🛠 Technology Stack

### Backend
| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 | Programming Language |
| **Spring Boot** | 3.2.0 | REST API Framework |
| **Spring Data JPA** | 3.2.0 | ORM & Database Access |
| **MySQL** | 8.0.44 | Relational Database |
| **Maven** | 3.6+ | Dependency Management |
| **ZXing** | 3.5.3 | QR Code Generation |
| **Lombok** | - | Boilerplate Reduction |

### Frontend
| Technology | Purpose |
|------------|---------|
| **HTML5** | Structure |
| **CSS3** | Styling & Animations |
| **JavaScript (ES6+)** | Client-side Logic |
| **jsPDF** | PDF Generation |

### Development Tools
- **IntelliJ IDEA** - Backend Development
- **VS Code** - Frontend Development
- **MySQL Workbench** - Database Management
- **Postman** - API Testing
- **Git & GitHub** - Version Control

---

## 📂 Project Structure

```
PayEasy-Payment-Gateway/
│
├── index.html                     # Landing page
├── login.html                     # Login page
├── signup.html                    # Registration page
├── home.html                      # Dashboard
├── transaction.html               # Payment method selection
├── pay-upi.html                   # UPI payment
├── pay-phone.html                 # Phone payment
├── pay-card.html                  # Card payment redirect
├── pay-with-saved-card.html       # Saved card payment
├── pay-bank.html                  # Bank transfer
├── pay-qr.html                    # QR code payment
├── cards.html                     # Saved cards management
├── history.html                   # Transaction history
├── rewards.html                   # Rewards & coupons
├── profile.html                   # User profile
├── success.html                   # Payment success
├── README.md                      # Documentation
├── .gitignore                     # Git ignore rules
│
├── assets/
│   ├── css/
│   │   ├── style.css              # Global styles
│   │   ├── login.css              # Auth pages styles
│   │   ├── signup.css             # Signup styles
│   │   ├── home.css               # Dashboard styles
│   │   ├── transaction.css        # Payment styles
│   │   ├── cards.css              # Card management styles
│   │   └── rewards.css            # Rewards styles
│   │
│   └── js/
│       ├── main.js                # Landing page logic
│       ├── login.js               # Login logic
│       ├── signup.js              # Registration logic
│       ├── home.js                # Dashboard logic
│       ├── transaction.js         # Payment selection logic
│       ├── pay-upi.js             # UPI payment logic
│       ├── pay-phone.js           # Phone payment logic
│       ├── pay-card.js            # Card payment redirect
│       ├── pay-with-saved-card.js # Saved card payment
│       ├── pay-bank.js            # Bank transfer logic
│       ├── pay-qr.js              # QR payment logic
│       ├── cards.js               # Card management logic
│       └── rewards.js             # Rewards logic
│
└── backend/
    ├── pom.xml                    # Maven configuration
    │
    └── src/main/
        ├── java/com/paymentgateway/
        │   ├── PaymentGatewayApplication.java
        │   │
        │   ├── config/
        │   │   ├── CORSConfig.java
        │   │   └── CORSFilter.java
        │   │
        │   ├── controller/
        │   │   ├── UserController.java
        │   │   ├── WalletController.java
        │   │   ├── TransactionController.java
        │   │   ├── RewardController.java
        │   │   ├── CardController.java
        │   │   └── PayeeController.java
        │   │
        │   ├── model/
        │   │   ├── User.java
        │   │   ├── Wallet.java
        │   │   ├── Transaction.java
        │   │   ├── Reward.java
        │   │   ├── SavedCard.java
        │   │   ├── Card.java
        │   │   └── RecentPayee.java
        │   │
        │   ├── repository/
        │   │   ├── UserRepository.java
        │   │   ├── WalletRepository.java
        │   │   ├── TransactionRepository.java
        │   │   ├── RewardRepository.java
        │   │   ├── SavedCardRepository.java
        │   │   └── RecentPayeeRepository.java
        │   │
        │   └── service/
        │       ├── UserService.java
        │       ├── WalletService.java
        │       ├── TransactionService.java
        │       ├── RewardService.java
        │       ├── CardService.java
        │       ├── PayeeService.java
        │       └── QRCodeService.java
        │
        └── resources/
            ├── application-template.properties
            └── data.sql (optional)
```

---

## 📋 Prerequisites

Before running this project, ensure you have:

### Required Software

1. **Java Development Kit (JDK) 17+**
   - Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
   - Verify: `java -version`

2. **Maven 3.6+**
   - Download: [Apache Maven](https://maven.apache.org/download.cgi)
   - Verify: `mvn -version`

3. **MySQL 8.0+**
   - Download: [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)
   - Verify: MySQL service is running

4. **IntelliJ IDEA** (Recommended)
   - Download: [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
   - Community Edition is sufficient

5. **VS Code** with Live Server Extension
   - Download: [Visual Studio Code](https://code.visualstudio.com/)
   - Extension: Live Server by Ritwick Dey

6. **Git**
   - Download: [Git SCM](https://git-scm.com/)

### System Requirements
- **OS**: Windows 10/11, macOS, or Linux
- **RAM**: Minimum 4GB (8GB recommended)
- **Disk Space**: At least 2GB free space

---

## 🚀 Installation & Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/nirnit-13/PayEasy-Payment-Gateway.git
cd PayEasy-Payment-Gateway
```

### Step 2: Database Setup

1. **Start MySQL Server**
   ```bash
   # Windows
   net start MySQL80
   
   # macOS/Linux
   sudo systemctl start mysql
   ```

2. **Create Database**
   ```sql
   -- Open MySQL Workbench or command line
   mysql -u root -p
   
   -- Create database
   CREATE DATABASE payment_db;
   
   -- Verify
   SHOW DATABASES;
   ```

3. **Configure Database Credentials**
   
   Navigate to `backend/src/main/resources/`
   
   Copy `application-template.properties` to `application.properties`:
   ```bash
   cp application-template.properties application.properties
   ```
   
   Edit `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/payment_db
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```

### Step 3: Backend Setup

1. **Open Project in IntelliJ IDEA**
   - File → Open → Select `backend` folder
   - Wait for Maven to download dependencies (may take 2-5 minutes)

2. **Build the Project**
   ```bash
   cd backend
   mvn clean install
   ```

3. **Run the Application**
   - Option 1: Run `PaymentGatewayApplication.java` from IntelliJ
   - Option 2: Command line
     ```bash
     mvn spring-boot:run
     ```

4. **Verify Backend is Running**
   - Console should show: "Payment Gateway Application Started Successfully!"
   - Default port: `http://localhost:8080`

### Step 4: Frontend Setup

1. **Open Frontend in VS Code**
   - Open VS Code
   - File → Open Folder → Select project root folder

2. **Install Live Server Extension**
   - VS Code → Extensions → Search "Live Server" → Install

3. **Start Frontend**
   - Right-click on `index.html` → "Open with Live Server"
   - Default URL: `http://localhost:5500`

---

## 🎮 Running the Application

### Starting the Complete Application

#### Terminal 1: Backend
```bash
cd backend
mvn spring-boot:run
```
**Output**: Server running on `http://localhost:8080`

#### Terminal 2: Frontend
- Right-click `index.html` in VS Code → "Open with Live Server"
**Output**: Application running on `http://localhost:5500`

### Access the Application

1. Open browser and navigate to: `http://localhost:5500`
2. Click **"Sign Up"** to create a new account
3. Fill in the registration form
4. Login with your credentials
5. Start making payments!

---

## 📡 API Documentation

### Base URL
```
http://localhost:8080/api
```

### User APIs

#### Sign Up
```http
POST /users/signup
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "phoneNumber": "9876543210",
  "password": "password123",
  "transactionPin": "1234"
}
```

#### Login
```http
POST /users/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

#### Verify PIN
```http
POST /users/verify-pin
Content-Type: application/json

{
  "userId": 1,
  "pin": "1234"
}
```

### Wallet APIs

#### Get Balance
```http
GET /wallet/balance/{userId}
```

### Transaction APIs

#### Process Payment
```http
POST /transactions/pay
Content-Type: application/json

{
  "userId": 1,
  "recipientName": "Amazon",
  "recipientUPI": "amazon@upi",
  "amount": 1500.00,
  "description": "Shopping",
  "paymentMethod": "UPI"
}
```

#### Get Transaction History
```http
GET /transactions/history/{userId}
```

For complete API documentation, check the controller files in the source code.

---

## 📖 User Guide

### Getting Started

#### 1. Create Account
1. Navigate to homepage
2. Click **"Sign Up"**
3. Fill registration form
4. Set a 4-digit transaction PIN
5. Submit

#### 2. Login
1. Enter email and password
2. Click **"Login"**
3. Redirected to dashboard

### Making Payments

#### UPI Payment
1. Dashboard → **"Send Money"** → **"UPI Payment"**
2. Enter UPI ID
3. Enter amount
4. Enter PIN
5. Swipe to Pay

#### Card Payment
1. Dashboard → **"Send Money"** → **"Card Payment"**
2. Select saved card or add new
3. Enter payment details
4. Swipe to Pay

---

## 📸 Screenshots

*(Add your screenshots here)*

---

## 🔒 Security Notes

### ⚠️ Important Disclaimer

This is a **college project simulation**. For production use, implement:

- ✅ Password hashing (BCrypt)
- ✅ JWT authentication
- ✅ HTTPS/TLS encryption
- ✅ Input validation
- ✅ Environment variables
- ✅ Rate limiting
- ✅ CSRF protection

---

## 🛠 Troubleshooting

### Backend Issues

**Port 8080 Already in Use**
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

**MySQL Connection Failed**
- Check MySQL service is running
- Verify credentials in `application.properties`
- Ensure database `payment_db` exists

### Frontend Issues

**CORS Errors**
- Verify backend is running on port 8080
- Clear browser cache

**API Calls Failing**
- Check Network tab in DevTools
- Verify backend console for errors

---

## 👥 Contributors

**Lead Developer**
- Name: Nirnit
- GitHub: [@nirnit-13](https://github.com/nirnit-13)
- Repository: [PayEasy-Payment-Gateway](https://github.com/nirnit-13/PayEasy-Payment-Gateway)

### Institution
- **College**: [Your College Name]
- **Department**: Computer Science & Engineering
- **Year**: 2024-2025

---

## 📄 License

This project is created for **educational purposes** as a college project demonstration.

### Attribution
```
PayEasy - Payment Gateway Simulation
Repository: https://github.com/nirnit-13/PayEasy-Payment-Gateway
Created by: Nirnit
```

---

## 🙏 Acknowledgments

### Technologies & Libraries
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MySQL](https://www.mysql.com/)
- [ZXing](https://github.com/zxing/zxing)
- [jsPDF](https://github.com/parallax/jsPDF)

### Inspiration
- Google Pay
- PhonePe
- Paytm

---

## 📞 Support

### For Issues
- **GitHub Issues**: [Create an issue](https://github.com/nirnit-13/PayEasy-Payment-Gateway/issues)
- **Documentation**: Check README and source code comments

---

## ⭐ Show Your Support

If you found this project helpful:

- ⭐ **Star** the repository on [GitHub](https://github.com/nirnit-13/PayEasy-Payment-Gateway)
- 🍴 **Fork** it for your own learning
- 📢 **Share** it with classmates

---

**Made with ❤️ for learning and education**

**PayEasy © 2025**

---

*Last Updated: January 2025*

*Repository: [https://github.com/nirnit-13/PayEasy-Payment-Gateway](https://github.com/nirnit-13/PayEasy-Payment-Gateway)*# 💳 PayEasy - Payment Gateway Web Application

![Java](https://img.shields.io/badge/Java-17-orange?style=flat&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen?style=flat&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat&logo=mysql)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black)

A **full-stack simulated payment gateway** web application built for college project demonstration. PayEasy mimics real-world payment platforms like Google Pay and PhonePe, featuring wallet management, multiple payment methods, cashback rewards, and transaction tracking.

> **⚠️ Important**: This is a **simulation project** for educational purposes only. No real money transactions or live payment APIs are involved.

**🔗 GitHub Repository**: [https://github.com/nirnit-13/PayEasy-Payment-Gateway](https://github.com/nirnit-13/PayEasy-Payment-Gateway)

---

## 📋 Table of Contents

- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
- [Running the Application](#-running-the-application)
- [API Documentation](#-api-documentation)
- [User Guide](#-user-guide)
- [Screenshots](#-screenshots)
- [Security Notes](#-security-notes)
- [Troubleshooting](#-troubleshooting)
- [Contributors](#-contributors)
- [License](#-license)

---

## ✨ Features

### 🔐 User Management
- ✅ User registration with email and phone validation
- ✅ Secure login system
- ✅ 4-digit transaction PIN protection
- ✅ User profile with QR code generation

### 💰 Wallet System
- ✅ Auto-generated wallet on signup (₹20,000 - ₹50,000 random balance)
- ✅ Real-time balance updates
- ✅ Transaction history tracking
- ✅ Cashback rewards (1% - 5% on every transaction)

### 💳 Payment Methods
- ✅ **UPI Payment** - Pay to any UPI ID
- ✅ **Phone Number** - Send money via mobile number
- ✅ **Saved Cards** - Manage and pay with saved cards
- ✅ **Bank Transfer** - IFSC-based transfers
- ✅ **QR Code** - Upload and scan QR codes

### 🎁 Rewards System
- ✅ Automatic coupon generation (70% chance per transaction)
- ✅ Reward management (view, filter, redeem)
- ✅ Expiry tracking (30-day validity)
- ✅ Discount percentages (5% - 20%)

### 🎨 User Interface
- ✅ Modern gradient-based design
- ✅ Responsive layout (mobile-friendly)
- ✅ Interactive "Swipe to Pay" gesture
- ✅ Real-time notifications
- ✅ PDF receipt generation

### 🔒 Security Features
- ✅ PIN verification before transactions
- ✅ Card number masking (only last 4 digits stored)
- ✅ Session-based authentication
- ✅ CORS configuration for API security

---

## 🛠 Technology Stack

### Backend
| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 | Programming Language |
| **Spring Boot** | 3.2.0 | REST API Framework |
| **Spring Data JPA** | 3.2.0 | ORM & Database Access |
| **MySQL** | 8.0.44 | Relational Database |
| **Maven** | 3.6+ | Dependency Management |
| **ZXing** | 3.5.3 | QR Code Generation |
| **Lombok** | - | Boilerplate Reduction |

### Frontend
| Technology | Purpose |
|------------|---------|
| **HTML5** | Structure |
| **CSS3** | Styling & Animations |
| **JavaScript (ES6+)** | Client-side Logic |
| **jsPDF** | PDF Generation |

### Development Tools
- **IntelliJ IDEA** - Backend Development
- **VS Code** - Frontend Development
- **MySQL Workbench** - Database Management
- **Postman** - API Testing
- **Git & GitHub** - Version Control

---

## 📂 Project Structure

```
PayEasy-Payment-Gateway/
│
├── index.html                     # Landing page
├── login.html                     # Login page
├── signup.html                    # Registration page
├── home.html                      # Dashboard
├── transaction.html               # Payment method selection
├── pay-upi.html                   # UPI payment
├── pay-phone.html                 # Phone payment
├── pay-card.html                  # Card payment redirect
├── pay-with-saved-card.html       # Saved card payment
├── pay-bank.html                  # Bank transfer
├── pay-qr.html                    # QR code payment
├── cards.html                     # Saved cards management
├── history.html                   # Transaction history
├── rewards.html                   # Rewards & coupons
├── profile.html                   # User profile
├── success.html                   # Payment success
├── README.md                      # Documentation
├── .gitignore                     # Git ignore rules
│
├── assets/
│   ├── css/
│   │   ├── style.css              # Global styles
│   │   ├── login.css              # Auth pages styles
│   │   ├── signup.css             # Signup styles
│   │   ├── home.css               # Dashboard styles
│   │   ├── transaction.css        # Payment styles
│   │   ├── cards.css              # Card management styles
│   │   └── rewards.css            # Rewards styles
│   │
│   └── js/
│       ├── main.js                # Landing page logic
│       ├── login.js               # Login logic
│       ├── signup.js              # Registration logic
│       ├── home.js                # Dashboard logic
│       ├── transaction.js         # Payment selection logic
│       ├── pay-upi.js             # UPI payment logic
│       ├── pay-phone.js           # Phone payment logic
│       ├── pay-card.js            # Card payment redirect
│       ├── pay-with-saved-card.js # Saved card payment
│       ├── pay-bank.js            # Bank transfer logic
│       ├── pay-qr.js              # QR payment logic
│       ├── cards.js               # Card management logic
│       └── rewards.js             # Rewards logic
│
└── backend/
    ├── pom.xml                    # Maven configuration
    │
    └── src/main/
        ├── java/com/paymentgateway/
        │   ├── PaymentGatewayApplication.java
        │   │
        │   ├── config/
        │   │   ├── CORSConfig.java
        │   │   └── CORSFilter.java
        │   │
        │   ├── controller/
        │   │   ├── UserController.java
        │   │   ├── WalletController.java
        │   │   ├── TransactionController.java
        │   │   ├── RewardController.java
        │   │   ├── CardController.java
        │   │   └── PayeeController.java
        │   │
        │   ├── model/
        │   │   ├── User.java
        │   │   ├── Wallet.java
        │   │   ├── Transaction.java
        │   │   ├── Reward.java
        │   │   ├── SavedCard.java
        │   │   ├── Card.java
        │   │   └── RecentPayee.java
        │   │
        │   ├── repository/
        │   │   ├── UserRepository.java
        │   │   ├── WalletRepository.java
        │   │   ├── TransactionRepository.java
        │   │   ├── RewardRepository.java
        │   │   ├── SavedCardRepository.java
        │   │   └── RecentPayeeRepository.java
        │   │
        │   └── service/
        │       ├── UserService.java
        │       ├── WalletService.java
        │       ├── TransactionService.java
        │       ├── RewardService.java
        │       ├── CardService.java
        │       ├── PayeeService.java
        │       └── QRCodeService.java
        │
        └── resources/
            ├── application-template.properties
            └── data.sql (optional)
```

---

## 📋 Prerequisites

Before running this project, ensure you have:

### Required Software

1. **Java Development Kit (JDK) 17+**
   - Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
   - Verify: `java -version`

2. **Maven 3.6+**
   - Download: [Apache Maven](https://maven.apache.org/download.cgi)
   - Verify: `mvn -version`

3. **MySQL 8.0+**
   - Download: [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)
   - Verify: MySQL service is running

4. **IntelliJ IDEA** (Recommended)
   - Download: [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
   - Community Edition is sufficient

5. **VS Code** with Live Server Extension
   - Download: [Visual Studio Code](https://code.visualstudio.com/)
   - Extension: Live Server by Ritwick Dey

6. **Git**
   - Download: [Git SCM](https://git-scm.com/)

### System Requirements
- **OS**: Windows 10/11, macOS, or Linux
- **RAM**: Minimum 4GB (8GB recommended)
- **Disk Space**: At least 2GB free space

---

## 🚀 Installation & Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/nirnit-13/PayEasy-Payment-Gateway.git
cd PayEasy-Payment-Gateway
```

### Step 2: Database Setup

1. **Start MySQL Server**
   ```bash
   # Windows
   net start MySQL80
   
   # macOS/Linux
   sudo systemctl start mysql
   ```

2. **Create Database**
   ```sql
   -- Open MySQL Workbench or command line
   mysql -u root -p
   
   -- Create database
   CREATE DATABASE payment_db;
   
   -- Verify
   SHOW DATABASES;
   ```

3. **Configure Database Credentials**
   
   Navigate to `backend/src/main/resources/`
   
   Copy `application-template.properties` to `application.properties`:
   ```bash
   cp application-template.properties application.properties
   ```
   
   Edit `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/payment_db
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```

### Step 3: Backend Setup

1. **Open Project in IntelliJ IDEA**
   - File → Open → Select `backend` folder
   - Wait for Maven to download dependencies (may take 2-5 minutes)

2. **Build the Project**
   ```bash
   cd backend
   mvn clean install
   ```

3. **Run the Application**
   - Option 1: Run `PaymentGatewayApplication.java` from IntelliJ
   - Option 2: Command line
     ```bash
     mvn spring-boot:run
     ```

4. **Verify Backend is Running**
   - Console should show: "Payment Gateway Application Started Successfully!"
   - Default port: `http://localhost:8080`

### Step 4: Frontend Setup

1. **Open Frontend in VS Code**
   - Open VS Code
   - File → Open Folder → Select project root folder

2. **Install Live Server Extension**
   - VS Code → Extensions → Search "Live Server" → Install

3. **Start Frontend**
   - Right-click on `index.html` → "Open with Live Server"
   - Default URL: `http://localhost:5500`

---

## 🎮 Running the Application

### Starting the Complete Application

#### Terminal 1: Backend
```bash
cd backend
mvn spring-boot:run
```
**Output**: Server running on `http://localhost:8080`

#### Terminal 2: Frontend
- Right-click `index.html` in VS Code → "Open with Live Server"
**Output**: Application running on `http://localhost:5500`

### Access the Application

1. Open browser and navigate to: `http://localhost:5500`
2. Click **"Sign Up"** to create a new account
3. Fill in the registration form
4. Login with your credentials
5. Start making payments!

---

## 📡 API Documentation

### Base URL
```
http://localhost:8080/api
```

### User APIs

#### Sign Up
```http
POST /users/signup
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "phoneNumber": "9876543210",
  "password": "password123",
  "transactionPin": "1234"
}
```

#### Login
```http
POST /users/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

#### Verify PIN
```http
POST /users/verify-pin
Content-Type: application/json

{
  "userId": 1,
  "pin": "1234"
}
```

### Wallet APIs

#### Get Balance
```http
GET /wallet/balance/{userId}
```

### Transaction APIs

#### Process Payment
```http
POST /transactions/pay
Content-Type: application/json

{
  "userId": 1,
  "recipientName": "Amazon",
  "recipientUPI": "amazon@upi",
  "amount": 1500.00,
  "description": "Shopping",
  "paymentMethod": "UPI"
}
```

#### Get Transaction History
```http
GET /transactions/history/{userId}
```

For complete API documentation, check the controller files in the source code.

---

## 📖 User Guide

### Getting Started

#### 1. Create Account
1. Navigate to homepage
2. Click **"Sign Up"**
3. Fill registration form
4. Set a 4-digit transaction PIN
5. Submit

#### 2. Login
1. Enter email and password
2. Click **"Login"**
3. Redirected to dashboard

### Making Payments

#### UPI Payment
1. Dashboard → **"Send Money"** → **"UPI Payment"**
2. Enter UPI ID
3. Enter amount
4. Enter PIN
5. Swipe to Pay

#### Card Payment
1. Dashboard → **"Send Money"** → **"Card Payment"**
2. Select saved card or add new
3. Enter payment details
4. Swipe to Pay

---

## 📸 Screenshots

*(Add your screenshots here)*

---

## 🔒 Security Notes

### ⚠️ Important Disclaimer

This is a **college project simulation**. For production use, implement:

- ✅ Password hashing (BCrypt)
- ✅ JWT authentication
- ✅ HTTPS/TLS encryption
- ✅ Input validation
- ✅ Environment variables
- ✅ Rate limiting
- ✅ CSRF protection

---

## 🛠 Troubleshooting

### Backend Issues

**Port 8080 Already in Use**
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

**MySQL Connection Failed**
- Check MySQL service is running
- Verify credentials in `application.properties`
- Ensure database `payment_db` exists

### Frontend Issues

**CORS Errors**
- Verify backend is running on port 8080
- Clear browser cache

**API Calls Failing**
- Check Network tab in DevTools
- Verify backend console for errors

---

## 👥 Contributors

**Lead Developer**
- Name: Nirnit
- GitHub: [@nirnit-13](https://github.com/nirnit-13)
- Repository: [PayEasy-Payment-Gateway](https://github.com/nirnit-13/PayEasy-Payment-Gateway)

### Institution
- **College**: [Your College Name]
- **Department**: Computer Science & Engineering
- **Year**: 2024-2025

---

## 📄 License

This project is created for **educational purposes** as a college project demonstration.

### Attribution
```
PayEasy - Payment Gateway Simulation
Repository: https://github.com/nirnit-13/PayEasy-Payment-Gateway
Created by: Nirnit
```

---

## 🙏 Acknowledgments

### Technologies & Libraries
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MySQL](https://www.mysql.com/)
- [ZXing](https://github.com/zxing/zxing)
- [jsPDF](https://github.com/parallax/jsPDF)

### Inspiration
- Google Pay
- PhonePe
- Paytm

---

## 📞 Support

### For Issues
- **GitHub Issues**: [Create an issue](https://github.com/nirnit-13/PayEasy-Payment-Gateway/issues)
- **Documentation**: Check README and source code comments

---

## ⭐ Show Your Support

If you found this project helpful:

- ⭐ **Star** the repository on [GitHub](https://github.com/nirnit-13/PayEasy-Payment-Gateway)
- 🍴 **Fork** it for your own learning
- 📢 **Share** it with classmates

---

**Made with ❤️ for learning and education**

**PayEasy © 2025**

---

*Last Updated: January 2025*

*Repository: [https://github.com/nirnit-13/PayEasy-Payment-Gateway](https://github.com/nirnit-13/PayEasy-Payment-Gateway)*