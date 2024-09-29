package Composite.Round3;

import java.util.ArrayList;
import java.util.List;

public class ATMCompositeOperation implements ATMOperation {
    private List<ATMOperation> operations = new ArrayList<>();

    public void addOperation(ATMOperation operation) {
        operations.add(operation);
    }

    @Override
    public void execute() {
        for (ATMOperation operation : operations) {
            operation.execute();
        }
    }
}
