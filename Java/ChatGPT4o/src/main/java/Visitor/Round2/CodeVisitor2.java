package Visitor.Round2;

// Java.ChatGPT4o.Visitor interface
interface ATMVisitor {
    void visit(Authentication authentication);
    void visit(BalanceInquiry balanceInquiry);
    void visit(Withdrawal withdrawal);
    void visit(Deposit deposit);
    void visit(ChangePin changePin);
    void visit(PrintReceipt printReceipt);
}

// Element interface
interface ATMElement {
    void accept(ATMVisitor visitor);
}

// Concrete Elements
class Authentication implements ATMElement {
    private boolean isAuthenticated;
    private int attempts;
    private static final int MAX_ATTEMPTS = 3;

    public Authentication() {
        this.isAuthenticated = false;
        this.attempts = 0;
    }

    public void authenticate(String cardNumber, String pin) {
        // Logic to check if cardNumber and pin are correct
        if (attempts >= MAX_ATTEMPTS) {
            System.out.println("Card has been retained due to too many incorrect attempts.");
        } else if (/* check validity */ true) {
            isAuthenticated = true;
            System.out.println("Authentication successful.");
        } else {
            attempts++;
            System.out.println("Invalid card or PIN. Attempts remaining: " + (MAX_ATTEMPTS - attempts));
        }
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

class BalanceInquiry implements ATMElement {
    private double balance;

    public BalanceInquiry(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

class Withdrawal implements ATMElement {
    public double balance;

    public Withdrawal(double balance) {
        this.balance = balance;
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds. Withdrawal declined.");
        } else {
            balance -= amount;
            System.out.println("Please take your cash. Remaining balance: " + balance);
        }
    }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

class Deposit implements ATMElement {
    public double balance;

    public Deposit(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit successful. Updated balance: " + balance);
    }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

class ChangePin implements ATMElement {
    private String oldPin;
    private String newPin;

    public ChangePin(String oldPin) {
        this.oldPin = oldPin;
    }

    public void changePin(String currentPin, String newPin1, String newPin2) {
        if (!currentPin.equals(oldPin)) {
            System.out.println("Incorrect current PIN.");
        } else if (!newPin1.equals(newPin2)) {
            System.out.println("New PIN entries do not match.");
        } else {
            this.newPin = newPin1;
            System.out.println("PIN successfully changed.");
        }
    }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

class PrintReceipt implements ATMElement {
    private String transactionDetails;

    public PrintReceipt(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public void print() {
        System.out.println("Printing receipt...");
        System.out.println("Transaction details: " + transactionDetails);
    }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

// Concrete Java.ChatGPT4o.Visitor
class ATMActionVisitor implements ATMVisitor {
    @Override
    public void visit(Authentication authentication) {
        authentication.authenticate("cardNumber", "1234"); // Example input
    }

    @Override
    public void visit(BalanceInquiry balanceInquiry) {
        System.out.println("Your current balance is: " + balanceInquiry.getBalance());
    }

    @Override
    public void visit(Withdrawal withdrawal) {
        withdrawal.withdraw(500); // Example amount
    }

    @Override
    public void visit(Deposit deposit) {
        deposit.deposit(1000); // Example amount
    }

    @Override
    public void visit(ChangePin changePin) {
        changePin.changePin("1234", "5678", "5678"); // Example input
    }

    @Override
    public void visit(PrintReceipt printReceipt) {
        printReceipt.print();
    }
}

// Client
public class CodeVisitor2 {
    public static void main(String[] args) {
        // Example usage
        ATMElement authentication = new Authentication();
        ATMElement balanceInquiry = new BalanceInquiry(5000); // Example balance
        ATMElement withdrawal = new Withdrawal(5000); // Example balance
        ATMElement deposit = new Deposit(5000); // Example balance
        ATMElement changePin = new ChangePin("1234"); // Example old PIN
        ATMElement printReceipt = new PrintReceipt("Withdrawal: 500, Remaining Balance: 4500");

        ATMVisitor visitor = new ATMActionVisitor();

        // Simulate an ATM operation
        authentication.accept(visitor);
        balanceInquiry.accept(visitor);
        withdrawal.accept(visitor);
        deposit.accept(visitor);
        changePin.accept(visitor);
        printReceipt.accept(visitor);
    }
}

