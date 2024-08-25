from abc import ABC, abstractmethod

# Visitor Interface
class Visitor(ABC):
    @abstractmethod
    def visit_atm(self, atm):
        pass

    @abstractmethod
    def visit_account(self, account):
        pass

    @abstractmethod
    def visit_transaction(self, transaction):
        pass

# Elements (Visitable Classes)
class ATM:
    def __init__(self):
        self.account = None

    def insert_card(self, account):
        print("Card inserted.")
        self.account = account

    def accept(self, visitor):
        visitor.visit_atm(self)

class Account:
    def __init__(self, pin, balance):
        self.pin = pin
        self.balance = balance
        self.attempts = 0

    def accept(self, visitor):
        visitor.visit_account(self)

class Transaction:
    def __init__(self, transaction_type, amount=0):
        self.transaction_type = transaction_type
        self.amount = amount

    def accept(self, visitor):
        visitor.visit_transaction(self)

# Concrete Visitors for each operation
class AuthenticationVisitor(Visitor):
    def __init__(self, pin):
        self.input_pin = pin

    def visit_atm(self, atm):
        if atm.account:
            atm.account.accept(self)

    def visit_account(self, account):
        if account.pin == self.input_pin:
            account.attempts = 0
            print("Authentication successful.")
        else:
            account.attempts += 1
            if account.attempts >= 3:
                print("Card is seized due to too many incorrect attempts.")
            else:
                print("Incorrect PIN. Try again.")

    def visit_transaction(self, transaction):
        pass

class BalanceInquiryVisitor(Visitor):
    def visit_atm(self, atm):
        if atm.account:
            atm.account.accept(self)

    def visit_account(self, account):
        print(f"Your current balance is: {account.balance}.")

    def visit_transaction(self, transaction):
        pass

class WithdrawalVisitor(Visitor):
    def __init__(self, amount):
        self.amount = amount

    def visit_atm(self, atm):
        if atm.account:
            atm.account.accept(self)

    def visit_account(self, account):
        if account.balance >= self.amount:
            account.balance -= self.amount
            print(f"Withdrawal successful. Dispensed {self.amount}.")
            print(f"New balance: {account.balance}.")
        else:
            print("Insufficient funds for withdrawal.")

    def visit_transaction(self, transaction):
        pass

class DepositVisitor(Visitor):
    def __init__(self, amount):
        self.amount = amount

    def visit_atm(self, atm):
        if atm.account:
            atm.account.accept(self)

    def visit_account(self, account):
        account.balance += self.amount
        print(f"Deposit successful. New balance: {account.balance}.")

    def visit_transaction(self, transaction):
        pass

class PinChangeVisitor(Visitor):
    def __init__(self, old_pin, new_pin, confirm_new_pin):
        self.old_pin = old_pin
        self.new_pin = new_pin
        self.confirm_new_pin = confirm_new_pin

    def visit_atm(self, atm):
        if atm.account:
            atm.account.accept(self)

    def visit_account(self, account):
        if account.pin == self.old_pin:
            if self.new_pin == self.confirm_new_pin:
                account.pin = self.new_pin
                print("PIN successfully changed.")
            else:
                print("New PINs do not match. Try again.")
        else:
            print("Old PIN is incorrect. Try again.")

    def visit_transaction(self, transaction):
        pass

class PrintReceiptVisitor(Visitor):
    def visit_atm(self, atm):
        if atm.account:
            atm.account.accept(self)

    def visit_account(self, account):
        print("Receipt: Showing transaction details.")
        # Add detailed information such as date, time, transaction type, etc.

    def visit_transaction(self, transaction):
        print(f"Transaction: {transaction.transaction_type}, Amount: {transaction.amount}")

# Example usage
if __name__ == "__main__":
    # Setup
    account = Account(pin="1234", balance=5000)
    atm = ATM()

    # Insert card and authenticate
    atm.insert_card(account)
    auth_visitor = AuthenticationVisitor(pin="1234")
    atm.accept(auth_visitor)

    # Balance inquiry
    balance_visitor = BalanceInquiryVisitor()
    atm.accept(balance_visitor)

    # Withdraw money
    withdrawal_visitor = WithdrawalVisitor(amount=1500)
    atm.accept(withdrawal_visitor)

    # Deposit money
    deposit_visitor = DepositVisitor(amount=2000)
    atm.accept(deposit_visitor)

    # Change PIN
    pin_change_visitor = PinChangeVisitor(old_pin="1234", new_pin="5678", confirm_new_pin="5678")
    atm.accept(pin_change_visitor)

    # Print receipt
    print_receipt_visitor = PrintReceiptVisitor()
    atm.accept(print_receipt_visitor)
