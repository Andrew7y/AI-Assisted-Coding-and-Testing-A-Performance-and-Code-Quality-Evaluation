from abc import ABC, abstractmethod
from datetime import datetime

# Abstract base class for ATM components
class ATMComponent(ABC):
    @abstractmethod
    def execute(self):
        pass

# Concrete class for Authentication
class Authentication(ATMComponent):
    def __init__(self, card, pin):
        self.card = card
        self.pin = pin
        self.max_attempts = 3
        self.attempts = 0
        self.valid_card = "valid_card"
        self.valid_pin = "1234"

    def execute(self):
        if self.card != self.valid_card or self.pin != self.valid_pin:
            self.attempts += 1
            if self.attempts >= self.max_attempts:
                print("Card is locked")
                return
            raise ValueError("Invalid card or PIN")
        print("Authentication successful")

# Concrete class for Balance Inquiry
class BalanceInquiry(ATMComponent):
    def __init__(self, balance):
        self.balance = balance

    def execute(self):
        print(f"Current balance is: ${self.balance}")

# Concrete class for Withdrawal
class Withdrawal(ATMComponent):
    def __init__(self, balance, amount):
        self.balance = balance
        self.amount = amount

    def execute(self):
        if self.amount > self.balance:
            print("Insufficient funds")
        else:
            self.balance -= self.amount
            print(f"Withdrew ${self.amount}, new balance is ${self.balance}")

# Concrete class for Deposit
class Deposit(ATMComponent):
    def __init__(self, balance, amount):
        self.balance = balance
        self.amount = amount

    def execute(self):
        self.balance += self.amount
        print(f"Deposited ${self.amount}, new balance is ${self.balance}")

# Concrete class for PIN Change
class PINChange(ATMComponent):
    def __init__(self, old_pin, new_pin, confirm_pin):
        self.old_pin = old_pin
        self.new_pin = new_pin
        self.confirm_pin = confirm_pin
        self.current_pin = "1234"

    def execute(self):
        if self.old_pin != self.current_pin:
            print("Old PIN is incorrect")
            return
        if self.new_pin != self.confirm_pin:
            print("New PINs do not match")
            return
        self.current_pin = self.new_pin
        print("PIN changed successfully")

# Concrete class for Print Slip
class PrintSlip(ATMComponent):
    def __init__(self, transaction_details):
        self.transaction_details = transaction_details

    def execute(self):
        print("Printing slip...")
        print(f"Date: {self.transaction_details['date']}")
        print(f"Time: {self.transaction_details['time']}")
        print(f"Amount: ${self.transaction_details['amount']}")
        print(f"Remaining balance: ${self.transaction_details['balance']}")

# Composite class to combine different operations
class ATMComposite(ATMComponent):
    def __init__(self):
        self.components = []

    def add(self, component):
        self.components.append(component)

    def execute(self):
        for component in self.components:
            component.execute()

# Example usage
def main():
    atm_operations = ATMComposite()

    # Create and add operations
    atm_operations.add(Authentication("valid_card", "1234"))
    atm_operations.add(BalanceInquiry(1000))
    atm_operations.add(Withdrawal(1000, 200))
    atm_operations.add(Deposit(800, 500))
    atm_operations.add(PINChange("1234", "5698", "5678"))
    atm_operations.add(PrintSlip({
        'date': datetime.now().strftime('%Y-%m-%d'),
        'time': datetime.now().strftime('%H:%M:%S'),
        'amount': 500,
        'balance': 1300
    }))

    # Execute all operations
    atm_operations.execute()

if __name__ == "__main__":
    main()
