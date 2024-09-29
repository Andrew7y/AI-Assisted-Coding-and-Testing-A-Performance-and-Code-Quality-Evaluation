package Composite.Round3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {

    private ATM atm;

    @BeforeEach
    public void setUp() {
        atm = new ATM(1000, "1234567890", "1234");
    }

    @Test
    public void testAuthenticate() {
        assertTrue(atm.authenticate("1234567890", "1234")); // Correct PIN
        assertFalse(atm.authenticate("1234567890", "0000")); // Incorrect PIN
        assertFalse(atm.authenticate("0000000000", "1234")); // Incorrect card number
        atm.authenticate("1234567890", "0000");
        atm.authenticate("1234567890", "0000");
        assertFalse(atm.authenticate("1234567890", "0000")); // Card retained after 3 failed attempts
    }

    @Test
    public void testCheckBalance() {
        atm.authenticate("1234567890", "1234");
        assertEquals(1000, atm.checkBalance(), 0.01); // Check balance
        atm.authenticate("1234567890", "0000");
        assertEquals(-1, atm.checkBalance(), 0.01); // Not authenticated
    }

    @Test
    public void testWithdraw() {
        atm.authenticate("1234567890", "1234");
        assertTrue(atm.withdraw(200)); // Successful withdrawal
        assertEquals(800, atm.checkBalance(), 0.01); // Check balance after withdrawal
        assertFalse(atm.withdraw(2000)); // Insufficient funds
        atm.authenticate("1234567890", "0000");
        assertFalse(atm.withdraw(100)); // Not authenticated
    }

    @Test
    public void testDeposit() {
        atm.authenticate("1234567890", "1234");
        atm.deposit(500);
        assertEquals(1500, atm.checkBalance(), 0.01); // Check balance after deposit
        atm.authenticate("1234567890", "0000");
        atm.deposit(500); // Not authenticated
    }

    @Test
    public void testChangePin() {
        atm.authenticate("1234567890", "1234");
        assertTrue(atm.changePin("1234", "5678")); // Successful PIN change
        assertFalse(atm.changePin("1234", "0000")); // Incorrect old PIN
        atm.authenticate("1234567890", "0000");
        assertFalse(atm.changePin("1234", "5678")); // Not authenticated
    }

    @Test
    public void testPrintReceipt() {
        atm.authenticate("1234567890", "1234");
        atm.withdraw(200);
        atm.printReceipt(); // Print receipt after transaction
        atm.authenticate("1234567890", "0000");
        atm.printReceipt(); // Not authenticated
    }
}
