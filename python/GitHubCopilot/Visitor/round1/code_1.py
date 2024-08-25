class ATMOperation:
    def accept(self, visitor):
        pass

class AuthenticateUser(ATMOperation):
    def __init__(self, card_number, pin):
        self.card_number = card_number
        self.pin = pin

    def accept(self, visitor):
        visitor.visit_authenticate_user(self)

class CheckBalance(ATMOperation):
    def accept(self, visitor):
        visitor.visit_check_balance(self)

class WithdrawMoney(ATMOperation):
    def __init__(self, amount):
        self.amount = amount

    def accept(self, visitor):
        visitor.visit_withdraw_money(self)

class DepositMoney(ATMOperation):
    def __init__(self, amount):
        self.amount = amount

    def accept(self, visitor):
        visitor.visit_deposit_money(self)

class ChangePin(ATMOperation):
    def __init__(self, old_pin, new_pin):
        self.old_pin = old_pin
        self.new_pin = new_pin

    def accept(self, visitor):
        visitor.visit_change_pin(self)

class PrintReceipt(ATMOperation):
    def accept(self, visitor):
        visitor.visit_print_receipt(self)
        
class ATMVisitor:
    def visit_authenticate_user(self, operation):
        pass

    def visit_check_balance(self, operation):
        pass

    def visit_withdraw_money(self, operation):
        pass

    def visit_deposit_money(self, operation):
        pass

    def visit_change_pin(self, operation):
        pass

    def visit_print_receipt(self, operation):
        pass
    
class ATM:
    def __init__(self, balance, card_number, pin):
        self.balance = balance
        self.card_number = card_number
        self.pin = pin
        self.authenticated = False
        self.failed_attempts = 0

    def authenticate(self, card_number, pin):
        if self.card_number == card_number and self.pin == pin:
            self.authenticated = True
            self.failed_attempts = 0
            print("Authentication successful.")
            return True
        else:
            self.failed_attempts += 1
            if self.failed_attempts >= 3:
                print("Card retained due to too many failed attempts.")
                # Logic to retain the card
            else:
                print("Authentication failed.")
            return False

    def check_balance(self):
        if self.authenticated:
            print(f"Current balance: {self.balance}")
            return self.balance
        else:
            print("User not authenticated.")
            return -1

    def withdraw(self, amount):
        if self.authenticated:
            if self.balance >= amount:
                self.balance -= amount
                print(f"Withdrawal successful. Amount: {amount}")
                return True
            else:
                print("Insufficient funds.")
                return False
        else:
            print("User not authenticated.")
            return False

    def deposit(self, amount):
        if self.authenticated:
            self.balance += amount
            print(f"Deposit successful. Amount: {amount}")
        else:
            print("User not authenticated.")

    def change_pin(self, old_pin, new_pin):
        if self.authenticated:
            if self.pin == old_pin:
                self.pin = new_pin
                print("PIN changed successfully.")
                return True
            else:
                print("Old PIN is incorrect.")
                return False
        else:
            print("User not authenticated.")
            return False

    def print_receipt(self):
        if self.authenticated:
            print("Printing receipt...")
            # Print receipt details
        else:
            print("User not authenticated.")

class ATMVisitorImpl(ATMVisitor):
    def __init__(self, atm):
        self.atm = atm

    def visit_authenticate_user(self, operation):
        self.atm.authenticate(operation.card_number, operation.pin)

    def visit_check_balance(self, operation):
        self.atm.check_balance()

    def visit_withdraw_money(self, operation):
        self.atm.withdraw(operation.amount)

    def visit_deposit_money(self, operation):
        self.atm.deposit(operation.amount)

    def visit_change_pin(self, operation):
        self.atm.change_pin(operation.old_pin, operation.new_pin)

    def visit_print_receipt(self, operation):
        self.atm.print_receipt()
        
if __name__ == "__main__":
    atm = ATM(1000, "1234567890", "1234")
    visitor = ATMVisitorImpl(atm)

    operations = [
        AuthenticateUser("1234567890", "1234"),
        CheckBalance(),
        WithdrawMoney(200),
        DepositMoney(500),
        ChangePin("1234", "5678"),
        PrintReceipt()
    ]

    for operation in operations:
        operation.accept(visitor)