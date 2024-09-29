package Composite.Round3;

public class PrintReceipt implements ATMOperation {
    private ATM atm;

    public PrintReceipt(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void execute() {
        atm.printReceipt();
    }
}
