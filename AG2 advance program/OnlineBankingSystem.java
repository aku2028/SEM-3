
class Account {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;


    Account(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    
    void displayDetails() {
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + accountHolder);
        System.out.println("Balance        : ₹" + balance);
    }
}

// Child class - Savings Account
class SavingsAccount extends Account {
    private double interestRate;

    SavingsAccount(String accountNumber, String accountHolder,
                   double balance, double interestRate) {
        super(accountNumber, accountHolder, balance);
        this.interestRate = interestRate;
    }

   
    @Override
    void displayDetails() {
        System.out.println("----- Savings Account -----");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + accountHolder);
        System.out.println("Balance        : ₹" + balance);
        System.out.println("Interest Rate  : " + interestRate + "%");
    }
}

class CurrentAccount extends Account {
    private double overdraftLimit;

    CurrentAccount(String accountNumber, String accountHolder,
                   double balance, double overdraftLimit) {
        super(accountNumber, accountHolder, balance);
        this.overdraftLimit = overdraftLimit;
    }

   
    @Override
    void displayDetails() {
        System.out.println("----- Current Account -----");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + accountHolder);
        System.out.println("Balance        : ₹" + balance);
        System.out.println("Overdraft Limit: ₹" + overdraftLimit);
    }
}

// Main class
public class OnlineBankingSystem {
    public static void main(String[] args) {
 System.out.println("RA2511026011370");
      
        Account account;

        
        account = new SavingsAccount(
                "SA1001", "Akash", 50000, 6.5);

        account.displayDetails();

        System.out.println();

        account = new CurrentAccount(
                "CA2001", "Rahul", 75000, 25000);

        account.displayDetails();
    }
}