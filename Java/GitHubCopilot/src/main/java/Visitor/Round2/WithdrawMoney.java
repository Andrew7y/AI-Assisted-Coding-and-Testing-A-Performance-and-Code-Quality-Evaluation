package Visitor.Round2;

public class WithdrawMoney implements ATMOperation {
    private int amount;

    public WithdrawMoney(int amount) {
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
