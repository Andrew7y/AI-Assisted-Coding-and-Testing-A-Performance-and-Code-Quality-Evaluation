from abc import ABC, abstractmethod

# Component interface
class ATMOperation(ABC):
    @abstractmethod
    def execute(self):
        pass  # pragma: no cover

# Leaf classes
class Authentication(ATMOperation):
    def __init__(self, card_number, pin, max_attempts=3):
        self.card_number = card_number
        self.pin = pin
        self.attempts = 0
        self.max_attempts = max_attempts
        self.is_authenticated = False

    def execute(self):
        # Simulate authentication logic
        print(f"Authenticating card {self.card_number} with PIN {self.pin}")
        # Add logic to check card and PIN, and handle attempts
        if self.attempts < self.max_attempts:
            # Simulate a successful authentication
            self.is_authenticated = True
            print("Authentication successful")
        else:
            print("Card retained due to too many failed attempts")

class BalanceInquiry(ATMOperation):
    def __init__(self, account):
        self.account = account

    def execute(self):
        # Simulate balance inquiry logic
        print(f"Current balance for account {self.account.account_number}: {self.account.get_balance()}")

class Withdrawal(ATMOperation):
    def __init__(self, account, amount):
        self.account = account
        self.amount = amount

    def execute(self):
        # Simulate withdrawal logic
        if self.account.get_balance() >= self.amount:
            self.account.withdraw(self.amount)
            print(f"Withdrew {self.amount}. New balance: {self.account.get_balance()}")
        else:
            print("Insufficient funds")

class Deposit(ATMOperation):
    def __init__(self, account, amount):
        self.account = account
        self.amount = amount

    def execute(self):
        # Simulate deposit logic
        self.account.deposit(self.amount)
        print(f"Deposited {self.amount}. New balance: {self.account.get_balance()}")

class PinChange(ATMOperation):
    def __init__(self, account, old_pin, new_pin):
        self.account = account
        self.old_pin = old_pin
        self.new_pin = new_pin

    def execute(self):
        # Simulate PIN change logic
        if self.account.verify_pin(self.old_pin):
            self.account.change_pin(self.new_pin)
            print("PIN changed successfully")
        else:
            print("Old PIN is incorrect")

class PrintReceipt(ATMOperation):
    def __init__(self, transaction_details):
        self.transaction_details = transaction_details

    def execute(self):
        # Simulate printing receipt logic
        print("Printing receipt with details:")
        print(self.transaction_details)

# Composite class
class ATMComposite(ATMOperation):
    def __init__(self):
        self.operations = []

    def add_operation(self, operation):
        self.operations.append(operation)

    def execute(self):
        for operation in self.operations:
            operation.execute()

# Example usage
class Account:
    def __init__(self, account_number, balance, pin):
        self.account_number = account_number
        self.balance = balance
        self.pin = pin

    def get_balance(self):
        return self.balance

    def withdraw(self, amount):
        self.balance -= amount

    def deposit(self, amount):
        self.balance += amount

    def verify_pin(self, pin):
        return self.pin == pin

    def change_pin(self, new_pin):
        self.pin = new_pin

# Initialize account and operations
account = Account(account_number="1234567890", balance=1000, pin="1234")
auth = Authentication(card_number="1234567890", pin="1234")
balance_inquiry = BalanceInquiry(account)
withdrawal = Withdrawal(account, 200)
deposit = Deposit(account, 500)
pin_change = PinChange(account, old_pin="1234", new_pin="5678")
print_receipt = PrintReceipt(transaction_details="Transaction details here")

# Create composite and add operations
atm_composite = ATMComposite()
atm_composite.add_operation(auth)
atm_composite.add_operation(balance_inquiry)
atm_composite.add_operation(withdrawal)
atm_composite.add_operation(deposit)
atm_composite.add_operation(pin_change)
atm_composite.add_operation(print_receipt)

# Execute all operations
atm_composite.execute()