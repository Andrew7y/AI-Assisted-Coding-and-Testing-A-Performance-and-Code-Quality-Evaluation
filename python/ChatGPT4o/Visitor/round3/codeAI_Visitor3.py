from abc import ABC, abstractmethod

# Visitor Interface
class ATMVisitor(ABC):
    @abstractmethod
    def visit_authentication(self, auth):
        pass  # pragma: no cover

    @abstractmethod
    def visit_balance_inquiry(self, balance_inquiry):
        pass # pragma: no cover

    @abstractmethod
    def visit_withdrawal(self, withdrawal):
        pass  # pragma: no cover

    @abstractmethod
    def visit_deposit(self, deposit):
        pass  # pragma: no cover

    @abstractmethod
    def visit_pin_change(self, pin_change):
        pass  # pragma: no cover

    @abstractmethod
    def visit_print_receipt(self, print_receipt):
        pass  # pragma: no cover

# Concrete Visitor
class ATMOperation(ATMVisitor):
    def visit_authentication(self, auth):
        auth.authenticate()

    def visit_balance_inquiry(self, balance_inquiry):
        balance_inquiry.display_balance()

    def visit_withdrawal(self, withdrawal):
        withdrawal.process_withdrawal()

    def visit_deposit(self, deposit):
        deposit.process_deposit()

    def visit_pin_change(self, pin_change):
        pin_change.change_pin()

    def visit_print_receipt(self, print_receipt):
        print_receipt.print_receipt()

# Element Interface
class ATMFeature(ABC):
    @abstractmethod
    def accept(self, visitor: ATMVisitor):
        pass  # pragma: no cover

# Concrete Elements
class Authentication(ATMFeature):
    def __init__(self):
        self.pin_attempts = 0
        self.max_attempts = 3

    def authenticate(self):
        print("Please insert your ATM card and enter your PIN.")
        while self.pin_attempts < self.max_attempts:
            pin = input("Enter PIN: ")
            if self.verify_pin(pin):
                print("Authentication successful.")
                break
            else:
                self.pin_attempts += 1
                print(f"Incorrect PIN. Attempts remaining: {self.max_attempts - self.pin_attempts}")
        if self.pin_attempts >= self.max_attempts:
            print("Card retained due to multiple failed attempts.")

    def verify_pin(self, pin):
        # Mock verification, assuming correct PIN is "1234"
        return pin == "1234"

    def accept(self, visitor: ATMVisitor):
        visitor.visit_authentication(self)

class BalanceInquiry(ATMFeature):
    def __init__(self, balance):
        self.balance = balance

    def display_balance(self):
        print(f"Your current balance is: ${self.balance}")

    def accept(self, visitor: ATMVisitor):
        visitor.visit_balance_inquiry(self)

class Withdrawal(ATMFeature):
    def __init__(self, balance):
        self.balance = balance

    def process_withdrawal(self):
        amount = float(input("Enter the amount to withdraw: "))
        if amount <= self.balance:
            self.balance -= amount
            print(f"Please take your cash. Your new balance is: ${self.balance}")
        else:
            print("Insufficient funds.")

    def accept(self, visitor: ATMVisitor):
        visitor.visit_withdrawal(self)

class Deposit(ATMFeature):
    def __init__(self, balance):
        self.balance = balance

    def process_deposit(self):
        amount = float(input("Enter the amount to deposit: "))
        self.balance += amount
        print(f"Deposit successful. Your new balance is: ${self.balance}")

    def accept(self, visitor: ATMVisitor):
        visitor.visit_deposit(self)

class PinChange(ATMFeature):
    def change_pin(self):
        old_pin = input("Enter your old PIN: ")
        if old_pin == "1234":  # Assume "1234" is the correct old PIN
            new_pin = input("Enter your new PIN: ")
            confirm_pin = input("Confirm your new PIN: ")
            if new_pin == confirm_pin:
                print("PIN successfully changed.")
            else:
                print("PIN confirmation does not match.")
        else:
            print("Incorrect old PIN.")

    def accept(self, visitor: ATMVisitor):
        visitor.visit_pin_change(self)

class PrintReceipt(ATMFeature):
    def print_receipt(self):
        print("Printing receipt...")
        print("Date: 24/08/2024\nTime: 12:34 PM\nTransaction: Withdrawal\nAmount: $50\nBalance: $450")

    def accept(self, visitor: ATMVisitor):
        visitor.visit_print_receipt(self)

# # Client code
# def atm_system():
#     balance = 500.0  # Initial balance
#     atm_operation = ATMOperation()
#
#     # ATM features
#     auth = Authentication()
#     balance_inquiry = BalanceInquiry(balance)
#     withdrawal = Withdrawal(balance)
#     deposit = Deposit(balance)
#     pin_change = PinChange()
#     print_receipt = PrintReceipt()
#
#     # Simulate ATM operations
#     auth.accept(atm_operation)
#     if auth.pin_attempts < auth.max_attempts:
#         balance_inquiry.accept(atm_operation)
#         withdrawal.accept(atm_operation)
#         deposit.accept(atm_operation)
#         pin_change.accept(atm_operation)
#         print_receipt.accept(atm_operation)
#
# if __name__ == "__main__":
#     atm_system()
