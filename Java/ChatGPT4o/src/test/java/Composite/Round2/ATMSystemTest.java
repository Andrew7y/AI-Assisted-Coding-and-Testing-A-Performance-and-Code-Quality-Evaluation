package Composite.Round2;

import org.junit.jupiter.api.BeforeEach; // เปลี่ยนจาก @Before เป็น @BeforeEach
import org.junit.jupiter.api.Test; // เปลี่ยนจาก @Test ของ JUnit 4 เป็น @Test ของ JUnit 5

import static org.junit.jupiter.api.Assertions.assertEquals; // เปลี่ยนการนำเข้า Assert

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

    @BeforeEach // เปลี่ยนเป็น @BeforeEach
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

    @Test // ใช้ @Test จาก JUnit 5
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
        assertEquals(1300.0, cashDeposit.getBalance());

    }
}
