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
	}

	public int withdrawAmount(int amount) throws CreditLimitExceededException, AccountSuspendedException {
		checkActivated(this);
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
	
	@Override
	public void setLimit (int amount) {
		accountLog.add(new AccountActivity(this.getSin(), getAccountNumber(), "Credit Limit set to: " + amount, LocalDate.now()));
		CreditAccount.CLE_PENALTY = amount;
	}

	@Override
	public int compareTo(BankAccount arg0) {
		// TODO Auto-generated method stub
		return 0;
	}	
}