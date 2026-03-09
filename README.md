# Bank Account Management System

A console-based Java banking application demonstrating OOP principles and fundamental Data Structures & Algorithms.

## Features

- **Account Creation** — Create Savings or Checking accounts for Regular or Premium customers
- **View Accounts** — List all accounts with balances and details
- **Process Transactions** — Deposit and withdraw with confirmation
- **Transaction History** — View all transactions for a specific account

## Class Structure (11 classes/interfaces)

| Category       | Classes                                           |
|----------------|---------------------------------------------------|
| Accounts       | `Account` (abstract), `SavingsAccount`, `CheckingAccount` |
| Customers      | `Customer` (abstract), `RegularCustomer`, `PremiumCustomer` |
| Interface      | `Transactable`                                    |
| Transactions   | `Transaction`                                     |
| Management     | `AccountManager`, `TransactionManager`            |
| Entry Point    | `Main`                                            |

## DSA Concepts

- **Array-based storage** for accounts (O(1) insert, O(n) search) and transactions
- **Linear search** (O(n)) to locate accounts and transactions by ID
- Fixed-size arrays chosen for simplicity; suitable for bounded data sets

## Setup & Run

1. **Prerequisites**: Java 21+ (or Java 25 with preview features enabled)
2. **Clone**: `git clone <repository-url>`
3. **Open** the project in IntelliJ IDEA
4. **Run** `Main.java`
5. Follow the on-screen menu prompts

## Input Validation

- Customer names, ages, contacts, and addresses are validated
- Transaction amounts must be positive
- Menu choices are validated with error messages for invalid input
- Null checks prevent crashes when accounts are not found