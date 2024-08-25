package visitor.java.round2.scr;

public class ATM {
    private int balance;
    private String cardNumber;
    private String pin;
    private boolean authenticated;
    private int failedAttempts;

    public ATM(int balance, String cardNumber, String pin) {
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.authenticated = false;
        this.failedAttempts = 0;
    }

    public boolean authenticate(String cardNumber, String pin) {
        if (this.cardNumber.equals(cardNumber) && this.pin.equals(pin)) {
            this.authenticated = true;
            this.failedAttempts = 0;
            System.out.println("Authentication successful.");
            return true;
        } else {
            this.failedAttempts++;
            if (this.failedAttempts >= 3) {
                System.out.println("Card retained due to too many failed attempts.");
                // Logic to retain the card
            } else {
                System.out.println("Authentication failed.");
            }
            return false;
        }
    }

    public int checkBalance() {
        if (this.authenticated) {
            System.out.println("Current balance: " + this.balance);
            return this.balance;
        } else {
            System.out.println("User not authenticated.");
            return -1;
        }
    }

    public boolean withdraw(int amount) {
        if (this.authenticated) {
            if (this.balance >= amount) {
                this.balance -= amount;
                System.out.println("Withdrawal successful. Amount: " + amount);
                return true;
            } else {
                System.out.println("Insufficient funds.");
                return false;
            }
        } else {
            System.out.println("User not authenticated.");
            return false;
        }
    }

    public void deposit(int amount) {
        if (this.authenticated) {
            this.balance += amount;
            System.out.println("Deposit successful. Amount: " + amount);
        } else {
            System.out.println("User not authenticated.");
        }
    }

    public boolean changePin(String oldPin, String newPin) {
        if (this.authenticated) {
            if (this.pin.equals(oldPin)) {
                this.pin = newPin;
                System.out.println("PIN changed successfully.");
                return true;
            } else {
                System.out.println("Old PIN is incorrect.");
                return false;
            }
        } else {
            System.out.println("User not authenticated.");
            return false;
        }
    }

    public void printReceipt() {
        if (this.authenticated) {
            System.out.println("Printing receipt...");
            // Print receipt details
        } else {
            System.out.println("User not authenticated.");
        }
    }
}
