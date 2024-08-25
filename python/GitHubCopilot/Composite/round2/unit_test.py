import pytest
from code_2 import Authentication, BalanceInquiry, Withdrawal, Deposit, PinChange, PrintReceipt, ATMComposite, Account

@pytest.fixture
def account():
    return Account(account_number="1234567890", balance=1000, pin="1234")

def test_authentication_success():
    auth = Authentication(card_number="1234567890", pin="1234")
    auth.execute()
    assert auth.is_authenticated

def test_authentication_failure():
    auth = Authentication(card_number="1234567890", pin="wrong_pin")
    auth.execute()
    assert not auth.is_authenticated

def test_authentication_max_attempts():
    auth = Authentication(card_number="1234567890", pin="wrong_pin", max_attempts=1)
    auth.execute()
    auth.execute()
    assert not auth.is_authenticated

def test_balance_inquiry(account):
    balance_inquiry = BalanceInquiry(account)
    balance_inquiry.execute()
    assert account.get_balance() == 1000

def test_withdrawal_success(account):
    withdrawal = Withdrawal(account, 200)
    withdrawal.execute()
    assert account.get_balance() == 800

def test_withdrawal_insufficient_funds(account):
    withdrawal = Withdrawal(account, 1200)
    withdrawal.execute()
    assert account.get_balance() == 1000  # Balance should remain unchanged

def test_deposit(account):
    deposit = Deposit(account, 500)
    deposit.execute()
    assert account.get_balance() == 1500

def test_pin_change_success(account):
    pin_change = PinChange(account, old_pin="1234", new_pin="5678")
    pin_change.execute()
    assert account.verify_pin("5678")

def test_pin_change_failure(account):
    pin_change = PinChange(account, old_pin="wrong_pin", new_pin="5678")
    pin_change.execute()
    assert not account.verify_pin("5678")

def test_print_receipt():
    print_receipt = PrintReceipt(transaction_details="Transaction details here")
    print_receipt.execute()
    # Assuming the execute method prints a message, you can capture the output using capsys
    # and assert the expected output

def test_atm_composite(account):
    auth = Authentication(card_number="1234567890", pin="1234")
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
    # Capture the output and assert the expected sequence of operations

if __name__ == "__main__":
    pytest.main()