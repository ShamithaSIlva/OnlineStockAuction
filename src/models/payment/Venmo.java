package models.payment;

public class Venmo extends BankAccount {	

	public Venmo( double balance, String swiftCode, String accountNumber, long userID, String emailID )
	{
		super( balance, swiftCode, accountNumber, userID, emailID,"venmo" );
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validateAccount() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String makePayment(double amount) {
		return null;
	}

	@Override
	public StringBuilder printReceipt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double checkBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addCash(double amount) {
		// TODO Auto-generated method stub

	}

}
