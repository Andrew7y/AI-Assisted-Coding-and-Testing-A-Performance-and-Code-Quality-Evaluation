package Composite.Round3;

public class WithdrawMoney implements ATMOperation {
    private ATM atm;
    private double amount;

    public WithdrawMoney(ATM atm, double amount) {
        this.atm = atm;
        this.amount = amount;
    }

    @Override
    public void execute() {
        atm.withdraw(amount);
    }
}
