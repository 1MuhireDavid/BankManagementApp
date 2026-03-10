package com.bank.management;

import com.bank.account.Account;
import com.bank.account.CheckingAccount;
import com.bank.account.SavingsAccount;
import com.bank.customer.PremiumCustomer;

import java.text.DecimalFormat;
import java.util.Scanner;

public class AccountManager {
    private final Account[] accounts;
    private int accountCount;

    public AccountManager() {
        this.accountCount = 0;
        this.accounts = new Account[50];
    }

    public void addAccount(Account account){
        Scanner input = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("#.##");

        if (accountCount < accounts.length){
            accounts[accountCount] = account;
            accountCount++;
            System.out.println("✔️ Account added successfully!");
            System.out.println("Account Number: "+ account.getAccountNumber());
            System.out.print("Customer: "+ account.getCustomer().getName() );
            System.out.println(" (" + account.getCustomer().getCustomerType() + ")");
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println("Initial Balance: $" + String.format("%.2f", account.getBalance()));

            if (account instanceof SavingsAccount savings) {
                System.out.println("Interest Rate: " + df.format(savings.getInterestRate() * 100) + "%");
                System.out.printf("Minimum Balance: $%.2f%n", savings.getMinimumBalance());
            } else if (account instanceof CheckingAccount checking) {
                System.out.println("Overdraft Limit: $" + String.format("%.2f", checking.getOverdraftLimit()));
                if (account.getCustomer() instanceof PremiumCustomer) {
                    System.out.println("Monthly Fee: $0.00 (WAIVED - PREMIUM CUSTOMER)");
                } else {
                    System.out.println("Monthly Fee: $" + String.format("%.2f", checking.getMonthlyFee()));
                }
            }
            System.out.println("Status: " + account.getStatus());
            System.out.print("\nPress Enter to continue...");
            input.nextLine();
        } else {
            System.out.println("Account list is full.");
        }
    }
    public Account findAccount(String accountNumber){

        for(int i=0; i<accountCount;i++){
            if(accounts[i].getAccountNumber().equals(accountNumber)){
                return accounts[i];}
        }
        return null;
    }

    public void viewAllAccounts() {
        if (accountCount == 0) {
            System.out.println("No accounts available.");
            return;
        }

        String line = "-".repeat(65);
        System.out.println("\nACCOUNT LISTING");
        System.out.println(line);
        System.out.printf("%-8s | %-18s | %-10s | %-12s | %-8s%n",
                "ACC NO", "CUSTOMER NAME", "TYPE", "BALANCE", "STATUS");
        System.out.println(line);

        for (int i = 0; i < accountCount; i++) {
            Account acc = accounts[i];

            System.out.printf("%-8s | %-18s | %-10s | $%-11s | %-8s%n",
                    acc.getAccountNumber(),
                    acc.getCustomer().getName(),
                    acc.getAccountType(),
                    String.format("%,.2f", acc.getBalance()),
                    acc.getStatus());

            if (acc instanceof SavingsAccount savings) {
                System.out.printf("         | Interest Rate: %.1f%%   | Min Balance: $%,.2f%n",
                        savings.getInterestRate() * 100,
                        savings.getMinimumBalance());
            } else if (acc instanceof CheckingAccount checking) {
                String feeStr = acc.getCustomer() instanceof PremiumCustomer
                        ? "$0.00 (WAIVED)"
                        : String.format("$%,.2f", checking.getMonthlyFee());
                System.out.printf("         | Overdraft Limit: $%,-8.2f | Monthly Fee: %s%n",
                        checking.getOverdraftLimit(),
                        feeStr);
            }

            System.out.println();
        }

        System.out.println(line);
        System.out.println("Total Accounts: " + accountCount);
        System.out.printf("Total Bank Balance: $%,.2f%n", getTotalBalance());
        System.out.print("\nPress Enter to continue...");
        new java.util.Scanner(System.in).nextLine();
    }
    public double getTotalBalance() {
        double totalBalance = 0.0;
        for (int i = 0; i < accountCount; i++) {
            totalBalance += accounts[i].getBalance();
        }
        return totalBalance;
    }

    public int getAccountCount(){
        return accountCount;
    }


}
