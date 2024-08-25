package visitor.java.round1.scr;

public class PrintReceipt implements ATMOperation {
    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}
