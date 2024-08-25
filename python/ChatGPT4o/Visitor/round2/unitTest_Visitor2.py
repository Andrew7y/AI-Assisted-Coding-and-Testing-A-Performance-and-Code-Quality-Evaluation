import pytest
from io import StringIO
from unittest.mock import patch

from codeAI_Visitor2 import (
    Authentication, CheckBalance, WithdrawMoney, DepositMoney,
    ChangePin, PrintReceipt, ATMVisitorImplementation
)

# Test Authentication
@pytest.mark.parametrize("input_pin, expected_output, attempts, is_locked", [
    ("1234", "Authentication successful.", 0, False),
    ("0000", "Incorrect PIN. Attempt 1 of 3.", 1, False),
    ("0000", "Incorrect PIN. Attempt 2 of 3.", 2, False),
    ("0000", "Your card has been locked due to too many failed attempts.", 3, True),
])
def test_authentication(input_pin, expected_output, attempts, is_locked):
    auth = Authentication("1234567890", "1234")
    visitor = ATMVisitorImplementation()
    auth.attempts = attempts
    auth.is_locked = is_locked

    with patch('builtins.input', return_value=input_pin), patch('sys.stdout', new=StringIO()) as output:
        result = auth.accept(visitor)
        assert expected_output in output.getvalue()
        if input_pin == "1234" and not is_locked:
            assert result is True
        else:
            assert result is False

# Test Check Balance
def test_check_balance():
    check_balance = CheckBalance(5000)
    visitor = ATMVisitorImplementation()

    with patch('sys.stdout', new=StringIO()) as output:
        check_balance.accept(visitor)
        assert "Your current balance is: $5000" in output.getvalue()

# Test Withdraw Money - Success
def test_withdraw_money_success():
    withdraw_money = WithdrawMoney(5000, 300)
    visitor = ATMVisitorImplementation()

    with patch('sys.stdout', new=StringIO()) as output:
        withdraw_money.accept(visitor)
        assert "Withdrawal successful. You withdrew $300." in output.getvalue()
        assert "New balance: $4700" in output.getvalue()
        assert withdraw_money.balance == 4700

# Test Withdraw Money - Insufficient Funds
def test_withdraw_money_insufficient_funds():
    withdraw_money = WithdrawMoney(5000, 6000)
    visitor = ATMVisitorImplementation()

    with patch('sys.stdout', new=StringIO()) as output:
        withdraw_money.accept(visitor)
        assert "Insufficient funds. Unable to complete the transaction." in output.getvalue()
        assert withdraw_money.balance == 5000  # Balance should remain unchanged

# Test Deposit Money
def test_deposit_money():
    deposit_money = DepositMoney(5000, 200)
    visitor = ATMVisitorImplementation()

    with patch('sys.stdout', new=StringIO()) as output:
        deposit_money.accept(visitor)
        assert "Deposit successful. You deposited $200." in output.getvalue()
        assert "New balance: $5200" in output.getvalue()
        assert deposit_money.balance == 5200

# Test Change PIN - Successful Change
def test_change_pin_success():
    change_pin = ChangePin("1234", "5678")
    visitor = ATMVisitorImplementation()

    with patch('builtins.input', side_effect=["1234", "5678", "5678"]), patch('sys.stdout', new=StringIO()) as output:
        change_pin.accept(visitor)
        assert "PIN changed successfully." in output.getvalue()
        assert change_pin.old_pin == "5678"

# Test Change PIN - Incorrect Old PIN
def test_change_pin_incorrect_old():
    change_pin = ChangePin("1234", "5678")
    visitor = ATMVisitorImplementation()

    with patch('builtins.input', side_effect=["0000", "5678", "5678"]), patch('sys.stdout', new=StringIO()) as output:
        change_pin.accept(visitor)
        assert "Old PIN is incorrect. Cannot change PIN." in output.getvalue()
        assert change_pin.old_pin == "1234"  # Old PIN should remain unchanged

# Test Change PIN - New PIN Mismatch
def test_change_pin_mismatch():
    change_pin = ChangePin("1234", "5678")
    visitor = ATMVisitorImplementation()

    with patch('builtins.input', side_effect=["1234", "5678", "0000"]), patch('sys.stdout', new=StringIO()) as output:
        change_pin.accept(visitor)
        assert "New PINs do not match. Try again." in output.getvalue()
        assert change_pin.old_pin == "1234"  # Old PIN should remain unchanged

# Test Print Receipt
def test_print_receipt():
    transaction_details = "Withdrawal: $300 | Remaining Balance: $4700"
    print_receipt = PrintReceipt(transaction_details)
    visitor = ATMVisitorImplementation()

    with patch('sys.stdout', new=StringIO()) as output:
        print_receipt.accept(visitor)
        assert "Printing receipt..." in output.getvalue()
        assert f"Receipt Details: {transaction_details}" in output.getvalue()

