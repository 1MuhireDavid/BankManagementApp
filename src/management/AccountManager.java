package management;

import account.Account;
import account.CheckingAccount;
import account.SavingsAccount;
import customer.Customer;
import customer.PremiumCustomer;
import customer.RegularCustomer;

import java.text.DecimalFormat;
import java.util.function.DoubleToIntFunction;

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
            if(account.getCustomer() instanceof PremiumCustomer customer){
                System.out.println(" ("+customer.getCustomerType()+")");
            } else if (account.getCustomer() instanceof RegularCustomer customer) {
                System.out.println(" ("+customer.getCustomerType()+")");
            }
            System.out.println("Account Type: "+ account.getAccountType());
            System.out.println("Initial Balance: $"+ account.getBalance());
            if (account instanceof SavingsAccount savings) {
                System.out.println("Interest Rate: " + df.format(savings.getInterestRate() * 100) + "%");
                System.out.printf( "Minimum Balance: $%.2f%n", savings.getMinimumBalance());
            } else if (account instanceof CheckingAccount checking) {
                System.out.println("Overdraft Limit: $" + checking.getOverdraftLimit());
                System.out.println("Monthly Fee: $0.00 (WAIVED - PREMIUM CUSTOMER)");
            }
            System.out.println("Status: "+ account.getStatus());
            System.out.println();

        }else {
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
    public void viewAllAccounts(){
        DecimalFormat df = new DecimalFormat("#.##");
        if (accountCount == 0) {
            System.out.println("No accounts available.");
            return;
        }
        System.out.println("ACCOUNT LISTING ");
        System.out.println("+----------------------------------------------------------------+");
        System.out.printf(   "║ %-12s ║ %-16s ║ %-11s ║ %-11s ║ %-8s ║%n",
                "Account", "Customer NAME", "Type", "BALANCE", "Status");
        for(int i = 0; i < accountCount; i++){
            Account acc = accounts[i];

            String extraInfo;
            if(acc instanceof SavingsAccount savings){
                extraInfo = "Rate: " + df.format(savings.getInterestRate() * 100) + "% Min: $" + savings.getMinimumBalance();
            } else if(acc instanceof CheckingAccount checking){
                extraInfo = "OD: $" + checking.getOverdraftLimit() + " Fee: $" + checking.getMonthlyFee();
            } else {
                extraInfo = "N/A";
            }

            System.out.printf("║ %-12s ║ %-16s ║ %-11s ║ $%-10s ║ %-8s ║ %-14s ║%n",
                    acc.getAccountNumber(),
                    acc.getCustomer().getName(),
                    acc.getAccountType(),
                    String.format("%.2f", acc.getBalance()),
                    acc.getStatus(),
                    extraInfo);
        }
        System.out.println("Total Accounts: " + getAccountCount());
        System.out.println("Total Bank Balance: "+ getTotalBalance());
    }


    public double getTotalBalance(){
        double totalBalance = 0.0;
        for(Account account: accounts){
            if(account==null){
                continue;
            }else{
                totalBalance += account.getBalance();
            }

        }
        return totalBalance;
    }
    public int getAccountCount(){
        return accountCount;
    }


}
