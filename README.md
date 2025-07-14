# 💰 Online Banking System

A Java-based console application that simulates core banking functionalities like user registration, login, deposits, withdrawals, fund transfers, and transaction history using MySQL and JDBC.

---

## 🚀 Features

- ✅ User Registration & Login
- 💵 Deposit & Withdraw Funds
- 🔁 Transfer Money to Other Users
- 📄 View Transaction History
- 🛡️ MySQL + JDBC Integration
- 🖥️ Console-Based User Interface

---

## 🛠️ Tech Stack

- **Language**: Java  
- **Database**: MySQL  
- **Connectivity**: JDBC  
- **IDE**: IntelliJ IDEA  
- **Version Control**: Git & GitHub

---

## 🗃️ Database Tables

### `users`
| Field     | Type      |
|-----------|-----------|
| id        | INT (PK)  |
| username  | VARCHAR   |
| password  | VARCHAR   |
| balance   | DOUBLE    |

### `transactions`
| Field     | Type          |
|-----------|---------------|
| id        | INT (PK)      |
| user_id   | INT (FK)      |
| type      | VARCHAR       |
| amount    | DOUBLE        |
| date      | TIMESTAMP     |

---

## 🧪 How to Run Locally

1. Clone the repository  
2. Set up MySQL and import `banking.sql`  
3. Update DB credentials in `Database.java`  
4. Run `Main.java` from IntelliJ or terminal

---

## 📸 Screenshots

> *(Add screenshots of your app running here)*

---

## 📄 Project Report

A complete `.docx` report is included in the repository for submission and reference.

---

## 🧑‍💻 Developed by

**Akshit**  
July 2025
