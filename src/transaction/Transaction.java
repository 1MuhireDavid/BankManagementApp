package transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String transactionId;
    private String accountNumber;
    private String type;
    private double amount;
    private double balanceAfter;
    private String timestamp;
    static int transactionCounter = 1000;

    public Transaction(String accountNumber, String type, double amount, double balanceAfter) {
        this.transactionId = "TXN"+ (++transactionCounter);
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.timestamp = dtf.format(LocalDateTime.now());
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void displayTransactionDetails(){
        double previousBalance;
        if(type.equals("Deposit")){
            previousBalance = balanceAfter - amount;
        } else {
            previousBalance = balanceAfter + amount;
        }
        System.out.println("TRANSACTION CONFIRMATION");
        System.out.println("+-----------------------------------------+");
        System.out.println("| %-21s |\\n\",Transaction ID: "+ transactionId);
        System.out.println("| %-21s |\\n\",Account: "+ accountNumber);
        System.out.println("| %-21s |\\n\",Type: "+type);
        System.out.println("| %-21s |\\n\",Amount: $"+ amount);
        System.out.println("| %-21s |\\n\",Previous Balance: "+ previousBalance);
        System.out.println("| %-21s |\\n\",New Balance: "+ balanceAfter);
        System.out.println("| %-21s |\\n\",Date/Time: "+ timestamp);
        System.out.println("+-----------------------+");
    }
}
