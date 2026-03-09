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

        for (Transaction transaction : transactions) {

            if (transaction == null) {
                continue;
            }

            if (transaction.getAccountNumber().equals(accountNumber)) {
                transaction.displayTransactionDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for this account.");
        }
    }
    public double calculateTotalDeposits(String accountNumber){
        double depositSum = 0.0;
        for(Transaction transaction: transactions){
            if(transaction.getAccountNumber().equals(accountNumber)&& transaction.getType().equals("deposit")){
                depositSum++;
            }
        }
        return depositSum;
    }
    public double calculateTotalWithdrawals(String accountNumber){
        double withDrawSum = 0.0;
        for(Transaction transaction: transactions){
            if(transaction.getAccountNumber().equals(accountNumber)&& transaction.getType().equals("withdraw")){
                withDrawSum++;
            }
        }
        return withDrawSum;
    }
    public int getTransactionCount(){
        return transactionCount;
    }

}
