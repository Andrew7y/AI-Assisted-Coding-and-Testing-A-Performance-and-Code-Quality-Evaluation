package java.round1.scr;

public class ATM {
    private double balance;
    private String cardNumber;
    private String pin;
    private boolean authenticated;

    public ATM(double balance, String cardNumber, String pin) {
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.authenticated = false;
    }

    public void authenticate(String cardNumber, String pin) {
        if (this.cardNumber.equals(cardNumber) && this.pin.equals(pin)) {
            authenticated = true;
            System.out.println("Authentication successful.");
        } else {
            System.out.println("Authentication failed.");
        }
    }

    public void checkBalance() {
        if (authenticated) {
            System.out.println("Current balance: " + balance);
        } else {
            System.out.println("User not authenticated.");
        }
    }

    public void withdraw(double amount) {
        if (authenticated) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrawal successful. Amount: " + amount);
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("User not authenticated.");
        }
    }

    public void deposit(double amount) {
        if (authenticated) {
            balance += amount;
            System.out.println("Deposit successful. Amount: " + amount);
        } else {
            System.out.println("User not authenticated.");
        }
    }

    public void changePin(String oldPin, String newPin) {
        if (authenticated) {
            if (this.pin.equals(oldPin)) {
                this.pin = newPin;
                System.out.println("PIN changed successfully.");
            } else {
                System.out.println("Old PIN is incorrect.");
            }
        } else {
            System.out.println("User not authenticated.");
        }
    }

    public void printReceipt() {
        if (authenticated) {
            System.out.println("Printing receipt...");
            // Print receipt details
        } else {
            System.out.println("User not authenticated.");
        }
    }
}
