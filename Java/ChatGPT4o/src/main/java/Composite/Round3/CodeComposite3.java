package Composite.Round3;

import java.util.ArrayList;
import java.util.List;

// Component Interface
interface ATMComponent {
    void execute();
}

// Leaf classes
class Authentication implements ATMComponent {
    private String cardNumber;
    private String pin;
    private int attemptsLeft = 3;

    public Authentication(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    @Override
    public void execute() {
        if (attemptsLeft > 0) {
            System.out.println("Authenticating card number: " + cardNumber);
            if (isPinValid()) {
                System.out.println("Authentication successful.");
            } else {
                attemptsLeft--;
                System.out.println("Invalid PIN. Attempts left: " + attemptsLeft);
                if (attemptsLeft == 0) {
                    System.out.println("Card has been retained due to too many invalid attempts.");
                }
            }
        } else {
            System.out.println("Card has already been retained.");
        }
    }

    private boolean isPinValid() {
        // Assume the correct PIN is "1234" for demonstration purposes.
        return "1234".equals(pin);
    }
}

class BalanceInquiry implements ATMComponent {
    private double balance;

    public BalanceInquiry(double balance) {
        this.balance = balance;
    }

    @Override
    public void execute() {
        System.out.println("Your current balance is: " + balance);
    }
}

class Withdrawal implements ATMComponent {
    private double balance;
    private double amount;

    public Withdrawal(double balance, double amount) {
        this.balance = balance;
        this.amount = amount;
    }

    @Override
    public void execute() {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("You have withdrawn: " + amount);
            System.out.println("Remaining balance: " + balance);
        } else {
            System.out.println("Insufficient funds. Withdrawal failed.");
        }
    }
}

class Deposit implements ATMComponent {
    private double balance;
    private double depositAmount;

    public Deposit(double balance, double depositAmount) {
        this.balance = balance;
        this.depositAmount = depositAmount;
    }

    @Override
    public void execute() {
        balance += depositAmount;
        System.out.println("You have deposited: " + depositAmount);
        System.out.println("New balance: " + balance);
    }
}

class PinChange implements ATMComponent {
    private String oldPin;
    private String newPin;
    private String confirmPin;

    public PinChange(String oldPin, String newPin, String confirmPin) {
        this.oldPin = oldPin;
        this.newPin = newPin;
        this.confirmPin = confirmPin;
    }

    @Override
    public void execute() {
        if (!"1234".equals(oldPin)) {
            System.out.println("Old PIN is incorrect.");
        } else if (!newPin.equals(confirmPin)) {
            System.out.println("New PIN does not match the confirmation.");
        } else {
            System.out.println("PIN changed successfully.");
        }
    }
}

class PrintReceipt implements ATMComponent {
    private String transactionDetails;

    public PrintReceipt(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    @Override
    public void execute() {
        System.out.println("Printing receipt...");
        System.out.println(transactionDetails);
    }
}

// Composite class
class ATMComposite implements ATMComponent {
    private List<ATMComponent> components = new ArrayList<>();

    public void addComponent(ATMComponent component) {
        components.add(component);
    }

    public void removeComponent(ATMComponent component) {
        components.remove(component);
    }

    @Override
    public void execute() {
        for (ATMComponent component : components) {
            component.execute();
        }
    }
}

// Client code
public class CodeComposite3 {
    public static void main(String[] args) {
        ATMComposite atm = new ATMComposite();

        // Example ATM transaction scenario
        Authentication authentication = new Authentication("123456789", "1234");
        BalanceInquiry balanceInquiry = new BalanceInquiry(5000.0);
        Withdrawal withdrawal = new Withdrawal(5000.0, 1000.0);
        Deposit deposit = new Deposit(4000.0, 2000.0);
        PinChange pinChange = new PinChange("1234", "5678", "5678");
        PrintReceipt printReceipt = new PrintReceipt("Date: 2024-08-25\nTime: 10:00\nWithdraw: 1000\nBalance: 4000");

        atm.addComponent(authentication);
        atm.addComponent(balanceInquiry);
        atm.addComponent(withdrawal);
        atm.addComponent(deposit);
        atm.addComponent(pinChange);
        atm.addComponent(printReceipt);

        // Execute all ATM operations
        atm.execute();
    }
}
