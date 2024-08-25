package java.round2.scr;

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
