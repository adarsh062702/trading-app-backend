# 📈 Trading App Backend

[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/JWT-Authentication-black?style=for-the-badge&logo=jsonwebtokens)](https://jwt.io/)

I built this backend to handle everything a stock trading app needs—secure auth, wallet operations, and portfolio tracking. It's the foundation I used to learn how to scale Spring Boot apps with JWT and MySQL.

---

## 🛠️ What it does

### 🔐 Security & Auth
*   **JWT Login**: I used JSON Web Tokens to keep user sessions secure and stateless.
*   **Spring Security**: Role-based access is baked in, so only authorized users can touch trading APIs.
*   **Swagger Integration**: You can test the protected endpoints directly in the Swagger UI.

### 💰 Wallet & Trading Logic
*   **Wallet Engine**: Users can deposit and withdraw, with the app handling all the balance validation.
*   **Trade Execution**: Logic for buying and selling stocks, including real-time portfolio updates.
*   **History**: Every single trade and wallet movement is logged for a full audit trail.

### 🛠️ Developer Experience
*   **OpenAPI/Swagger**: Interactive documentation at `/swagger-ui/index.html`.
*   **Clean Architecture**: Separation of concerns across controllers, services, and repositories.

---

## 💻 Tech Stack

| Component | Technology |
| :--- | :--- |
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.x |
| **Security** | Spring Security + JWT |
| **Persistence** | Spring Data JPA (Hibernate) |
| **Database** | MySQL |
| **Build Tool** | Maven |
| **Docs** | Swagger / OpenAPI |

---

## 📂 Project Structure

The project follows a standard Spring Boot multi-package architecture:

```text
src/main/java/com/adarsh/tradingapp
├── config          # Application & Security configurations
├── controller      # REST API Endpoints
├── dto             # Data Transfer Objects
├── entity          # Database Models
├── exception       # Global Error Handling
├── repository      # Data Access Layer
├── security/jwt    # JWT logic (Filters, Providers)
├── service         # Business Logic
└── util            # Helper classes
```

---

## 📡 API Reference

### Authentication
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Create a new user account |
| `POST` | `/api/auth/login` | Get a JWT token |

### Wallet & Portfolio
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/wallet/add-money` | Deposit funds |
| `POST` | `/api/wallet/withdraw` | Withdraw funds |
| `GET` | `/api/wallet/balance` | Get current wallet state |
| `GET` | `/api/portfolio/{email}` | View all holdings for a user |

### Trading
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/stocks` | List available stocks |
| `POST` | `/api/stocks/buy-stock` | Execute a buy order |
| `POST` | `/api/stocks/sell-stock` | Execute a sell order |

---

## ⚙️ Getting Started

### Prerequisites
*   Java 17 or higher
*   MySQL 8.x
*   Maven

### 1. Setup the Database
Create a new schema in MySQL:
```sql
CREATE DATABASE trading_app;
```

### 2. Configure Environment
Update `src/main/resources/application.properties` with your credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/trading_app
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

jwt.secret=YOUR_SECRET_KEY_HERE
```

### 3. Build and Run
```bash
# Clone the repo
git clone https://github.com/adarsh062702/trading-app-backend.git

# Run via Maven
mvn spring-boot:run
```

---

## 🔮 Roadmap
- [ ] **Redis Caching**: Speed up stock price lookups.
- [ ] **Docker Support**: Containerize the app for easy deployment.
- [ ] **Live Data**: Integrate with a real-time stock price API (Alpha Vantage/IEX).
- [ ] **Unit Tests**: Full coverage for the trading engine.

---

## 👤 Author
**Adarsh Bindal**
*   [GitHub](https://github.com/adarsh062702)
*   [LinkedIn](https://www.linkedin.com/in/adarshbindal/)
