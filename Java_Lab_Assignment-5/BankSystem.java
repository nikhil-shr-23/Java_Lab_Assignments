import java.util.*;

class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public synchronized void withdraw(String user, int amt) {
        System.out.println(user + " wants to withdraw: " + amt);
        
        if (balance >= amt) {
            try {
                Thread.sleep(500); 
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            balance = balance - amt;
            System.out.println("✔ " + user + " withdrew " + amt + ". Current Balance: " + balance);
        } else {
            System.out.println( user + " failed (Insufficient Balance).");
        }
        System.out.println("---------");
    }

    public synchronized void deposit(String user, int amt) {
        System.out.println(user + " is depositing: " + amt);
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        
        balance = balance + amt;
        System.out.println("✔ " + user + " deposited " + amt + ". Current Balance: " + balance);
        System.out.println("-----------");
    }
    
    public int getBalance() {
        return balance;
    }
}

class UserTransaction extends Thread {
    BankAccount acc;
    String name;
    int amount;
    boolean isDeposit; 

    public UserTransaction(BankAccount acc, String name, int amount, boolean isDeposit) {
        this.acc = acc;
        this.name = name;
        this.amount = amount;
        this.isDeposit = isDeposit;
    }

    public void run() {
        if (isDeposit) {
            acc.deposit(name, amount);
        } else {
            acc.withdraw(name, amount);
        }
    }
}

public class BankSystem {
    public static void main(String[] args) {
        BankAccount myAcc = new BankAccount(1000); 
        System.out.println("Initial Balance: " + myAcc.getBalance());
        System.out.println("-------------------------");

        UserTransaction t1 = new UserTransaction(myAcc, "Nikhil", 500, false);
        
        UserTransaction t2 = new UserTransaction(myAcc, "Rayyan", 700, false);
        
        UserTransaction t3 = new UserTransaction(myAcc, "Raghav", 1000, true);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("All transactions finished.");
        System.out.println("Final Account Balance: " + myAcc.getBalance());
    }
}