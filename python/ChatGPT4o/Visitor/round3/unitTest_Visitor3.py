import pytest
from unittest.mock import patch
from io import StringIO
from codeAI_Visitor3 import (
    ATMOperation,
    Authentication,
    BalanceInquiry,
    Withdrawal,
    Deposit,
    PinChange,
    PrintReceipt,
)

def test_authentication_success():
    auth = Authentication()
    atm_operation = ATMOperation()

    with patch('builtins.input', side_effect=["1234"]):
        auth.accept(atm_operation)

    assert auth.pin_attempts == 0  # Ensure no additional attempts were made

def test_authentication_failure_max_attempts():
    auth = Authentication()
    atm_operation = ATMOperation()

    with patch('builtins.input', side_effect=["1111", "1111", "1111"]):
        auth.accept(atm_operation)

    assert auth.pin_attempts == auth.max_attempts

def test_authentication_partial_failure_then_success():
    auth = Authentication()
    atm_operation = ATMOperation()

    with patch('builtins.input', side_effect=["1111", "1111", "1234"]):
        auth.accept(atm_operation)

    assert auth.pin_attempts == 2

def test_balance_inquiry():
    balance_inquiry = BalanceInquiry(500.0)
    atm_operation = ATMOperation()

    with patch('sys.stdout', new=StringIO()) as fake_out:
        balance_inquiry.accept(atm_operation)
        output = fake_out.getvalue().strip()

    assert "Your current balance is: $500.0" in output

def test_withdrawal_success():
    withdrawal = Withdrawal(500.0)
    atm_operation = ATMOperation()

    with patch('builtins.input', side_effect=["100"]):
        withdrawal.accept(atm_operation)

    assert withdrawal.balance == 400.0

def test_withdrawal_insufficient_funds():
    withdrawal = Withdrawal(500.0)
    atm_operation = ATMOperation()

    with patch('builtins.input', side_effect=["600"]):
        with patch('sys.stdout', new=StringIO()) as fake_out:
            withdrawal.accept(atm_operation)
            output = fake_out.getvalue().strip()

    assert withdrawal.balance == 500.0  # Balance should remain unchanged
    assert "Insufficient funds." in output

def test_deposit():
    deposit = Deposit(500.0)
    atm_operation = ATMOperation()

    with patch('builtins.input', side_effect=["200"]):
        deposit.accept(atm_operation)

    assert deposit.balance == 700.0

def test_pin_change_success():
    pin_change = PinChange()
    atm_operation = ATMOperation()

    with patch('builtins.input', side_effect=["1234", "5678", "5678"]):
        with patch('sys.stdout', new=StringIO()) as fake_out:
            pin_change.accept(atm_operation)
            output = fake_out.getvalue().strip()

    assert "PIN successfully changed." in output

def test_pin_change_incorrect_old_pin():
    pin_change = PinChange()
    atm_operation = ATMOperation()

    with patch('builtins.input', side_effect=["1111"]):
        with patch('sys.stdout', new=StringIO()) as fake_out:
            pin_change.accept(atm_operation)
            output = fake_out.getvalue().strip()

    assert "Incorrect old PIN." in output

def test_pin_change_mismatch_new_pin():
    pin_change = PinChange()
    atm_operation = ATMOperation()

    with patch('builtins.input', side_effect=["1234", "5678", "9999"]):
        with patch('sys.stdout', new=StringIO()) as fake_out:
            pin_change.accept(atm_operation)
            output = fake_out.getvalue().strip()

    assert "PIN confirmation does not match." in output

def test_print_receipt():
    print_receipt = PrintReceipt()
    atm_operation = ATMOperation()

    with patch('sys.stdout', new=StringIO()) as fake_out:
        print_receipt.accept(atm_operation)
        output = fake_out.getvalue().strip()

    assert "Printing receipt..." in output
    assert "Date: 24/08/2024" in output
    assert "Transaction: Withdrawal" in output
    assert "Balance: $450" in output

if __name__ == "__main__":
    pytest.main()
