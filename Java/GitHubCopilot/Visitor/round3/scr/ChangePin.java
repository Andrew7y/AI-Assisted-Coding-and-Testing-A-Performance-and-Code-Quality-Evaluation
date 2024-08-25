package visitor.java.round3.scr;

public class ChangePin implements ATMOperation {
    private String oldPin;
    private String newPin;

    public ChangePin(String oldPin, String newPin) {
        this.oldPin = oldPin;
        this.newPin = newPin;
    }

    public String getOldPin() {
        return oldPin;
    }

    public String getNewPin() {
        return newPin;
    }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}
