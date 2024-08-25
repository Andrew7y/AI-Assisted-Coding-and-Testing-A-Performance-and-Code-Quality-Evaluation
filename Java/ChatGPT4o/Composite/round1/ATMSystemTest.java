package Java.ChatGPT4o.Composite.round1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ATMSystemTest {

    private ATMComposite atm;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        atm = new ATMComposite();

        // Add components to the ATM
        atm.addComponent(new Authentication());
        atm.addComponent(new BalanceInquiry());
        atm.addComponent(new CashWithdrawal());
        atm.addComponent(new CashDeposit());
        atm.addComponent(new ChangePin());
        atm.addComponent(new PrintReceipt());

        // Redirect System.out to capture output for assertion
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testAuthenticationOperation() {
        ATMComponent authentication = new Authentication();
        authentication.performOperation();
        assertTrue(outputStreamCaptor.toString().trim().contains("Authenticating user with ATM card and PIN."));
    }

    @Test
    void testBalanceInquiryOperation() {
        ATMComponent balanceInquiry = new BalanceInquiry();
        balanceInquiry.performOperation();
        assertTrue(outputStreamCaptor.toString().trim().contains("Displaying account balance."));
    }

    @Test
    void testCashWithdrawalOperation() {
        ATMComponent cashWithdrawal = new CashWithdrawal();
        cashWithdrawal.performOperation();
        assertTrue(outputStreamCaptor.toString().trim().contains("Withdrawing cash."));
    }

    @Test
    void testCashDepositOperation() {
        ATMComponent cashDeposit = new CashDeposit();
        cashDeposit.performOperation();
        assertTrue(outputStreamCaptor.toString().trim().contains("Depositing cash."));
    }

    @Test
    void testChangePinOperation() {
        ATMComponent changePin = new ChangePin();
        changePin.performOperation();
        assertTrue(outputStreamCaptor.toString().trim().contains("Changing PIN."));
    }

    @Test
    void testPrintReceiptOperation() {
        ATMComponent printReceipt = new PrintReceipt();
        printReceipt.performOperation();
        assertTrue(outputStreamCaptor.toString().trim().contains("Printing receipt."));
    }

    @Test
    void testATMCompositeOperation() {
        // Perform all ATM operations
        atm.performOperation();

        // Check if all components were executed
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Authenticating user with ATM card and PIN."));
        assertTrue(output.contains("Displaying account balance."));
        assertTrue(output.contains("Withdrawing cash."));
        assertTrue(output.contains("Depositing cash."));
        assertTrue(output.contains("Changing PIN."));
        assertTrue(output.contains("Printing receipt."));
    }

    @Test
    void testAddingAndRemovingComponentsInATMComposite() {
        // Create a new composite with only two operations
        ATMComposite customATM = new ATMComposite();
        ATMComponent authentication = new Authentication();
        ATMComponent balanceInquiry = new BalanceInquiry();

        customATM.addComponent(authentication);
        customATM.addComponent(balanceInquiry);

        // Perform operations before removal
        customATM.performOperation();
        String outputBeforeRemoval = outputStreamCaptor.toString().trim();
        assertTrue(outputBeforeRemoval.contains("Authenticating user with ATM card and PIN."));
        assertTrue(outputBeforeRemoval.contains("Displaying account balance."));

        // Remove one component and perform operations again
        customATM.removeComponent(balanceInquiry);
        outputStreamCaptor.reset(); // Clear the output stream for the next test
        customATM.performOperation();

        String outputAfterRemoval = outputStreamCaptor.toString().trim();
        assertTrue(outputAfterRemoval.contains("Authenticating user with ATM card and PIN."));
        assertFalse(outputAfterRemoval.contains("Displaying account balance."));
    }
}

