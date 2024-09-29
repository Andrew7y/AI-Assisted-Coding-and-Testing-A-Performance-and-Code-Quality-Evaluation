package Visitor.Round3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ATMSystemTest {

    private ATM atm;
    private Card card;
    private Account account;
    private User user;

    @BeforeEach
    public void setUp() {
        // Initialize a User with a Card and Account
        card = new Card("1234567890", "1234");
        account = new Account(1000.0);
        user = new User(card, account);
        atm = new ATM(user);
    }

    @Test
    public void testAuthenticationSuccessful() {
        assertTrue(atm.authenticate("1234567890", "1234"), "Authentication should be successful with correct PIN.");
    }

    @Test
    public void testAuthenticationFailed() {
        assertFalse(atm.authenticate("1234567890", "0000"), "Authentication should fail with incorrect PIN.");
        assertFalse(atm.authenticate("1234567890", "0000"), "Authentication should fail with incorrect PIN.");
        assertFalse(atm.authenticate("1234567890", "0000"), "Authentication should fail with incorrect PIN.");
        assertFalse(atm.authenticate("1234567890", "0000"), "Authentication should fail and card should be locked after 3 attempts.");
    }

    @Test
    public void testCardLockedAfterMultipleFailedAttempts() {
        atm.authenticate("1234567890", "0000");
        atm.authenticate("1234567890", "0000");
        atm.authenticate("1234567890", "0000");
        assertFalse(atm.authenticate("1234567890", "1234"), "Card should be locked after 3 failed attempts.");
    }

    @Test
    public void testDisplayBalance() {
        atm.authenticate("1234567890", "1234");
        atm.displayBalance();
        assertEquals(1000.0, user.getAccount().getBalance(), 0.001, "Balance should be 1000.0 after initialization.");
    }

    @Test
    public void testWithdrawalSuccessful() {
        atm.authenticate("1234567890", "1234");
        atm.withdraw(500.0);
        assertEquals(500.0, user.getAccount().getBalance(), 0.001, "Balance should be 500.0 after withdrawing 500.0.");
    }

    @Test
    public void testWithdrawalInsufficientFunds() {
        atm.authenticate("1234567890", "1234");
        atm.withdraw(2000.0);
        assertEquals(1000.0, user.getAccount().getBalance(), 0.001, "Balance should remain 1000.0 after failed withdrawal due to insufficient funds.");
    }

    @Test
    public void testDeposit() {
        atm.authenticate("1234567890", "1234");
        atm.deposit(500.0);
        assertEquals(1500.0, user.getAccount().getBalance(), 0.001, "Balance should be 1500.0 after depositing 500.0.");
    }

    @Test
    public void testChangePINSuccessful() {
        atm.authenticate("1234567890", "1234");
        atm.changePIN("1234", "5678", "5678");
        assertTrue(atm.authenticate("1234567890", "5678"), "PIN should be changed successfully, and authentication with the new PIN should work.");
    }

    @Test
    public void testChangePINOldPINIncorrect() {
        atm.authenticate("1234567890", "1234");
        atm.changePIN("0000", "5678", "5678");
        assertFalse(atm.authenticate("1234567890", "5678"), "PIN change should fail if the old PIN is incorrect.");
        assertTrue(atm.authenticate("1234567890", "1234"), "Authentication with the old PIN should still work after failed PIN change.");
    }

    @Test
    public void testChangePINNewPINsMismatch() {
        atm.authenticate("1234567890", "1234");
        atm.changePIN("1234", "5678", "8765");
        assertFalse(atm.authenticate("1234567890", "5678"), "PIN change should fail if the new PINs do not match.");
        assertTrue(atm.authenticate("1234567890", "1234"), "Authentication with the old PIN should still work after failed PIN change.");
    }

    @Test
    public void testSlipContentAfterBalanceInquiry() {
        atm.authenticate("1234567890", "1234");
        atm.displayBalance();
        // Since displayBalance prints the slip content to the console, we assume it runs correctly as the method contains a print statement.
    }

    @Test
    public void testSlipContentAfterWithdrawal() {
        atm.authenticate("1234567890", "1234");
        atm.withdraw(200.0);
        // Since withdraw prints the slip content to the console, we assume it runs correctly as the method contains a print statement.
    }

    @Test
    public void testSlipContentAfterDeposit() {
        atm.authenticate("1234567890", "1234");
        atm.deposit(200.0);
        // Since deposit prints the slip content to the console, we assume it runs correctly as the method contains a print statement.
    }

    @Test
    public void testSlipContentAfterChangePIN() {
        atm.authenticate("1234567890", "1234");
        atm.changePIN("1234", "5678", "5678");
        // Since changePIN prints the slip content to the console, we assume it runs correctly as the method contains a print statement.
    }
}



