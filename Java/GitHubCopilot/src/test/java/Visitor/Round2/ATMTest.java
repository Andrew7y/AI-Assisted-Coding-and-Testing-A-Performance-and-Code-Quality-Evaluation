package Visitor.Round2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {
    private ATM atm;
    private ATMVisitorImpl visitor;

    @BeforeEach
    public void setUp() {
        atm = new ATM(1000, "1234567890", "1234");
        visitor = new ATMVisitorImpl(atm);
    }

    @Test
    public void testAuthenticateUser() {
        AuthenticateUser operation = new AuthenticateUser("1234567890", "1234");
        operation.accept(visitor);
        assertTrue(atm.authenticate("1234567890", "1234"));

        operation = new AuthenticateUser("1234567890", "0000");
        operation.accept(visitor);
        assertFalse(atm.authenticate("1234567890", "0000"));
    }

    @Test
    public void testCheckBalance() {
        atm.authenticate("1234567890", "1234");
        CheckBalance operation = new CheckBalance();
        operation.accept(visitor);
        assertEquals(1000, atm.checkBalance());
    }

    @Test
    public void testWithdrawMoney() {
        atm.authenticate("1234567890", "1234");
        WithdrawMoney operation = new WithdrawMoney(200);
        operation.accept(visitor);
        assertEquals(800, atm.checkBalance());

        operation = new WithdrawMoney(2000);
        operation.accept(visitor);
        assertEquals(800, atm.checkBalance());
    }

    @Test
    public void testDepositMoney() {
        atm.authenticate("1234567890", "1234");
        DepositMoney operation = new DepositMoney(500);
        operation.accept(visitor);
        assertEquals(1500, atm.checkBalance());
    }

    @Test
    public void testChangePin() {
        atm.authenticate("1234567890", "1234");
        ChangePin operation = new ChangePin("1234", "5678");
        operation.accept(visitor);
        assertTrue(atm.changePin("1234", "5678"));

        operation = new ChangePin("1234", "0000");
        operation.accept(visitor);
        assertFalse(atm.changePin("1234", "0000"));
    }

    @Test
    public void testPrintReceipt() {
        atm.authenticate("1234567890", "1234");
        PrintReceipt operation = new PrintReceipt();
        operation.accept(visitor);
        // Assuming printReceipt prints to console, no assertion needed
    }
}
