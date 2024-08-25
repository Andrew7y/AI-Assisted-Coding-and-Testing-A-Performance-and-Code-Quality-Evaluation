package Java.ChatGPT4o.Visitor.round1;

// ATM Operation Java.ChatGPT4o.Visitor Interface
interface ATMVisitor {
    void visit(Authentication auth);
    void visit(BalanceInquiry balanceInquiry);
    void visit(Withdrawal withdrawal);
    void visit(Deposit deposit);
    void visit(PinChange pinChange);
    void visit(PrintReceipt printReceipt);
}

// Element Interface
interface ATMOperation {
    void accept(ATMVisitor visitor);
}

// Concrete Elements
class Authentication implements ATMOperation {
    private String cardNumber;
    private String pin;
    private int failedAttempts;
    private boolean isAuthenticated;

    public Authentication(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.failedAttempts = 0;
        this.isAuthenticated = false;
    }

    public String getCardNumber() { return cardNumber; }
    public String getPin() { return pin; }
    public int getFailedAttempts() { return failedAttempts; }
    public boolean isAuthenticated() { return isAuthenticated; }
    public void incrementFailedAttempts() { failedAttempts++; }
    public void authenticate() { isAuthenticated = true; }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

class BalanceInquiry implements ATMOperation {
    private double balance;

    public BalanceInquiry(double balance) {
        this.balance = balance;
    }

    public double getBalance() { return balance; }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

class Withdrawal implements ATMOperation {
    private double amount;
    private double balance;

    public Withdrawal(double amount, double balance) {
        this.amount = amount;
        this.balance = balance;
    }

    public double getAmount() { return amount; }
    public double getBalance() { return balance; }
    public void updateBalance(double newBalance) { balance = newBalance; }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

class Deposit implements ATMOperation {
    private double depositAmount;

    public Deposit(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public double getDepositAmount() { return depositAmount; }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

class PinChange implements ATMOperation {
    private String oldPin;
    private String newPin;
    private String confirmNewPin;

    public PinChange(String oldPin, String newPin, String confirmNewPin) {
        this.oldPin = oldPin;
        this.newPin = newPin;
        this.confirmNewPin = confirmNewPin;
    }

    public String getOldPin() { return oldPin; }
    public String getNewPin() { return newPin; }
    public String getConfirmNewPin() { return confirmNewPin; }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

class PrintReceipt implements ATMOperation {
    private String transactionDetails;

    public PrintReceipt(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public String getTransactionDetails() { return transactionDetails; }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}

// Concrete Java.ChatGPT4o.Visitor Implementations
class ATMOperationVisitor implements ATMVisitor {

    @Override
    public void visit(Authentication auth) {
        // Simulate authentication logic
        if ("1234".equals(auth.getPin())) {
            auth.authenticate();
            System.out.println("Authentication successful!");
        } else {
            auth.incrementFailedAttempts();
            if (auth.getFailedAttempts() >= 3) {
                System.out.println("Card is locked due to too many failed attempts.");
            } else {
                System.out.println("Incorrect PIN. Please try again.");
            }
        }
    }

    @Override
    public void visit(BalanceInquiry balanceInquiry) {
        System.out.println("Current balance: $" + balanceInquiry.getBalance());
    }

    @Override
    public void visit(Withdrawal withdrawal) {
        if (withdrawal.getAmount() > withdrawal.getBalance()) {
            System.out.println("Insufficient balance.");
        } else {
            double newBalance = withdrawal.getBalance() - withdrawal.getAmount();
            withdrawal.updateBalance(newBalance);
            System.out.println("Please take your cash. New balance: $" + newBalance);
        }
    }

    @Override
    public void visit(Deposit deposit) {
        System.out.println("Deposited: $" + deposit.getDepositAmount());
        // Assuming the deposit is immediately added to the balance
    }

    @Override
    public void visit(PinChange pinChange) {
        if ("1234".equals(pinChange.getOldPin())) {
            if (pinChange.getNewPin().equals(pinChange.getConfirmNewPin())) {
                System.out.println("PIN changed successfully.");
            } else {
                System.out.println("New PINs do not match.");
            }
        } else {
            System.out.println("Old PIN is incorrect.");
        }
    }

    @Override
    public void visit(PrintReceipt printReceipt) {
        System.out.println("Printing receipt: " + printReceipt.getTransactionDetails());
    }
}

// Client code
public class CodeVisitor1 {
    public static void main(String[] args) {
        ATMVisitor visitor = new ATMOperationVisitor();

        // Authentication
        Authentication auth = new Authentication("1234567890", "1234");
        auth.accept(visitor);

        // Balance Inquiry
        if (auth.isAuthenticated()) {
            BalanceInquiry balanceInquiry = new BalanceInquiry(1000.00);
            balanceInquiry.accept(visitor);

            // Withdrawal
            Withdrawal withdrawal = new Withdrawal(200.00, 1000.00);
            withdrawal.accept(visitor);

            // Deposit
            Deposit deposit = new Deposit(500.00);
            deposit.accept(visitor);

            // PIN Change
            PinChange pinChange = new PinChange("1234", "5678", "5678");
            pinChange.accept(visitor);

            // Print Receipt
            PrintReceipt printReceipt = new PrintReceipt("Transaction completed: Withdrawal $200.00, Deposit $500.00");
            printReceipt.accept(visitor);
        }
    }
}
