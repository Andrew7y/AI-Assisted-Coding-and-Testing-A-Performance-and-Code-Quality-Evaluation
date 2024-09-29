package Composite.Round2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Component
interface ATMOperation {
    void execute();
}

// Leaf for Authentication
class AuthenticateUser implements ATMOperation {
    private String cardNumber;
    private String pin;

    public AuthenticateUser(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    @Override
    public void execute() {
        // Simulate authentication
        System.out.println("Authenticating user...");
        if (this.cardNumber.equals("1234-5678-9012-3456") && this.pin.equals("1234")) {
            System.out.println("Authentication successful!");
        } else {
            System.out.println("Authentication failed! Card retained.");
        }
    }
}

// Leaf for Balance Inquiry
class BalanceInquiry implements ATMOperation {
    private double balance;

    public BalanceInquiry(double balance) {
        this.balance = balance;
    }

    @Override
    public void execute() {
        // Simulate balance inquiry
        System.out.println("Your current balance is: $" + balance);
    }
}

// Leaf for Cash Withdrawal
class CashWithdrawal implements ATMOperation {
    private double balance;
    private double amount;

    public CashWithdrawal(double balance, double amount) {
        this.balance = balance;
        this.amount = amount;
    }

    @Override
    public void execute() {
        // Simulate withdrawal process
        if (amount > balance) {
            System.out.println("Insufficient funds! Cannot withdraw.");
        } else {
            balance -= amount;
            System.out.println("Please take your cash. Your new balance is: $" + balance);
        }
    }

    public double getBalance() {
        return balance;
    }
}

// Leaf for Cash Deposit
class CashDeposit implements ATMOperation {
    private double balance;
    private double depositAmount;

    public CashDeposit(double balance, double depositAmount) {
        this.balance = balance;
        this.depositAmount = depositAmount;
    }

    @Override
    public void execute() {
        // Simulate cash deposit
        balance += depositAmount;
        System.out.println("Deposit successful. Your new balance is: $" + balance);
    }

    public double getBalance() {
        return balance;
    }
}

// Leaf for PIN Change
class ChangePIN implements ATMOperation {
    private String currentPIN;
    private String newPIN;
    private String confirmNewPIN;

    public ChangePIN(String currentPIN, String newPIN, String confirmNewPIN) {
        this.currentPIN = currentPIN;
        this.newPIN = newPIN;
        this.confirmNewPIN = confirmNewPIN;
    }

    @Override
    public void execute() {
        // Simulate PIN change
        if (newPIN.equals(confirmNewPIN)) {
            System.out.println("PIN changed successfully!");
        } else {
            System.out.println("PINs do not match! Try again.");
        }
    }
}

// Leaf for Printing Receipt
class PrintReceipt implements ATMOperation {
    private String transactionDetails;

    public PrintReceipt(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    @Override
    public void execute() {
        // Simulate printing receipt
        System.out.println("Printing receipt...");
        System.out.println(transactionDetails);
    }
}

// Java.ChatGPT4o.Composite Operation
class CompositeATMOperation implements ATMOperation {
    private List<ATMOperation> operations = new ArrayList<>();

    public void addOperation(ATMOperation operation) {
        operations.add(operation);
    }

    @Override
    public void execute() {
        for (ATMOperation operation : operations) {
            operation.execute();
        }
    }
}

// ATM System
public class CodeComposite2 {
    public static void main(String[] args) {
        double initialBalance = 1000.0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card number:");
        String cardNumber = scanner.nextLine();

        System.out.println("Enter PIN:");
        String pin = scanner.nextLine();

        // Java.ChatGPT4o.Composite pattern to handle multiple operations
        CompositeATMOperation atmOperations = new CompositeATMOperation();

        // Authentication operation
        atmOperations.addOperation(new AuthenticateUser(cardNumber, pin));

        // Perform operations based on user input
        while (true) {
            System.out.println("Select an operation: 1) Balance Inquiry 2) Withdraw Cash 3) Deposit Cash 4) Change PIN 5) Print Receipt 6) Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    atmOperations.addOperation(new BalanceInquiry(initialBalance));
                    break;
                case 2:
                    System.out.println("Enter amount to withdraw:");
                    double withdrawAmount = scanner.nextDouble();
                    CashWithdrawal withdrawal = new CashWithdrawal(initialBalance, withdrawAmount);
                    atmOperations.addOperation(withdrawal);
                    initialBalance = withdrawal.getBalance(); // Update balance
                    break;
                case 3:
                    System.out.println("Enter amount to deposit:");
                    double depositAmount = scanner.nextDouble();
                    CashDeposit deposit = new CashDeposit(initialBalance, depositAmount);
                    atmOperations.addOperation(deposit);
                    initialBalance = deposit.getBalance(); // Update balance
                    break;
                case 4:
                    scanner.nextLine(); // consume newline
                    System.out.println("Enter current PIN:");
                    String currentPIN = scanner.nextLine();
                    System.out.println("Enter new PIN:");
                    String newPIN = scanner.nextLine();
                    System.out.println("Confirm new PIN:");
                    String confirmNewPIN = scanner.nextLine();
                    atmOperations.addOperation(new ChangePIN(currentPIN, newPIN, confirmNewPIN));
                    break;
                case 5:
                    atmOperations.addOperation(new PrintReceipt("Transaction Details: Date: 2024-08-24, Amount: $100, Balance: $" + initialBalance));
                    break;
                case 6:
                    System.out.println("Exiting ATM. Have a nice day!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            // Execute all operations
            atmOperations.execute();
        }
    }
}

