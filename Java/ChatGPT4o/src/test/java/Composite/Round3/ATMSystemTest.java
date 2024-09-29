package Composite.Round3;

import org.junit.jupiter.api.Test;

public class ATMSystemTest {

    @Test
    public void testAuthenticationSuccessful() {
        Authentication auth = new Authentication("123456789", "1234");
        auth.execute();
        // Expected output is a successful authentication.
    }

    @Test
    public void testAuthenticationWithWrongPin() {
        Authentication auth = new Authentication("123456789", "1111");
        auth.execute(); // First attempt, wrong PIN
        auth.execute(); // Second attempt, wrong PIN
        auth.execute(); // Third attempt, card retained
        auth.execute(); // Card has already been retained
    }

    @Test
    public void testBalanceInquiry() {
        BalanceInquiry balanceInquiry = new BalanceInquiry(5000.0);
        balanceInquiry.execute();
        // Expected output: Balance should be 5000.0
    }

    @Test
    public void testWithdrawalWithSufficientFunds() {
        Withdrawal withdrawal = new Withdrawal(5000.0, 1000.0);
        withdrawal.execute();
        // Expected output: Withdrawal of 1000.0, remaining balance 4000.0
    }

    @Test
    public void testWithdrawalWithInsufficientFunds() {
        Withdrawal withdrawal = new Withdrawal(500.0, 1000.0);
        withdrawal.execute();
        // Expected output: Insufficient funds message.
    }

    @Test
    public void testDeposit() {
        Deposit deposit = new Deposit(5000.0, 2000.0);
        deposit.execute();
        // Expected output: Deposit of 2000.0, new balance 7000.0
    }

    @Test
    public void testPinChangeWithCorrectOldPinAndMatchingNewPins() {
        PinChange pinChange = new PinChange("1234", "5678", "5678");
        pinChange.execute();
        // Expected output: PIN changed successfully.
    }

    @Test
    public void testPinChangeWithIncorrectOldPin() {
        PinChange pinChange = new PinChange("1111", "5678", "5678");
        pinChange.execute();
        // Expected output: Old PIN is incorrect.
    }

    @Test
    public void testPinChangeWithNonMatchingNewPins() {
        PinChange pinChange = new PinChange("1234", "5678", "9999");
        pinChange.execute();
        // Expected output: New PIN does not match the confirmation.
    }

    @Test
    public void testPrintReceipt() {
        PrintReceipt printReceipt = new PrintReceipt("Date: 2024-08-25\nTime: 10:00\nWithdraw: 1000\nBalance: 4000");
        printReceipt.execute();
        // Expected output: Print receipt with transaction details.
    }

    @Test
    public void testATMComposite() {
        ATMComposite atm = new ATMComposite();

        // Add all components for a full transaction scenario
        atm.addComponent(new Authentication("123456789", "1234"));
        atm.addComponent(new BalanceInquiry(5000.0));
        atm.addComponent(new Withdrawal(5000.0, 1000.0));
        atm.addComponent(new Deposit(4000.0, 2000.0));
        atm.addComponent(new PinChange("1234", "5678", "5678"));
        atm.addComponent(new PrintReceipt("Date: 2024-08-25\nTime: 10:00\nWithdraw: 1000\nBalance: 4000"));

        // Execute the full scenario
        atm.execute();

        // Expected output: Executes all components in the correct order.
    }
}
