package Java.ChatGPT4o.Composite.round1;

import java.util.ArrayList;
import java.util.List;

interface ATMComponent {
    void performOperation();
}

class Authentication implements ATMComponent {
    @Override
    public void performOperation() {
        System.out.println("Authenticating user with ATM card and PIN.");
        // Code for card insertion and PIN validation
    }
}

class BalanceInquiry implements ATMComponent {
    @Override
    public void performOperation() {
        System.out.println("Displaying account balance.");
        // Code to display account balance
    }
}

class CashWithdrawal implements ATMComponent {
    @Override
    public void performOperation() {
        System.out.println("Withdrawing cash.");
        // Code for selecting amount, checking balance, and dispensing cash
    }
}

class CashDeposit implements ATMComponent {
    @Override
    public void performOperation() {
        System.out.println("Depositing cash.");
        // Code to accept and count cash, and update balance
    }
}

class ChangePin implements ATMComponent {
    @Override
    public void performOperation() {
        System.out.println("Changing PIN.");
        // Code to request and validate old and new PINs, then update the PIN
    }
}

class PrintReceipt implements ATMComponent {
    @Override
    public void performOperation() {
        System.out.println("Printing receipt.");
        // Code to print the transaction receipt
    }
}

class ATMComposite implements ATMComponent {
    private List<ATMComponent> components = new ArrayList<>();

    public void addComponent(ATMComponent component) {
        components.add(component);
    }

    public void removeComponent(ATMComponent component) {
        components.remove(component);
    }

    @Override
    public void performOperation() {
        for (ATMComponent component : components) {
            component.performOperation();
        }
    }
}

public class CodeComposite1 {
    public static void main(String[] args) {
        ATMComposite atm = new ATMComposite();

        // Adding ATM operations
        atm.addComponent(new Authentication());
        atm.addComponent(new BalanceInquiry());
        atm.addComponent(new CashWithdrawal());
        atm.addComponent(new CashDeposit());
        atm.addComponent(new ChangePin());
        atm.addComponent(new PrintReceipt());

        // Perform all ATM operations
        System.out.println("Performing ATM operations:");
        atm.performOperation();
    }
}

