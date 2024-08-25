package visitor.java.round1.scr;

public class CheckBalance implements ATMOperation {
    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}
