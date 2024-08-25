import pytest
from unittest.mock import patch
from io import StringIO
from codeAI_1 import Authentication, BalanceInquiry, Withdrawal, Deposit, PinChange, PrintReceipt, ATMComposite

# Sample balance and PIN for testing
SAMPLE_BALANCE = 5000.0
SAMPLE_PIN = "1234"

def test_authentication_success():
    with patch('builtins.input', side_effect=[SAMPLE_PIN]):
        auth = Authentication("card123", SAMPLE_PIN)
        assert auth.execute() == True

def test_authentication_failure():
    with patch('builtins.input', side_effect=['wrong_pin', 'wrong_pin', 'wrong_pin']):
        auth = Authentication("card123", SAMPLE_PIN)
        assert auth.execute() == False

def test_balance_inquiry():
    balance_inquiry = BalanceInquiry(SAMPLE_BALANCE)
    with patch('sys.stdout', new=StringIO()) as fake_out:
        balance_inquiry.execute()
        output = fake_out.getvalue().strip()
    assert output == f"Your current balance is: {SAMPLE_BALANCE} units."

def test_withdrawal_success():
    with patch('builtins.input', side_effect=['1000']):
        withdrawal = Withdrawal(SAMPLE_BALANCE)
        with patch('sys.stdout', new=StringIO()) as fake_out:
            withdrawal.execute()
            output = fake_out.getvalue().strip()
        assert output == f"Please take your cash. Your new balance is: {SAMPLE_BALANCE - 1000} units."

def test_withdrawal_insufficient_funds():
    with patch('builtins.input', side_effect=['6000']):
        withdrawal = Withdrawal(SAMPLE_BALANCE)
        with patch('sys.stdout', new=StringIO()) as fake_out:
            withdrawal.execute()
            output = fake_out.getvalue().strip()
        assert output == "Insufficient funds."

def test_deposit():
    with patch('builtins.input', side_effect=['1500']):
        deposit = Deposit(SAMPLE_BALANCE)
        with patch('sys.stdout', new=StringIO()) as fake_out:
            deposit.execute()
            output = fake_out.getvalue().strip()
        assert output == f"Amount deposited successfully. Your new balance is: {SAMPLE_BALANCE + 1500} units."

def test_pin_change_success():
    with patch('builtins.input', side_effect=[SAMPLE_PIN, '5678', '5678']):
        pin_change = PinChange(SAMPLE_PIN)
        with patch('sys.stdout', new=StringIO()) as fake_out:
            pin_change.execute()
            output = fake_out.getvalue().strip()
        assert output == "PIN changed successfully."

def test_pin_change_failure():
    with patch('builtins.input', side_effect=[SAMPLE_PIN, '5678', '1234']):
        pin_change = PinChange(SAMPLE_PIN)
        with patch('sys.stdout', new=StringIO()) as fake_out:
            pin_change.execute()
            output = fake_out.getvalue().strip()
        assert output == "PINs do not match."

def test_print_receipt():
    receipt = PrintReceipt()
    with patch('sys.stdout', new=StringIO()) as fake_out:
        receipt.execute()
        output = fake_out.getvalue().strip()
    assert "Printing receipt..." in output

def test_atm_composite():
    atm = ATMComposite()

    auth = Authentication("card123", SAMPLE_PIN)
    atm.add_operation(auth)

    if auth.execute():  # Ensure authentication is successful
        atm.add_operation(BalanceInquiry(SAMPLE_BALANCE))
        atm.add_operation(Withdrawal(SAMPLE_BALANCE))
        atm.add_operation(Deposit(SAMPLE_BALANCE))
        atm.add_operation(PinChange(SAMPLE_PIN))
        atm.add_operation(PrintReceipt())

        with patch('sys.stdout', new=StringIO()) as fake_out:
            atm.execute()
            output = fake_out.getvalue().strip()

        assert "Your current balance is:" in output
        assert "Please take your cash." in output
        assert "Amount deposited successfully." in output
        assert "PIN changed successfully." in output
        assert "Printing receipt..." in output

if __name__ == "__main__":
    pytest.main()
