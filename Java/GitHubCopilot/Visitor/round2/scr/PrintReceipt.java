package visitor.java.round2.scr;

public class PrintReceipt implements ATMOperation {
    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}
