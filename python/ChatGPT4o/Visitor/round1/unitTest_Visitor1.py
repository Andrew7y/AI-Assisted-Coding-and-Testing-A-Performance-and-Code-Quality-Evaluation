import pytest
from io import StringIO
from contextlib import redirect_stdout

from codeAI_Visitor1 import (
    ATM, Account, AuthenticationVisitor, BalanceInquiryVisitor,
    WithdrawalVisitor, DepositVisitor, PinChangeVisitor, PrintReceiptVisitor
)

@pytest.fixture
def setup_atm():
    account = Account(pin="1234", balance=5000)
    atm = ATM()
    atm.insert_card(account)
    return atm, account

def test_authentication_success(setup_atm):
    atm, account = setup_atm
    auth_visitor = AuthenticationVisitor(pin="1234")
    with redirect_stdout(StringIO()) as f:
        atm.accept(auth_visitor)
    output = f.getvalue().strip()
    assert output == "Authentication successful."
    assert account.attempts == 0

def test_authentication_incorrect_pin(setup_atm):
    atm, account = setup_atm
    auth_visitor = AuthenticationVisitor(pin="1111")
    with redirect_stdout(StringIO()) as f:
        atm.accept(auth_visitor)
    output = f.getvalue().strip()
    assert output == "Incorrect PIN. Try again."
    assert account.attempts == 1

def test_authentication_card_seized(setup_atm):
    atm, account = setup_atm
    auth_visitor = AuthenticationVisitor(pin="1111")
    for _ in range(3):  # 3 incorrect attempts
        atm.accept(auth_visitor)
    with redirect_stdout(StringIO()) as f:
        atm.accept(auth_visitor)
    output = f.getvalue().strip()
    assert output == "Card is seized due to too many incorrect attempts."
    assert account.attempts == 3

def test_balance_inquiry(setup_atm):
    atm, account = setup_atm
    balance_visitor = BalanceInquiryVisitor()
    with redirect_stdout(StringIO()) as f:
        atm.accept(balance_visitor)
    output = f.getvalue().strip()
    assert output == f"Your current balance is: {account.balance}."

def test_withdrawal_success(setup_atm):
    atm, account = setup_atm
    withdrawal_visitor = WithdrawalVisitor(amount=1500)
    with redirect_stdout(StringIO()) as f:
        atm.accept(withdrawal_visitor)
    output = f.getvalue().strip()
    assert output == f"Withdrawal successful. Dispensed 1500.\nNew balance: 3500."
    assert account.balance == 3500

def test_withdrawal_insufficient_funds(setup_atm):
    atm, account = setup_atm
    withdrawal_visitor = WithdrawalVisitor(amount=6000)
    with redirect_stdout(StringIO()) as f:
        atm.accept(withdrawal_visitor)
    output = f.getvalue().strip()
    assert output == "Insufficient funds for withdrawal."
    assert account.balance == 5000

def test_deposit_success(setup_atm):
    atm, account = setup_atm
    deposit_visitor = DepositVisitor(amount=2000)
    with redirect_stdout(StringIO()) as f:
        atm.accept(deposit_visitor)
    output = f.getvalue().strip()
    assert output == "Deposit successful. New balance: 7000."
    assert account.balance == 7000

def test_pin_change_success(setup_atm):
    atm, account = setup_atm
    pin_change_visitor = PinChangeVisitor(old_pin="1234", new_pin="5678", confirm_new_pin="5678")
    with redirect_stdout(StringIO()) as f:
        atm.accept(pin_change_visitor)
    output = f.getvalue().strip()
    assert output == "PIN successfully changed."
    assert account.pin == "5678"

def test_pin_change_incorrect_old_pin(setup_atm):
    atm, account = setup_atm
    pin_change_visitor = PinChangeVisitor(old_pin="0000", new_pin="5678", confirm_new_pin="5678")
    with redirect_stdout(StringIO()) as f:
        atm.accept(pin_change_visitor)
    output = f.getvalue().strip()
    assert output == "Old PIN is incorrect. Try again."
    assert account.pin == "1234"

def test_pin_change_mismatch_new_pins(setup_atm):
    atm, account = setup_atm
    pin_change_visitor = PinChangeVisitor(old_pin="1234", new_pin="5678", confirm_new_pin="9999")
    with redirect_stdout(StringIO()) as f:
        atm.accept(pin_change_visitor)
    output = f.getvalue().strip()
    assert output == "New PINs do not match. Try again."
    assert account.pin == "1234"

def test_print_receipt(setup_atm):
    atm, account = setup_atm
    print_receipt_visitor = PrintReceiptVisitor()
    with redirect_stdout(StringIO()) as f:
        atm.accept(print_receipt_visitor)
    output = f.getvalue().strip()
    assert "Receipt: Showing transaction details." in output

def test_print_transaction(setup_atm):
    atm, account = setup_atm
    transaction = Transaction(transaction_type="Withdrawal", amount=1500)
    with redirect_stdout(StringIO()) as f:
        transaction.accept(PrintReceiptVisitor())
    output = f.getvalue().strip()
    assert output == "Transaction: Withdrawal, Amount: 1500"

