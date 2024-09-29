package Visitor.Round3;

public interface ATMOperation {
    void accept(ATMVisitor visitor);
}
