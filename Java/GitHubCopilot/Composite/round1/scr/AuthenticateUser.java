package java.round1.scr;

public class AuthenticateUser implements ATMOperation {
    private ATM atm;
    private String cardNumber;
    private String pin;

    public AuthenticateUser(ATM atm, String cardNumber, String pin) {
        this.atm = atm;
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    @Override
    public void execute() {
        atm.authenticate(cardNumber, pin);
    }
}
