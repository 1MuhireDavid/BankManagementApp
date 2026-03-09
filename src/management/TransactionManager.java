package management;

import account.Account;
import transaction.Transaction;

public class TransactionManager {
    private Transaction[] transactions;
    private int transactionCount;

    public TransactionManager() {
        transactions = new Transaction[200];
        transactionCount = 0;
    }
    public void addTransaction(Transaction transaction){
        if(transactionCount<transactions.length){
            transactions[transactionCount] = transaction;
            transactionCount++;
            System.out.println("Transaction completed successfully");
        }else {
            System.out.println("Transactions is out of space");
        }
    }

    public void viewTransactionsByAccount(String accountNumber) {

        if (transactions == null) {
            System.out.println("+-----------------------+");
            System.out.println("No transactions found");
            System.out.println("+-----------------------+");
            return;
        }

        boolean found = false;

        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)) {
                transactions[i].displayTransactionDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for this account.");
        }
    }
    public double calculateTotalDeposits(String accountNumber) {
        double depositSum = 0.0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)
                    && transactions[i].getType().equalsIgnoreCase("Deposit")) {
                depositSum += transactions[i].getAmount();
            }
        }
        return depositSum;
    }

    public double calculateTotalWithdrawals(String accountNumber) {
        double withdrawSum = 0.0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)
                    && transactions[i].getType().equalsIgnoreCase("Withdrawal")) {
                withdrawSum += transactions[i].getAmount();
            }
        }
        return withdrawSum;
    }

    public int getTransactionCount(){
        return transactionCount;
    }

}
