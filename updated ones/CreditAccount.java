import java.time.LocalDate;

public class CreditAccount extends BankAccount {

	private static int CLE_PENALTY = 29;
	private int limit;

	public CreditAccount(int sin, String name, int limit) {
		super(sin, name);
		this.limit = limit;
		balance = 0;	
		creditAccLog.add(this);
		allAccounts.add(this);
		allAcc.add(this);
	}

	public int withdrawAmount(int amount, int accNum) throws CreditLimitExceededException, AccountSuspendedException {
		if (super.getAccountNumber() == accNum) {
		checkActivated(this, super.getAccountNumber());
		if ((limit > 1000) && (balance - amount) < -limit)
		{
			balance = balance - CLE_PENALTY;
			throw new CreditLimitExceededException("Insufficient funds. CLE penalty charged.");
		}
		else if ((limit <= 1000) && (balance - amount) < -limit)
		{	
			throw new CreditLimitExceededException("Insufficient funds. No CLE penalty charged");
		}
		balance = balance - amount;
		return amount;
		}
		else {
    		System.out.println("Please reverify the account number");
    	} 
		return 0;
	}
	
	@Override
	public void setLimit (int amount, int accNum) {
		if (super.getAccountNumber() == accNum) {
		accountLog.add(new AccountActivity(this.getSin(), getAccountNumber(), "Credit Limit set to: " + amount, LocalDate.now()));
		CreditAccount.CLE_PENALTY = amount;
		}
		else {
    		System.out.println("Please reverify the account number");
    	}
	}

	@Override
	public int compareTo(BankAccount arg0) {
		// TODO Auto-generated method stub
		return 0;
	}	
}