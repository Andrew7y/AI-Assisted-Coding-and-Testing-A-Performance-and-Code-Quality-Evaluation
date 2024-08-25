import pytest
from io import StringIO
from contextlib import redirect_stdout
from datetime import datetime

from codeAI_2 import (
    Authentication, BalanceInquiry, Withdrawal,
    Deposit, PINChange, PrintSlip, ATMComposite
)

def test_authentication_success():
    auth = Authentication("valid_card", "1234")
    try:
        auth.execute()
        assert True  # If no exception is raised, the test passes
    except ValueError:
        assert False

def test_authentication_failure():
    auth = Authentication("invalid_card", "wrong_pin")
    with pytest.raises(ValueError):
        auth.execute()

def test_balance_inquiry():
    balance_inquiry = BalanceInquiry(1000)
    with redirect_stdout(StringIO()) as f:
        balance_inquiry.execute()
        output = f.getvalue().strip()
    assert output == "Current balance is: $1000"

def test_withdrawal_success():
    withdrawal = Withdrawal(1000, 200)
    with redirect_stdout(StringIO()) as f:
        withdrawal.execute()
        output = f.getvalue().strip()
    assert output == "Withdrew $200, new balance is $800"

def test_withdrawal_failure():
    withdrawal = Withdrawal(100, 200)
    with redirect_stdout(StringIO()) as f:
        withdrawal.execute()
        output = f.getvalue().strip()
    assert output == "Insufficient funds"

def test_deposit():
    deposit = Deposit(800, 500)
    with redirect_stdout(StringIO()) as f:
        deposit.execute()
        output = f.getvalue().strip()
    assert output == "Deposited $500, new balance is $1300"

def test_pin_change_success():
    pin_change = PINChange("1234", "5678", "5678")
    try:
        pin_change.execute()
        assert True
    except ValueError:
        assert False

def test_pin_change_failure():
    pin_change = PINChange("1234", "5678", "8765")
    with pytest.raises(ValueError):
        pin_change.execute()

def test_print_slip():
    slip = PrintSlip({
        'date': datetime.now().strftime('%Y-%m-%d'),
        'time': datetime.now().strftime('%H:%M:%S'),
        'amount': 500,
        'balance': 1300
    })
    with redirect_stdout(StringIO()) as f:
        slip.execute()
        output = f.getvalue().strip().split('\n')
    assert output[0] == "Printing slip..."
    assert "Date: " in output[1]
    assert "Time: " in output[2]
    assert "Amount: $500" in output[3]
    assert "Remaining balance: $1300" in output[4]

def test_atm_composite():
    atm_operations = ATMComposite()

    # Adding operations
    atm_operations.add(Authentication("valid_card", "1234"))
    atm_operations.add(BalanceInquiry(1000))
    atm_operations.add(Withdrawal(1000, 200))
    atm_operations.add(Deposit(800, 500))
    atm_operations.add(PINChange("1234", "5678", "5678"))
    atm_operations.add(PrintSlip({
        'date': datetime.now().strftime('%Y-%m-%d'),
        'time': datetime.now().strftime('%H:%M:%S'),
        'amount': 500,
        'balance': 1300
    }))

    # Execute all operations
    with redirect_stdout(StringIO()) as f:
        atm_operations.execute()
        output = f.getvalue().strip().split('\n')

    assert output[0] == "Authentication successful"
    assert output[1] == "Current balance is: $1000"
    assert output[2] == "Withdrew $200, new balance is $800"
    assert output[3] == "Deposited $500, new balance is $1300"
    assert output[4] == "PIN changed successfully"
    assert output[5] == "Printing slip..."
    assert "Date: " in output[6]
    assert "Time: " in output[7]
    assert "Amount: $500" in output[8]
    assert "Remaining balance: $1300" in output[9]

if __name__ == "__main__":
    pytest.main()
