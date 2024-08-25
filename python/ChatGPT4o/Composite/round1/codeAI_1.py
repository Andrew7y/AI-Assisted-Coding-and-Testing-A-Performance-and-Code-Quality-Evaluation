from abc import ABC, abstractmethod

# Component Interface
class ATMOperation(ABC):
    @abstractmethod
    def execute(self):
        pass

# Leaf classes
class Authentication(ATMOperation):
    def __init__(self, card_number, pin):
        self.card_number = card_number
        self.pin = pin
        self.attempts = 0
        self.max_attempts = 3
        self.valid_pin = "1234"  # Example PIN for testing

    def execute(self):
        while self.attempts < self.max_attempts:
            entered_pin = input("Enter your PIN: ")
            if entered_pin == self.valid_pin:
                print("Authentication successful.")
                return True
            else:
                self.attempts += 1
                print(f"Invalid PIN. Attempts left: {self.max_attempts - self.attempts}")

        print("Card has been retained due to multiple failed attempts.")
        return False

class BalanceInquiry(ATMOperation):
    def __init__(self, balance):
        self.balance = balance

    def execute(self):
        print(f"Your current balance is: {self.balance} units.")

class Withdrawal(ATMOperation):
    def __init__(self, balance):
        self.balance = balance

    def execute(self):
        amount = float(input("Enter amount to withdraw: "))
        if amount <= self.balance:
            self.balance -= amount
            print(f"Please take your cash. Your new balance is: {self.balance} units.")
        else:
            print("Insufficient funds.")

class Deposit(ATMOperation):
    def __init__(self, balance):
        self.balance = balance

    def execute(self):
        amount = float(input("Enter amount to deposit: "))
        self.balance += amount
        print(f"Amount deposited successfully. Your new balance is: {self.balance} units.")

class PinChange(ATMOperation):
    def __init__(self, pin):
        self.pin = pin

    def execute(self):
        old_pin = input("Enter your current PIN: ")
        if old_pin == self.pin:
            new_pin = input("Enter new PIN: ")
            confirm_pin = input("Confirm new PIN: ")
            if new_pin == confirm_pin:
                self.pin = new_pin
                print("PIN changed successfully.")
            else:
                print("PINs do not match.")
        else:
            print("Incorrect current PIN.")

class PrintReceipt(ATMOperation):
    def execute(self):
        print("Printing receipt...")
        print("Date: 23/08/2024")
        print("Transaction details: ...")
        print("Thank you for using our ATM!")

# Composite class
class ATMComposite(ATMOperation):
    def __init__(self):
        self.operations = []

    def add_operation(self, operation):
        self.operations.append(operation)

    def remove_operation(self, operation):
        self.operations.remove(operation)

    def execute(self):
        for operation in self.operations:
            operation.execute()

# Client code
def main():
    # Example usage of the ATM system
    balance = 5000.0  # Initial balance
    current_pin = "1234"  # Initial PIN

    atm = ATMComposite()

    auth = Authentication("card123", current_pin)
    if auth.execute():  # Proceed only if authentication is successful
        # Adding operations based on user choices
        atm.add_operation(BalanceInquiry(balance))
        atm.add_operation(Withdrawal(balance))
        atm.add_operation(Deposit(balance))
        atm.add_operation(PinChange(current_pin))
        atm.add_operation(PrintReceipt())

        atm.execute()

if __name__ == "__main__":
    main()
