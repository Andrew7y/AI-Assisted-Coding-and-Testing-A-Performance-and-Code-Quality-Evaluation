package Composite.Round3;

public class CheckBalance implements ATMOperation {
    private ATM atm;

    public CheckBalance(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void execute() {
        atm.checkBalance();
    }
}
