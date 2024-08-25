package visitor.java.round2.scr;

public class AuthenticateUser implements ATMOperation {
    private String cardNumber;
    private String pin;

    public AuthenticateUser(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}
