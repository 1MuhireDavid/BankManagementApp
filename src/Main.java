import account.CheckingAccount;
import account.SavingsAccount;
import customer.Customer;
import customer.PremiumCustomer;
import customer.RegularCustomer;
import management.AccountManager;
import management.TransactionManager;
import account.Account;
import transaction.Transaction;

import java.util.Scanner;

void main() {
    Scanner input = new Scanner(System.in);
    AccountManager aManager = new AccountManager();
    TransactionManager tManager = new TransactionManager();

    boolean running = true;

    System.out.println("-".repeat(50));
    System.out.println("||   BANK ACCOUNT MANAGEMENT - MAIN MENU   ||");
    System.out.println("-".repeat(50));

    while (running) {
        System.out.println("\n1. Create Account");
        System.out.println("2. View Accounts");
        System.out.println("3. Process Transaction");
        System.out.println("4. View Transaction History");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");

        String choiceStr = input.nextLine().trim();
        int choice;
        try {
            choice = Integer.parseInt(choiceStr);
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid number.");
            continue;
        }

        switch (choice) {
            case 1 -> handleCreateAccount(input, aManager);
            case 2 -> handleViewAccounts(aManager);
            case 3 -> handleTransaction(input, tManager, aManager);
            case 4 -> handleTransViewHistory(input, tManager, aManager);
            case 5 -> {
                running = false;
                System.out.println("GoodBye!");
            }
            default -> System.out.println("Please input a valid choice (1-5).");
        }
    }
    input.close();
}

private int readInt(Scanner input, String prompt) {
    while (true) {
        System.out.print(prompt);
        String line = input.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }
}

private double readDouble(Scanner input, String prompt) {
    while (true) {
        System.out.print(prompt);
        String line = input.nextLine().trim();
        try {
            double val = Double.parseDouble(line);
            if (val < 0) {
                System.out.println("Amount cannot be negative.");
                continue;
            }
            return val;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }
}

private void handleViewAccounts(AccountManager aManager) {
    aManager.viewAllAccounts();
}

private void handleCreateAccount(Scanner input, AccountManager aManager) {
    System.out.println("\nACCOUNT CREATION");
    System.out.println("-".repeat(40));

    System.out.print("Enter customer name: ");
    String name = input.nextLine().trim();
    if (name.isEmpty()) {
        System.out.println("Name cannot be empty.");
        return;
    }

    int age = readInt(input, "Enter customer age: ");
    if (age <= 0 || age > 150) {
        System.out.println("Invalid age.");
        return;
    }

    System.out.print("Enter customer contact: ");
    String contact = input.nextLine().trim();

    System.out.print("Enter customer address: ");
    String address = input.nextLine().trim();

    System.out.println("\nCustomer type:");
    System.out.println("1. Regular Customer (Standard banking services)");
    System.out.println("2. Premium Customer (Enhanced benefits, min balance $10,000)");
    int customerType = readInt(input, "Select type (1-2): ");
    if (customerType < 1 || customerType > 2) {
        System.out.println("Invalid customer type.");
        return;
    }

    System.out.println("\nAccount type:");
    System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
    System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");
    int accountType = readInt(input, "Select type (1-2): ");
    if (accountType < 1 || accountType > 2) {
        System.out.println("Invalid account type.");
        return;
    }

    double deposit = readDouble(input, "\nEnter initial deposit amount: $");

    Customer newCustomer;
    if (customerType == 1) {
        newCustomer = new RegularCustomer(name, age, contact, address);
    } else {
        newCustomer = new PremiumCustomer(name, age, contact, address);
    }

    Account newAccount;
    if (accountType == 1) {
        newAccount = new SavingsAccount(newCustomer, deposit);
    } else {
        newAccount = new CheckingAccount(newCustomer, deposit);
    }

    aManager.addAccount(newAccount);
    input.nextLine();
}

private void handleTransaction(Scanner input, TransactionManager tManager, AccountManager aManager) {
    System.out.println("\nPROCESS TRANSACTION");
    System.out.println("-".repeat(40));

    System.out.print("Enter Account Number: ");
    String accNumber = input.nextLine().trim();

    Account acc = aManager.findAccount(accNumber);
    if (acc == null) {
        System.out.println("Account not found.");
        return;
    }

    System.out.println("\nAccount Details:");
    acc.displayAccountDetails();

    System.out.println("\nTransaction type:");
    System.out.println("1. Deposit");
    System.out.println("2. Withdrawal");
    int transTypeNum = readInt(input, "Select type (1-2): ");

    if (transTypeNum < 1 || transTypeNum > 2) {
        System.out.println("Invalid transaction type.");
        return;
    }

    double amount = readDouble(input, "Enter amount: $");
    if (amount <= 0) {
        System.out.println("Amount must be greater than zero.");
        return;
    }

    String transType = (transTypeNum == 1) ? "Deposit" : "Withdrawal";

    double balanceBefore = acc.getBalance();
    double balanceAfter;
    if (transType.equals("Deposit")) {
        balanceAfter = balanceBefore + amount;
    } else {
        balanceAfter = balanceBefore - amount;
    }

    Transaction transaction = new Transaction(accNumber, transType, amount, balanceAfter);
    transaction.displayTransactionDetails();

    System.out.print("Confirm transaction? (Y/N): ");
    String confirm = input.nextLine().trim().toUpperCase();

    if (confirm.equals("Y")) {
        if (transType.equals("Deposit")) {
            acc.deposit(amount);
        } else {
            acc.withdraw(amount);
        }
        tManager.addTransaction(transaction);
        System.out.println("Transaction completed successfully!");
    } else if (confirm.equals("N")) {
        System.out.println("Transaction cancelled.");
    } else {
        System.out.println("Invalid input. Transaction cancelled.");
    }
}

private void handleTransViewHistory(Scanner input, TransactionManager tManager, AccountManager aManager) {
    System.out.println("\nVIEW TRANSACTION HISTORY");
    System.out.println("-".repeat(40));

    System.out.print("Enter Account Number: ");
    String accNumber = input.nextLine().trim();

    Account acc = aManager.findAccount(accNumber);
    if (acc == null) {
        System.out.println("Account not found.");
        return;
    }

    System.out.println("Account: " + acc.getAccountNumber() + " - " + acc.getCustomer().getName());
    System.out.println("Account Type: " + acc.getAccountType());
    System.out.printf("Current Balance: $%.2f%n", acc.getBalance());
    System.out.println();

    tManager.viewTransactionsByAccount(accNumber);
}