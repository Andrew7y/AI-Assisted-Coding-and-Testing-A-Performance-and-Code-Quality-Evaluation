import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ATMTest {

    private ATM atm;

    @Before
    public void setUp() {
        atm = new ATM(1000, "1234567890", "1234");
    }

    @Test
    public void testAuthenticate() {
        assertTrue(atm.authenticate("1234567890", "1234")); // Correct PIN
        assertFalse(atm.authenticate("1234567890", "0000")); // Incorrect PIN
    }

    @Test
    public void testCheckBalance() {
        atm.authenticate("1234567890", "1234");
        assertEquals(1000, atm.checkBalance(), 0.01); // Check balance
    }

    @Test
    public void testWithdraw() {
        atm.authenticate("1234567890", "1234");
        assertTrue(atm.withdraw(200)); // Successful withdrawal
        assertEquals(800, atm.checkBalance(), 0.01); // Check balance after withdrawal
        assertFalse(atm.withdraw(2000)); // Insufficient funds
    }

    @Test
    public void testDeposit() {
        atm.authenticate("1234567890", "1234");
        atm.deposit(500);
        assertEquals(1500, atm.checkBalance(), 0.01); // Check balance after deposit
    }

    @Test
    public void testChangePin() {
        atm.authenticate("1234567890", "1234");
        assertTrue(atm.changePin("1234", "5678")); // Successful PIN change
        assertFalse(atm.changePin("1234", "0000")); // Incorrect old PIN
    }

    @Test
    public void testPrintReceipt() {
        atm.authenticate("1234567890", "1234");
        atm.withdraw(200);
        String receipt = atm.printReceipt();
        assertTrue(receipt.contains("200"));
        assertTrue(receipt.contains("balance"));
    }
}