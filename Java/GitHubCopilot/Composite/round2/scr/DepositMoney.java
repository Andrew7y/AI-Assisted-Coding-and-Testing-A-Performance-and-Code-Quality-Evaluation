package java.round2.scr;

public class DepositMoney implements ATMOperation {
    private ATM atm;
    private double amount;

    public DepositMoney(ATM atm, double amount) {
        this.atm = atm;
        this.amount = amount;
    }

    @Override
    public void execute() {
        atm.deposit(amount);
    }
}
