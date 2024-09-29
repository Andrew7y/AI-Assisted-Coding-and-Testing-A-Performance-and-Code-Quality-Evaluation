package Composite.Round2;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM(1000, "1234567890", "1234");

        ATMCompositeOperation compositeOperation = new ATMCompositeOperation();
        compositeOperation.addOperation(new AuthenticateUser(atm, "1234567890", "1234"));
        compositeOperation.addOperation(new CheckBalance(atm));
        compositeOperation.addOperation(new WithdrawMoney(atm, 200));
        compositeOperation.addOperation(new DepositMoney(atm, 500));
        compositeOperation.addOperation(new ChangePin(atm, "1234", "5678"));
        compositeOperation.addOperation(new PrintReceipt(atm));

        compositeOperation.execute();
    }
}
