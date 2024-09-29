package Composite.Round1;

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
