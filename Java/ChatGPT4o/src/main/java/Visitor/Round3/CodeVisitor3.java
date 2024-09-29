package Visitor.Round3;

import java.util.Date;

// Element Interface
interface Element {
    void accept(Visitor visitor);
}

// Java.ChatGPT4o.Visitor Interface
interface Visitor {
    void visit(User user);
    void visit(Card card);
    void visit(Account account);
}

// Concrete Elements
class User implements Element {
    private Card card;
    private Account account;

    public User(Card card, Account account) {
        this.card = card;
        this.account = account;
    }

    public Card getCard() {
        return card;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        card.accept(visitor);
        account.accept(visitor);
    }
}

class Card implements Element {
    private String cardNumber;
    private String pin;
    private boolean isLocked;
    private int failedAttempts;

    public Card(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.isLocked = false;
        this.failedAttempts = 0;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void lockCard() {
        this.isLocked = true;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    public void resetFailedAttempts() {
        this.failedAttempts = 0;
    }

    public void setPin(String newPin) {
        this.pin = newPin;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class Account implements Element {
    private double balance;

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if(balance >= amount){
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Concrete Visitors
class AuthenticationVisitor implements Visitor {
    private String enteredCardNumber;
    private String enteredPIN;
    private boolean authenticated = false;
    private boolean cardLocked = false;
    private static final int MAX_ATTEMPTS = 3;

    public AuthenticationVisitor(String enteredCardNumber, String enteredPIN) {
        this.enteredCardNumber = enteredCardNumber;
        this.enteredPIN = enteredPIN;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public boolean isCardLocked() {
        return cardLocked;
    }

    @Override
    public void visit(User user) {
        // No action needed for User in authentication
    }

    @Override
    public void visit(Card card) {
        if(card.isLocked()) {
            cardLocked = true;
            return;
        }
        if(card.getCardNumber().equals(enteredCardNumber)) {
            if(card.getPin().equals(enteredPIN)) {
                authenticated = true;
                card.resetFailedAttempts();
            } else {
                card.incrementFailedAttempts();
                if(card.getFailedAttempts() >= MAX_ATTEMPTS) {
                    card.lockCard();
                    cardLocked = true;
                }
            }
        }
    }

    @Override
    public void visit(Account account) {
        // No action needed for Account in authentication
    }
}

class BalanceInquiryVisitor implements Visitor {
    private double balance;

    public double getBalance() {
        return balance;
    }

    @Override
    public void visit(User user) {
        // No action needed for User in balance inquiry
    }

    @Override
    public void visit(Card card) {
        // No action needed for Card in balance inquiry
    }

    @Override
    public void visit(Account account) {
        balance = account.getBalance();
    }
}

class WithdrawalVisitor implements Visitor {
    private double amount;
    private boolean success;
    private boolean insufficientFunds;

    public WithdrawalVisitor(double amount) {
        this.amount = amount;
        this.success = false;
        this.insufficientFunds = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isInsufficientFunds() {
        return insufficientFunds;
    }

    @Override
    public void visit(User user) {
        // No action needed for User in withdrawal
    }

    @Override
    public void visit(Card card) {
        // No action needed for Card in withdrawal
    }

    @Override
    public void visit(Account account) {
        if(account.getBalance() >= amount) {
            account.withdraw(amount);
            success = true;
        } else {
            insufficientFunds = true;
        }
    }
}

class DepositVisitor implements Visitor {
    private double amount;

    public DepositVisitor(double amount) {
        this.amount = amount;
    }

    @Override
    public void visit(User user) {
        // No action needed for User in deposit
    }

    @Override
    public void visit(Card card) {
        // No action needed for Card in deposit
    }

    @Override
    public void visit(Account account) {
        account.deposit(amount);
    }
}

class ChangePINVisitor implements Visitor {
    private String oldPIN;
    private String newPIN1;
    private String newPIN2;
    private boolean success;
    private boolean pinMismatch;

    public ChangePINVisitor(String oldPIN, String newPIN1, String newPIN2) {
        this.oldPIN = oldPIN;
        this.newPIN1 = newPIN1;
        this.newPIN2 = newPIN2;
        this.success = false;
        this.pinMismatch = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isPinMismatch() {
        return pinMismatch;
    }

    @Override
    public void visit(User user) {
        // No action needed for User in PIN change
    }

    @Override
    public void visit(Card card) {
        if(card.getPin().equals(oldPIN)) {
            if(newPIN1.equals(newPIN2)) {
                card.setPin(newPIN1);
                success = true;
            } else {
                pinMismatch = true;
            }
        }
    }

    @Override
    public void visit(Account account) {
        // No action needed for Account in PIN change
    }
}

class PrintSlipVisitor implements Visitor {
    private String transactionType;
    private double amount;
    private boolean success;
    private double balance;
    private String message;
    private StringBuilder slipContent = new StringBuilder();

    public void setTransactionDetails(String transactionType, double amount, boolean success, double balance, String message) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.success = success;
        this.balance = balance;
        this.message = message;
    }

    public String getSlipContent() {
        return slipContent.toString();
    }

    @Override
    public void visit(User user) {
        // Add transaction type and date/time
        Date now = new Date();
        slipContent.append("Transaction: ").append(transactionType).append("\n");
        slipContent.append("Date: ").append(now.toString()).append("\n");
    }

    @Override
    public void visit(Card card) {
        // Include card number
        slipContent.append("Card Number: ").append(card.getCardNumber()).append("\n");
    }

    @Override
    public void visit(Account account) {
        // Include transaction result and balance
        slipContent.append("Transaction Successful: ").append(success ? "Yes" : "No").append("\n");
        if(amount > 0) {
            slipContent.append("Amount: ").append(amount).append("\n");
        }
        slipContent.append("Balance: ").append(balance).append("\n");
        if(message != null && !message.isEmpty()) {
            slipContent.append("Message: ").append(message).append("\n");
        }
    }
}

// ATM Class to handle operations
class ATM {
    private User user;

    public ATM(User user) {
        this.user = user;
    }

    // Method to authenticate the user
    public boolean authenticate(String cardNumber, String pin) {
        AuthenticationVisitor authVisitor = new AuthenticationVisitor(cardNumber, pin);
        user.accept(authVisitor);
        if(authVisitor.isCardLocked()) {
            System.out.println("Card is locked due to multiple failed attempts.");
            return false;
        }
        if(authVisitor.isAuthenticated()) {
            System.out.println("Authentication successful.");
            return true;
        } else {
            System.out.println("Authentication failed.");
            return false;
        }
    }

    // Method to display balance
    public void displayBalance() {
        BalanceInquiryVisitor balanceVisitor = new BalanceInquiryVisitor();
        user.accept(balanceVisitor);
        System.out.println("Current balance: " + balanceVisitor.getBalance());

        // Print slip for balance inquiry
        PrintSlipVisitor printSlip = new PrintSlipVisitor();
        printSlip.setTransactionDetails("Balance Inquiry", 0, true, balanceVisitor.getBalance(), "");
        user.accept(printSlip);
        System.out.println("Slip Content:\n" + printSlip.getSlipContent());
    }

    // Method to withdraw money
    public void withdraw(double amount) {
        WithdrawalVisitor withdrawalVisitor = new WithdrawalVisitor(amount);
        user.accept(withdrawalVisitor);
        if(withdrawalVisitor.isSuccess()) {
            System.out.println("Withdrawal successful.");
        } else if(withdrawalVisitor.isInsufficientFunds()) {
            System.out.println("Insufficient funds.");
        }

        // After withdrawal, print slip
        double currentBalance = user.getAccount().getBalance();
        String message = withdrawalVisitor.isInsufficientFunds() ? "Insufficient funds." : "";
        PrintSlipVisitor printSlip = new PrintSlipVisitor();
        printSlip.setTransactionDetails("Withdrawal", amount, withdrawalVisitor.isSuccess(), currentBalance, message);
        user.accept(printSlip);
        System.out.println("Slip Content:\n" + printSlip.getSlipContent());
    }

    // Method to deposit money
    public void deposit(double amount) {
        DepositVisitor depositVisitor = new DepositVisitor(amount);
        user.accept(depositVisitor);
        System.out.println("Deposit successful.");

        // After deposit, print slip
        double currentBalance = user.getAccount().getBalance();
        PrintSlipVisitor printSlip = new PrintSlipVisitor();
        printSlip.setTransactionDetails("Deposit", amount, true, currentBalance, "");
        user.accept(printSlip);
        System.out.println("Slip Content:\n" + printSlip.getSlipContent());
    }

    // Method to change PIN
    public void changePIN(String oldPIN, String newPIN1, String newPIN2) {
        ChangePINVisitor changePINVisitor = new ChangePINVisitor(oldPIN, newPIN1, newPIN2);
        user.accept(changePINVisitor);
        if(changePINVisitor.isSuccess()) {
            System.out.println("PIN changed successfully.");
        } else if(changePINVisitor.isPinMismatch()) {
            System.out.println("New PIN entries do not match.");
        } else {
            System.out.println("Old PIN is incorrect.");
        }

        // After PIN change, print slip
        String message;
        if(changePINVisitor.isSuccess()) {
            message = "PIN change successful.";
        } else if(changePINVisitor.isPinMismatch()) {
            message = "PIN change failed: new PINs do not match.";
        } else {
            message = "PIN change failed: old PIN incorrect.";
        }

        PrintSlipVisitor printSlip = new PrintSlipVisitor();
        printSlip.setTransactionDetails("Change PIN", 0, changePINVisitor.isSuccess(), user.getAccount().getBalance(), message);
        user.accept(printSlip);
        System.out.println("Slip Content:\n" + printSlip.getSlipContent());
    }
}

// Main Class to demonstrate ATM operations
public class CodeVisitor3 {
    public static void main(String[] args) {
        // Initialize a User with a Card and Account
        Card card = new Card("1234567890", "1234");
        Account account = new Account(1000.0);
        User user = new User(card, account);
        ATM atm = new ATM(user);

        // Simulate ATM operations
        // Authenticate the user
        boolean isAuthenticated = atm.authenticate("1234567890", "1234");
        if(isAuthenticated) {
            // Display balance
            atm.displayBalance();

            // Withdraw money
            atm.withdraw(200.0);

            // Deposit money
            atm.deposit(500.0);

            // Change PIN
            atm.changePIN("1234", "5678", "5678");

            // Attempt to change PIN with mismatched new PIN entries
            atm.changePIN("5678", "abcd", "efgh");

            // Attempt to authenticate with wrong PIN multiple times to lock the card
            atm.authenticate("1234567890", "0000");
            atm.authenticate("1234567890", "0000");
            atm.authenticate("1234567890", "0000");
            // Now the card should be locked
            atm.authenticate("1234567890", "5678");
        }
    }
}



