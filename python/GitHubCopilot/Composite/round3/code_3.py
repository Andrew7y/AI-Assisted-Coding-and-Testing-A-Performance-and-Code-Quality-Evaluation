from abc import ABC, abstractmethod

class ATMOperation(ABC):
    @abstractmethod
    def execute(self):
        pass

class Account:
    def __init__(self, account_number, balance, pin):
        self.account_number = account_number
        self.balance = balance
        self.pin = pin
        self.attempts = 0
        self.max_attempts = 3

    def verify_pin(self, pin):
        return self.pin == pin

    def get_balance(self):
        return self.balance

    def withdraw(self, amount):
        if self.balance >= amount:
            self.balance -= amount
            return True
        return False

    def deposit(self, amount):
        self.balance += amount

    def change_pin(self, old_pin, new_pin):
        if self.verify_pin(old_pin):
            self.pin = new_pin
            return True
        return False

class Authentication(ATMOperation):
    def __init__(self, account, pin):
        self.account = account
        self.pin = pin
        self.is_authenticated = False

    def execute(self):
        if self.account.verify_pin(self.pin):
            self.is_authenticated = True
            self.account.attempts = 0
        else:
            self.account.attempts += 1
            if self.account.attempts >= self.account.max_attempts:
                print("Card retained due to too many incorrect attempts.")
            self.is_authenticated = False

class BalanceInquiry(ATMOperation):
    def __init__(self, account):
        self.account = account

    def execute(self):
        print(f"Current balance: {self.account.get_balance()}")

class Withdrawal(ATMOperation):
    def __init__(self, account, amount):
        self.account = account
        self.amount = amount

    def execute(self):
        if self.account.withdraw(self.amount):
            print(f"Withdrew {self.amount}. New balance: {self.account.get_balance()}")
        else:
            print("Insufficient funds.")

class Deposit(ATMOperation):
    def __init__(self, account, amount):
        self.account = account
        self.amount = amount

    def execute(self):
        self.account.deposit(self.amount)
        print(f"Deposited {self.amount}. New balance: {self.account.get_balance()}")

class PinChange(ATMOperation):
    def __init__(self, account, old_pin, new_pin):
        self.account = account
        self.old_pin = old_pin
        self.new_pin = new_pin

    def execute(self):
        if self.account.change_pin(self.old_pin, self.new_pin):
            print("PIN changed successfully.")
        else:
            print("Incorrect old PIN.")

class PrintReceipt(ATMOperation):
    def __init__(self, transaction_details):
        self.transaction_details = transaction_details

    def execute(self):
        print(f"Receipt: {self.transaction_details}")

class ATMComposite(ATMOperation):
    def __init__(self):
        self.operations = []

    def add_operation(self, operation):
        self.operations.append(operation)

    def execute(self):
        for operation in self.operations:
            operation.execute()

# Example usage
if __name__ == "__main__":
    account = Account(account_number="1234567890", balance=1000, pin="1234")

    auth = Authentication(account, pin="1234")
    balance_inquiry = BalanceInquiry(account)
    withdrawal = Withdrawal(account, 200)
    deposit = Deposit(account, 500)
    pin_change = PinChange(account, old_pin="1234", new_pin="5678")
    print_receipt = PrintReceipt(transaction_details="Transaction details here")

    atm_composite = ATMComposite()
    atm_composite.add_operation(auth)
    atm_composite.add_operation(balance_inquiry)
    atm_composite.add_operation(withdrawal)
    atm_composite.add_operation(deposit)
    atm_composite.add_operation(pin_change)
    atm_composite.add_operation(print_receipt)

    atm_composite.execute()