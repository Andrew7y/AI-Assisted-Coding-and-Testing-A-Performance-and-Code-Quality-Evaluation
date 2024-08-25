package Java.ChatGPT4o.Composite.round2;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ATMSystemTest {

    private AuthenticateUser authUserSuccess;
    private AuthenticateUser authUserFail;
    private BalanceInquiry balanceInquiry;
    private CashWithdrawal cashWithdrawalSuccess;
    private CashWithdrawal cashWithdrawalFail;
    private CashDeposit cashDeposit;
    private ChangePIN changePinSuccess;
    private ChangePIN changePinFail;
    private PrintReceipt printReceipt;

    @Before
    public void setUp() {
        // Set up test data
        authUserSuccess = new AuthenticateUser("1234-5678-9012-3456", "1234");
        authUserFail = new AuthenticateUser("1234-5678-9012-3456", "0000");

        balanceInquiry = new BalanceInquiry(1000.0);

        cashWithdrawalSuccess = new CashWithdrawal(1000.0, 200.0);
        cashWithdrawalFail = new CashWithdrawal(1000.0, 1500.0);

        cashDeposit = new CashDeposit(1000.0, 300.0);

        changePinSuccess = new ChangePIN("1234", "5678", "5678");
        changePinFail = new ChangePIN("1234", "5678", "0000");

        printReceipt = new PrintReceipt("Transaction Details: Date: 2024-08-24, Amount: $100, Balance: $1100");
    }

    @Test
    public void testAuthenticateUserSuccess() {
        authUserSuccess.execute();
    }

    @Test
    public void testAuthenticateUserFail() {
        authUserFail.execute();
    }

    @Test
    public void testBalanceInquiry() {
        balanceInquiry.execute();
    }

    @Test
    public void testCashWithdrawalSuccess() {
        cashWithdrawalSuccess.execute();
        assertEquals(800.0, cashWithdrawalSuccess.getBalance(), 0.01);
    }

    @Test
    public void testCashWithdrawalFail() {
        cashWithdrawalFail.execute();
        assertEquals(1000.0, cashWithdrawalFail.getBalance(), 0.01); // Balance should remain unchanged
    }

    @Test
    public void testCashDeposit() {
        cashDeposit.execute();
        assertEquals(1300.0, cashDeposit.getBalance(), 0.01);
    }

    @Test
    public void testChangePinSuccess() {
        changePinSuccess.execute();
    }

    @Test
    public void testChangePinFail() {
        changePinFail.execute();
    }

    @Test
    public void testPrintReceipt() {
        printReceipt.execute();
    }

    @Test
    public void testCompositeATMOperation() {
        // Test the composite operation
        CompositeATMOperation compositeOperation = new CompositeATMOperation();
        compositeOperation.addOperation(authUserSuccess);
        compositeOperation.addOperation(balanceInquiry);
        compositeOperation.addOperation(cashWithdrawalSuccess);
        compositeOperation.addOperation(cashDeposit);
        compositeOperation.addOperation(printReceipt);

        compositeOperation.execute();

        // Verifying final balance after composite operations
        assertEquals(1100.0, cashDeposit.getBalance(), 0.01);
    }
}

