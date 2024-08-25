package visitor.java.round1.scr;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM(1000, "1234567890", "1234");
        ATMVisitor visitor = new ATMVisitorImpl(atm);

        ATMOperation[] operations = {
            new AuthenticateUser("1234567890", "1234"),
            new CheckBalance(),
            new WithdrawMoney(200),
            new DepositMoney(500),
            new ChangePin("1234", "5678"),
            new PrintReceipt()
        };

        for (ATMOperation operation : operations) {
            operation.accept(visitor);
        }
    }
}
