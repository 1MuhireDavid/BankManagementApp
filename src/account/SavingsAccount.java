package account;

import customer.Customer;

import java.text.DecimalFormat;

public class SavingsAccount extends Account {
    private double interestRate;
    private double minimumBalance;

    public SavingsAccount(){}
    public SavingsAccount(Customer customer, double initialBalance) {
        super(customer, initialBalance);
        this.interestRate = 3.5/100;
        this.minimumBalance = 500;
    }

    @Override
    public void displayAccountDetails() {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println();
        System.out.println("Account :       " + getAccountNumber());
        //TODO: ADD PROPER WAY OF GETTING CUSTOMER NAME NOT THIS customer.PremiumCustomer@548c4f57
        System.out.println("Customer:        " + getCustomer().getName());
        System.out.println("Balance:         $" + getBalance());
        System.out.println("Status:           "+ getStatus());
        System.out.println("Interest Rate:    "+ df.format(this.interestRate *100) + "%");
        System.out.println("Min Balance:   "+ this.minimumBalance);
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    @Override
    public void withdraw(double amount){
        if(amount> getBalance()){
            System.out.println("Withdraw denied not enough money");
        }else{
           super.withdraw(amount);
            System.out.println("Withdraw successfully");
        }
    }

    double calculateInterest(){
        double interest = getBalance() * interestRate;
        System.out.println("Interest earned $ "+ interest);
        return interest;
    }
}
