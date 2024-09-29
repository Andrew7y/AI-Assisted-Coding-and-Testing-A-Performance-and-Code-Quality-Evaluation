package Visitor.Round1;

public interface ATMOperation {
    void accept(ATMVisitor visitor);
}
