package Java.ChatGPT4o.Visitor.round1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ATMSystemTest {
    private ATMVisitor visitor;

    @Before
    public void setUp() {
        visitor = new ATMOperationVisitor();
    }

    @Test
    public void testAuthenticationSuccess() {
        Authentication auth = new Authentication("1234567890", "1234");
        auth.accept(visitor);
        assertTrue(auth.isAuthenticated());
        assertEquals(0, auth.getFailedAttempts());
    }

    @Test
    public void testAuthenticationFailure() {
        Authentication auth = new Authentication("1234567890", "1111");
        auth.accept(visitor);
        assertFalse(auth.isAuthenticated());
        assertEquals(1, auth.getFailedAttempts());

        // Test second failure
        auth.accept(visitor);
        assertFalse(auth.isAuthenticated());
        assertEquals(2, auth.getFailedAttempts());

        // Test third failure and lock
        auth.accept(visitor);
        assertFalse(auth.isAuthenticated());
        assertEquals(3, auth.getFailedAttempts());
    }

    @Test
    public void testBalanceInquiry() {
        BalanceInquiry balanceInquiry = new BalanceInquiry(1000.00);
        balanceInquiry.accept(visitor);
        assertEquals(1000.00, balanceInquiry.getBalance(), 0.0);
    }

    @Test
    public void testWithdrawalSuccess() {
        Withdrawal withdrawal = new Withdrawal(200.00, 1000.00);
        withdrawal.accept(visitor);
        assertEquals(800.00, withdrawal.getBalance(), 0.0);
    }

    @Test
    public void testWithdrawalFailureInsufficientFunds() {
        Withdrawal withdrawal = new Withdrawal(1200.00, 1000.00);
        withdrawal.accept(visitor);
        assertEquals(1000.00, withdrawal.getBalance(), 0.0);  // Balance remains unchanged
    }

    @Test
    public void testDeposit() {
        Deposit deposit = new Deposit(500.00);
        deposit.accept(visitor);
        assertEquals(500.00, deposit.getDepositAmount(), 0.0);
    }

    @Test
    public void testPinChangeSuccess() {
        PinChange pinChange = new PinChange("1234", "5678", "5678");
        pinChange.accept(visitor);
        // Successful change
    }

    @Test
    public void testPinChangeFailureOldPinIncorrect() {
        PinChange pinChange = new PinChange("0000", "5678", "5678");
        pinChange.accept(visitor);
        // Old PIN incorrect, PIN change fails
    }

    @Test
    public void testPinChangeFailureNewPinsDoNotMatch() {
        PinChange pinChange = new PinChange("1234", "5678", "0000");
        pinChange.accept(visitor);
        // New PINs do not match, PIN change fails
    }

    @Test
    public void testPrintReceipt() {
        PrintReceipt printReceipt = new PrintReceipt("Transaction completed: Withdrawal $200.00, Deposit $500.00");
        printReceipt.accept(visitor);
        assertEquals("Transaction completed: Withdrawal $200.00, Deposit $500.00", printReceipt.getTransactionDetails());
    }
}

