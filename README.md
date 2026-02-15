# Bank Account Management System

A console-based banking application built with Java and Spring Framework that allows users to manage bank accounts, perform transactions, and handle multi-user financial operations with commission logic.

## 📋 Table of Contents
- [Features](#-features)
- [Technologies & Dependencies](#-technologies--dependencies)
- [Project Structure](#-project-structure)
- [Configuration](#-configuration)
- [Setup & Installation](#-setup--installation)
- [Usage Guide](#-usage-guide)
- [Business Rules](#-business-rules)
- [Known Limitations](#-known-limitations)

## ✨ Features

- Create and manage multiple users with unique logins
- Create multiple accounts per user with automatic ID generation
- Deposit and withdraw funds from accounts
- Transfer money between accounts:
  - **Within same user**: No commission
  - **Between different users**: Commission applied (configurable percentage)
- Close accounts (with safety rules to prevent closing last account)
- View all users and their account details in real-time
- Console-based interactive menu system

## 🛠 Technologies & Dependencies

### Core Technologies
- **Java 25**
- **Spring Framework 6.x** (Core Container, Dependency Injection)
- **Spring Context** (`AnnotationConfigApplicationContext`)

### Key Spring Features Used
- `@Component` - Component scanning for service registration
- `@Configuration` & `@PropertySource` - Externalized configuration
- `@Value` - Property injection from `application.properties`
- `@Lazy` - Circular dependency resolution between services
- Constructor-based dependency injection

### Project Structure
```
src/main/java/com/pazl2/
├── home_task/models/    # Account model (note: package mismatch with other models)
├── models/              # User model
├── properties/          # Spring configuration classes
├── services/            # Business logic (AccountService, UserService, Console UI)
├── Main.java            # Application entry point
└── OperationType.java   # Enum for operation types
```

## ⚙️ Configuration

Create `src/main/resources/application.properties` with:

```properties
# Default amount for newly created accounts
account.default-amount=1000

# Transfer commission percentage (applied only for cross-user transfers)
account.transfer-commission=5
```

> 💡 **Note**: Commission is calculated as integer percentage (e.g., 5 = 5%). Integer division is used, so small amounts may have rounding effects.


### Available Operations
```
-ACCOUNT_CREATE
-SHOW_ALL_USERS
-ACCOUNT_CLOSE
-ACCOUNT_WITHDRAW
-ACCOUNT_DEPOSIT
-ACCOUNT_TRANSFER
-USER_CREATE
-EXIT
```

### Step-by-Step Workflow

1. **Create a User**
   ```
   Enter operation: USER_CREATE
   Enter login for new user: john_doe
   ```
   → System automatically creates first account with default amount

2. **Create Additional Account**
   ```
   Enter operation: ACCOUNT_CREATE
   Enter the user id for which to create an account: 0
   ```
   → New account created with default amount

3. **Deposit Funds**
   ```
   Enter operation: ACCOUNT_DEPOSIT
   Enter account ID to deposit to: 1
   Enter amount to deposit: 500
   ```

4. **Withdraw Funds**
   ```
   Enter operation: ACCOUNT_WITHDRAW
   Enter account ID to withdraw from: 1
   Enter amount to withdraw: 200
   ```

5. **Transfer Between Accounts**
   ```
   Enter operation: ACCOUNT_TRANSFER
   Enter source account ID: 0
   Enter target account ID: 1
   Enter amount to transfer: 300
   ```
   - ✅ Same-user transfer: Full amount received
   - 💸 Cross-user transfer: Commission deducted from transferred amount

6. **Close Account**
   ```
   Enter operation: ACCOUNT_CLOSE
   Enter account ID to close: 1
   ```
   → Cannot close user's first/only account (safety rule)

7. **View All Users**
   ```
   Enter operation: SHOW_ALL_USERS
   ```
   → Displays all users with their accounts and balances

8. **Exit Application**
   ```
   Enter operation: EXIT
   ```

## 📜 Business Rules

| Operation | Rule |
|-----------|------|
| **Account Creation** | First account auto-created when user is registered |
| **Account Closure** | Cannot close user's first/only account |
| **Withdrawals** | Must have sufficient balance |
| **Same-User Transfers** | No commission applied |
| **Cross-User Transfers** | Commission deducted from transferred amount (`amount - (amount * commission%)`) |
| **ID Generation** | Auto-incremented using static counters |


---
