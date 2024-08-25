package visitor.java.round3.scr;

public interface ATMVisitor {
    void visit(AuthenticateUser authenticateUser);
    void visit(CheckBalance checkBalance);
    void visit(WithdrawMoney withdrawMoney);
    void visit(DepositMoney depositMoney);
    void visit(ChangePin changePin);
    void visit(PrintReceipt printReceipt);
}
