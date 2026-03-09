import account.CheckingAccount;
import account.SavingsAccount;
import customer.Customer;
import customer.PremiumCustomer;
import customer.RegularCustomer;
import management.AccountManager;
import management.TransactionManager;
import account.Account;
import transaction.Transaction;

void main(){
    Scanner input = new Scanner(System.in);
    AccountManager aManager = new AccountManager();
    TransactionManager tManager = new TransactionManager();

    boolean running  = true;
    int choice;

    System.out.println("-".repeat(70));
    System.out.println("||      BANK ACCOUNT MANAGEMENT - MAIN MENU     ||");
    System.out.println("-".repeat(70));

    while (running){
        System.out.println("1. Create Account");

        System.out.println("2. View Accounts");
        System.out.println("3. Process Transaction");
        System.out.println("4. View Transaction History");
        System.out.println("5. Exit");
        System.out.println("Enter choice: ");

        choice = input.nextInt();
        input.nextLine();

        switch (choice){
            case 1 -> handleCreateAccount(input,aManager);
            case 2 -> handleViewAccounts(aManager);
            case 3-> handleTransaction(input,tManager,aManager);
            case 4 -> handleTransViewHistory(input,tManager,aManager);
            case 5 -> {
                running = false;
                System.out.println("GoodBye!");
            }
            default -> System.out.println("Please input a valid choice");
        }
    }
    input.close();
}


private void handleViewAccounts(AccountManager aManager) {
    System.out.println("ACCOUNT LISTING");
    aManager.viewAllAccounts();
}

private void handleCreateAccount(Scanner input, AccountManager aManager) {
    System.out.println("\nACCOUNT CREATION");
    System.out.println("-----------------------------------------");

    System.out.print("Enter customer name: ");
    String name = input.nextLine().trim();

    System.out.print("Enter customer age: ");
    int age = input.nextInt();

    System.out.print("Enter customer contact: ");
    String contact = input.nextLine().trim();
    input.nextLine();

    System.out.print("Enter customer address: ");
    String address = input.nextLine().trim();

    System.out.println("\nCustomer type:");
    System.out.println("1. Regular Customer (Standard banking services)");
    System.out.println("2. Premium Customer (Enhanced benefits, min balance $10,000)");
    System.out.print("Select type (1-2): ");
    int customerType = input.nextInt();

    System.out.println("\nAccount type:");
    System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
    System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");
    System.out.print("Select type (1-2): ");
    int accountType = input.nextInt();

    System.out.print("\nEnter initial deposit amount: $");
    double deposit = input.nextDouble();

    Customer newCustomer;
    Account newAccount;
    if(customerType == 1){
        newCustomer = new RegularCustomer(name, age, contact, address);
    }else {
        newCustomer = new PremiumCustomer(name, age, contact, address);
    }

    if(accountType == 1){
        newAccount = new SavingsAccount(newCustomer,deposit);
    }else if(accountType == 2){
       newAccount = new CheckingAccount(newCustomer,deposit);
    }else{
        System.out.println("Invalid input on account type");
        return;
    }
    aManager.addAccount(newAccount);
}

private void handleTransaction(Scanner input, TransactionManager tManager, AccountManager aManager) {
    System.out.println("VIEW TRANSACTION HISTORY");
    System.out.println("---------------------------------");

    System.out.println("Enter Account Number: ");
    String accNumber = input.nextLine();


    Account acc= aManager.findAccount(accNumber);
    if (acc == null) {
        System.out.println("Account not found.");
        return;
    }
    System.out.println("Account Details: ");
    acc.displayAccountDetails();

    System.out.println("Transaction type");
    System.out.println("1. Deposit ");
    System.out.println("2. Withdrawal");

    System.out.println("Select type (1-2): ");
    int transTypeNum = input.nextInt();
    input.nextLine();

    System.out.println("Enter amount: ");
    double amount = input.nextDouble();
    input.nextLine();

    String transType ="";
    if(transTypeNum == 1){
        transType = "Deposit";
        acc.deposit(amount);
    } else if (transTypeNum == 2) {
        transType = "Withdrawal";
        acc.withdraw(amount);
    }else{
        System.out.println("Invalid Input");
        return;
    }
    double balanceAfter = acc.getBalance();

    Transaction transaction = new Transaction(accNumber,transType,amount,balanceAfter);
    transaction.displayTransactionDetails();

    System.out.println("Confirm transaction? (Y/N)");
    String confirm = input.nextLine();
    if(confirm.equals("Y")){
        tManager.addTransaction(transaction);
        System.out.println("Transaction completed successfully!");
    }else if(confirm.equals("N")){
        System.out.println("Transaction cancelled successfully");
        System.out.println("Please continue...");
    }
    else{
        System.out.println("Invalid letter");
    }
}

private void handleTransViewHistory(Scanner input, TransactionManager tManager,AccountManager aManager) {
    System.out.println("VIEW TRANSACTION HISTORY");
    System.out.println("+----------------------------+");
    System.out.println("Enter Account Number: ");
    String accNumber = input.nextLine();
    Account acc = aManager.findAccount(accNumber);
    System.out.println("Account: "+acc.getAccountNumber()+" - "+acc.getCustomer().getName());
    System.out.println("Account Type: "+acc.getAccountType());
    System.out.println("Current Balance: $"+ acc.getBalance());
    tManager.viewTransactionsByAccount(accNumber);
}