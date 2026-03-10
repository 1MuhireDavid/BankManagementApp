package com.bank.ui;

import com.bank.account.Account;
import com.bank.account.CheckingAccount;
import com.bank.account.SavingsAccount;
import com.bank.customer.Customer;
import com.bank.customer.PremiumCustomer;
import com.bank.customer.RegularCustomer;
import com.bank.management.TransactionManager;
import com.bank.transaction.Transaction;
import com.bank.management.AccountManager;

import java.util.Scanner;

public class MenuHandler {

    private final Scanner input = new Scanner(System.in);
    private final AccountManager aManager = new AccountManager();
    private final TransactionManager tManager = new TransactionManager();

    public void start() {
        seedDefaultAccounts();
        System.out.println("-".repeat(50));
        System.out.println("||   BANK ACCOUNT MANAGEMENT - MAIN MENU   ||");
        System.out.println("-".repeat(50));

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> handleCreateAccount();
                case 2 -> handleViewAccounts();
                case 3 -> handleTransaction();
                case 4 -> handleTransViewHistory();
                case 5 -> {
                    running = false;
                    System.out.println("GoodBye!");
                }
                default -> System.out.println("Please input a valid choice (1-5).");
            }
        }
        input.close();
    }

    private void printMenu() {
        System.out.println("\n1. Create Account");
        System.out.println("2. View Accounts");
        System.out.println("3. Process Transaction");
        System.out.println("4. View Transaction History");
        System.out.println("5. Exit");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(input.nextLine().trim());
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

    private String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private void handleViewAccounts() {
        aManager.viewAllAccounts();
    }

    private void handleCreateAccount() {
        System.out.println("\nACCOUNT CREATION");
        System.out.println("-".repeat(40));

        String name = readString("Enter customer name: ");
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }

        int age = readInt("Enter customer age: ");
        if (age <= 0 || age > 150) { System.out.println("Invalid age."); return; }

        String contact = readString("Enter customer contact: ");
        String address = readString("Enter customer address: ");

        System.out.println("\nCustomer type:");
        System.out.println("1. Regular Customer");
        System.out.println("2. Premium Customer");
        int customerType = readInt("Select type (1-2): ");
        if (customerType < 1 || customerType > 2) { System.out.println("Invalid customer type."); return; }

        System.out.println("\nAccount type:");
        System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
        System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");
        int accountType = readInt("Select type (1-2): ");
        if (accountType < 1 || accountType > 2) { System.out.println("Invalid account type."); return; }

        double deposit = readDouble("\nEnter initial deposit amount: $");

        Customer newCustomer = (customerType == 1)
                ? new RegularCustomer(name, age, contact, address)
                : new PremiumCustomer(name, age, contact, address);

        Account newAccount = (accountType == 1)
                ? new SavingsAccount(newCustomer, deposit)
                : new CheckingAccount(newCustomer, deposit);

        aManager.addAccount(newAccount);
    }

    private void handleTransaction() {
        System.out.println("\nPROCESS TRANSACTION");
        System.out.println("-".repeat(40));

        String accNumber = readString("Enter Account Number: ").toUpperCase();

        Account acc = aManager.findAccount(accNumber);
        if (acc == null) { System.out.println("Account not found."); return; }

        System.out.println("\nAccount Details:");
        System.out.println("Customer:  " + acc.getCustomer().getName());
        System.out.println("Account Type: " + acc.getAccountType());
        System.out.printf("Current Balance: $%.2f%n", acc.getBalance());

        System.out.println("\nTransaction type:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        int transTypeNum = readInt("Select type (1-2): ");
        if (transTypeNum < 1 || transTypeNum > 2) { System.out.println("Invalid transaction type."); return; }

        double amount = readDouble("Enter amount: $");
        if (amount <= 0) { System.out.println("Amount must be greater than zero."); return; }

        String transType = (transTypeNum == 1) ? "Deposit" : "Withdrawal";

        double previewBalance = transType.equals("Deposit")
                ? acc.getBalance() + amount
                : acc.getBalance() - amount;
        Transaction preview = new Transaction(accNumber, transType, amount, previewBalance);
        preview.displayTransactionDetails();

        System.out.print("Confirm transaction? (Y/N): ");
        String confirm = input.nextLine().trim().toUpperCase();

        if (confirm.equals("Y")) {
            boolean success = acc.processTransaction(amount, transType);
            if (success) {
                tManager.addTransaction(preview);
                System.out.println("\n✓ Transaction completed successfully!");
            } else {
                System.out.println("✗ Transaction failed and was not recorded.");
            }
        } else {
            System.out.println("Transaction cancelled.");
        }

        System.out.print("\nPress Enter to continue...");
        input.nextLine();
    }


    private void handleTransViewHistory() {
        System.out.println("\nVIEW TRANSACTION HISTORY");
        System.out.println("-".repeat(40));

        String accNumber = readString("Enter Account Number: ").toUpperCase();

        Account acc = aManager.findAccount(accNumber);
        if (acc == null) { System.out.println("Account not found."); return; }

        System.out.println("Account: " + acc.getAccountNumber() + " - " + acc.getCustomer().getName());
        System.out.println("Account Type: " + acc.getAccountType());
        System.out.printf("Current Balance: $%.2f%n", acc.getBalance());
        tManager.viewTransactionsByAccount(accNumber);

        System.out.print("\nPress Enter to continue...");
        input.nextLine();
    }

    private void seedDefaultAccounts() {
        aManager.addAccount(new SavingsAccount(new RegularCustomer("Alice Johnson", 34, "555-1001", "101 Maple St"), 1500.00));
        aManager.addAccount(new SavingsAccount(new PremiumCustomer("Bob Smith",     45, "555-1002", "202 Oak Ave"),  5000.00));
        aManager.addAccount(new SavingsAccount(new RegularCustomer("Carol White",   28, "555-1003", "303 Pine Rd"),  800.00));
        aManager.addAccount(new CheckingAccount(new PremiumCustomer("David Brown",  52, "555-1004", "404 Elm Blvd"), 500.00));
        aManager.addAccount(new CheckingAccount(new RegularCustomer("Eva Davis",    39, "555-1005", "505 Cedar Ln"), 1200.00));
    }
}


