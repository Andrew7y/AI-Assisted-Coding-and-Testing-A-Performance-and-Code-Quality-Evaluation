from abc import ABC, abstractmethod

# Element Interface
class ATMOperation(ABC):
    @abstractmethod
    def accept(self, visitor):
        pass

# Concrete Elements representing different ATM operations
class Authentication(ATMOperation):
    def __init__(self, card_number, pin):
        self.card_number = card_number
        self.pin = pin
        self.attempts = 0
        self.max_attempts = 3
        self.is_locked = False

    def accept(self, visitor):
        visitor.visit_authentication(self)

class CheckBalance(ATMOperation):
    def __init__(self, balance):
        self.balance = balance

    def accept(self, visitor):
        visitor.visit_check_balance(self)

class WithdrawMoney(ATMOperation):
    def __init__(self, balance, withdraw_amount):
        self.balance = balance
        self.withdraw_amount = withdraw_amount

    def accept(self, visitor):
        visitor.visit_withdraw_money(self)

class DepositMoney(ATMOperation):
    def __init__(self, balance, deposit_amount):
        self.balance = balance
        self.deposit_amount = deposit_amount

    def accept(self, visitor):
        visitor.visit_deposit_money(self)

class ChangePin(ATMOperation):
    def __init__(self, old_pin, new_pin):
        self.old_pin = old_pin
        self.new_pin = new_pin

    def accept(self, visitor):
        visitor.visit_change_pin(self)

class PrintReceipt(ATMOperation):
    def __init__(self, transaction_details):
        self.transaction_details = transaction_details

    def accept(self, visitor):
        visitor.visit_print_receipt(self)

# Visitor Interface
class ATMVisitor(ABC):
    @abstractmethod
    def visit_authentication(self, authentication):
        pass

    @abstractmethod
    def visit_check_balance(self, check_balance):
        pass

    @abstractmethod
    def visit_withdraw_money(self, withdraw_money):
        pass

    @abstractmethod
    def visit_deposit_money(self, deposit_money):
        pass

    @abstractmethod
    def visit_change_pin(self, change_pin):
        pass

    @abstractmethod
    def visit_print_receipt(self, print_receipt):
        pass

# Concrete Visitor implementing the behavior for each operation
class ATMVisitorImplementation(ATMVisitor):
    def visit_authentication(self, authentication):
        if authentication.is_locked:
            print("Your card is locked due to too many failed attempts.")
            return False

        # Simulate user input for PIN
        user_pin = input("Enter PIN: ")
        if user_pin == authentication.pin:
            print("Authentication successful.")
            return True
        else:
            authentication.attempts += 1
            if authentication.attempts >= authentication.max_attempts:
                authentication.is_locked = True
                print("Your card has been locked due to too many failed attempts.")
            else:
                print(f"Incorrect PIN. Attempt {authentication.attempts} of {authentication.max_attempts}.")
            return False

    def visit_check_balance(self, check_balance):
        print(f"Your current balance is: ${check_balance.balance}")

    def visit_withdraw_money(self, withdraw_money):
        if withdraw_money.withdraw_amount > withdraw_money.balance:
            print("Insufficient funds. Unable to complete the transaction.")
        else:
            withdraw_money.balance -= withdraw_money.withdraw_amount
            print(f"Withdrawal successful. You withdrew ${withdraw_money.withdraw_amount}.")
            print(f"New balance: ${withdraw_money.balance}")

    def visit_deposit_money(self, deposit_money):
        deposit_money.balance += deposit_money.deposit_amount
        print(f"Deposit successful. You deposited ${deposit_money.deposit_amount}.")
        print(f"New balance: ${deposit_money.balance}")

    def visit_change_pin(self, change_pin):
        old_pin_input = input("Enter your old PIN: ")
        if old_pin_input == change_pin.old_pin:
            new_pin_input = input("Enter your new PIN: ")
            confirm_new_pin_input = input("Confirm your new PIN: ")
            if new_pin_input == confirm_new_pin_input:
                change_pin.old_pin = new_pin_input
                print("PIN changed successfully.")
            else:
                print("New PINs do not match. Try again.")
        else:
            print("Old PIN is incorrect. Cannot change PIN.")

    def visit_print_receipt(self, print_receipt):
        print("Printing receipt...")
        print(f"Receipt Details: {print_receipt.transaction_details}")

# Usage Example
if __name__ == "__main__":
    # Create different ATM operations
    authentication = Authentication("1234567890", "1234")
    check_balance = CheckBalance(5000)
    withdraw_money = WithdrawMoney(5000, 300)
    deposit_money = DepositMoney(5000, 200)
    change_pin = ChangePin("1234", "5678")
    print_receipt = PrintReceipt("Withdrawal: $300 | Remaining Balance: $4700")

    # Visitor instance
    atm_visitor = ATMVisitorImplementation()

    # Perform operations
    authentication.accept(atm_visitor)
    check_balance.accept(atm_visitor)
    withdraw_money.accept(atm_visitor)
    deposit_money.accept(atm_visitor)
    change_pin.accept(atm_visitor)
    print_receipt.accept(atm_visitor)
