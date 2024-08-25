package visitor.java.round1.scr;

public class DepositMoney implements ATMOperation {
    private int amount;

    public DepositMoney(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public void accept(ATMVisitor visitor) {
        visitor.visit(this);
    }
}
