import pytest
from code_1 import ATM, ATMVisitorImpl, AuthenticateUser, CheckBalance, WithdrawMoney, DepositMoney, ChangePin, PrintReceipt

@pytest.fixture
def atm():
    return ATM(1000, "1234567890", "1234")

@pytest.fixture
def visitor(atm):
    return ATMVisitorImpl(atm)

def test_authenticate_user(visitor):
    operation = AuthenticateUser("1234567890", "1234")
    operation.accept(visitor)
    assert visitor.atm.authenticated

    operation = AuthenticateUser("1234567890", "0000")
    operation.accept(visitor)
    assert not visitor.atm.authenticated

def test_check_balance(visitor):
    visitor.atm.authenticate("1234567890", "1234")
    operation = CheckBalance()
    operation.accept(visitor)
    assert visitor.atm.check_balance() == 1000

def test_withdraw_money(visitor):
    visitor.atm.authenticate("1234567890", "1234")
    operation = WithdrawMoney(200)
    operation.accept(visitor)
    assert visitor.atm.balance == 800

    operation = WithdrawMoney(2000)
    operation.accept(visitor)
    assert visitor.atm.balance == 800

def test_deposit_money(visitor):
    visitor.atm.authenticate("1234567890", "1234")
    operation = DepositMoney(500)
    operation.accept(visitor)
    assert visitor.atm.balance == 1500

def test_change_pin(visitor):
    visitor.atm.authenticate("1234567890", "1234")
    operation = ChangePin("1234", "5678")
    operation.accept(visitor)
    assert visitor.atm.pin == "5678"

    operation = ChangePin("1234", "0000")
    operation.accept(visitor)
    assert visitor.atm.pin == "5678"

def test_print_receipt(visitor):
    visitor.atm.authenticate("1234567890", "1234")
    operation = PrintReceipt()
    operation.accept(visitor)
    # Assuming print_receipt prints to console, no assertion needed