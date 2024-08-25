package visitor.java.round2.scr;

public class ATMVisitorImpl implements ATMVisitor {
    private ATM atm;

    public ATMVisitorImpl(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void visit(AuthenticateUser authenticateUser) {
        atm.authenticate(authenticateUser.getCardNumber(), authenticateUser.getPin());
    }

    @Override
    public void visit(CheckBalance checkBalance) {
        atm.checkBalance();
    }

    @Override
    public void visit(WithdrawMoney withdrawMoney) {
        atm.withdraw(withdrawMoney.getAmount());
    }

    @Override
    public void visit(DepositMoney depositMoney) {
        atm.deposit(depositMoney.getAmount());
    }

    @Override
    public void visit(ChangePin changePin) {
        atm.changePin(changePin.getOldPin(), changePin.getNewPin());
    }

    @Override
    public void visit(PrintReceipt printReceipt) {
        atm.printReceipt();
    }
}
