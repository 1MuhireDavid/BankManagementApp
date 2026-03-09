package management;

import account.Account;
import account.CheckingAccount;
import account.SavingsAccount;
import customer.PremiumCustomer;
import customer.RegularCustomer;

import java.text.DecimalFormat;

public class AccountManager {
    private Account[] accounts;
    private int accountCount;

    public AccountManager() {
        this.accountCount = 0;
        this.accounts = new Account[50];
    }

    public void addAccount(Account account){
        DecimalFormat df = new DecimalFormat("#.##");

        if (accountCount < accounts.length){
            accounts[accountCount] = account;
            accountCount++;
            System.out.println("✔\uFE0F Account added successfully!");
            System.out.println("Account Number: "+ account.getAccountNumber());
            System.out.print("Customer: "+ account.getCustomer().getName() );
            System.out.println(" (" + account.getCustomer().getCustomerType() + ")");
            System.out.println("  Account Type: " + account.getAccountType());
            System.out.println("  Initial Balance: $" + String.format("%.2f", account.getBalance()));

            if (account instanceof SavingsAccount savings) {
                System.out.println("  Interest Rate: " + df.format(savings.getInterestRate() * 100) + "%");
                System.out.printf("  Minimum Balance: $%.2f%n", savings.getMinimumBalance());
            } else if (account instanceof CheckingAccount checking) {
                System.out.println("  Overdraft Limit: $" + String.format("%.2f", checking.getOverdraftLimit()));
                if (account.getCustomer() instanceof PremiumCustomer) {
                    System.out.println("  Monthly Fee: $0.00 (WAIVED - PREMIUM CUSTOMER)");
                } else {
                    System.out.println("  Monthly Fee: $" + String.format("%.2f", checking.getMonthlyFee()));
                }
            }
            System.out.println("  Status: " + account.getStatus());
            System.out.println("\nPress Enter to continue...");
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
        DecimalFormat df = new DecimalFormat("#.##");
        if (accountCount == 0) {
            System.out.println("No accounts available.");
            return;
        }
        System.out.println("\nACCOUNT LISTING");
        System.out.println("+----------------------------------------------------------------+");
        System.out.printf("| %-10s | %-18s | %-9s | %-12s | %-8s |%n",
                "Account", "Customer", "Type", "Balance", "Status");
        System.out.println("+----------------------------------------------------------------+");

        for (int i = 0; i < accountCount; i++) {
            Account acc = accounts[i];
            System.out.printf("| %-10s | %-18s | %-9s | $%-11s | %-8s |%n",
                    acc.getAccountNumber(),
                    acc.getCustomer().getName(),
                    acc.getAccountType(),
                    String.format("%.2f", acc.getBalance()),
                    acc.getStatus());
        }

        System.out.println("+----------------------------------------------------------------+");
        System.out.println("Total Accounts: " + getAccountCount());
        System.out.printf("Total Bank Balance: $%.2f%n", getTotalBalance());
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
