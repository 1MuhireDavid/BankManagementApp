package account;

import customer.Customer;

public class CheckingAccount extends Account{
    private double overdraftLimit;
    private double monthlyFee;


    public CheckingAccount(Customer customer, double initialBalance) {
        super(customer, initialBalance);
        this.monthlyFee = 10;
        this.overdraftLimit = 1000;
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("Account #:       " + getAccountNumber());
        System.out.println("Customer:        " + getCustomer());
        System.out.println("Balance:         $" + getBalance());
        System.out.println("Overdraft Limit: $" + overdraftLimit);
        System.out.println("Monthly Fee:     $" + monthlyFee);
        System.out.println("Status:          " + getStatus());
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    @Override
    public void withdraw(double amount){
      if(amount <= getBalance() + overdraftLimit){
          setBalance(getBalance() - amount);
          System.out.println("Withdrawn: $"+ amount);
      }else {
          System.out.println("Withdraw denied: exceeds overdraft limit");
      }
    }

     void applyMonthlyFee(){
        double newBalance = getBalance() - monthlyFee;
        setBalance(newBalance);
        System.out.println("Monthly fee of $" + monthlyFee + " applied.");
    }


}
