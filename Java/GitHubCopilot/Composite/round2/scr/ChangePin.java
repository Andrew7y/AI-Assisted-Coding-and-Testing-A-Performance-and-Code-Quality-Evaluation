package java.round2.scr;

public class ChangePin implements ATMOperation {
    private ATM atm;
    private String oldPin;
    private String newPin;

    public ChangePin(ATM atm, String oldPin, String newPin) {
        this.atm = atm;
        this.oldPin = oldPin;
        this.newPin = newPin;
    }

    @Override
    public void execute() {
        atm.changePin(oldPin, newPin);
    }
}
