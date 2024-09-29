package Visitor.Round2;

public interface ATMOperation {
    void accept(ATMVisitor visitor);
}
