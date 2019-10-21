import java.time.LocalDate;

public class CheckingAccount extends BankAccount {

	public static int OVERDRAFT_LIMIT = 300;
	public static final int OVERDRAFT_FEE = 5;
	public static final int NSF_FEE = 40;
	private boolean monthlyProtection;
	private boolean PPUProtection;
	private boolean noProtection;

	public CheckingAccount(int sin, String name, String overdraftOption) throws AccountSuspendedException {
		super(sin, name);
		setOverdraftOption(overdraftOption);
		balance = 0;
		checkingAccLog.add(this);
		allAccounts.add(this);
	}

	public void setOverdraftOption(String overdraftOption) throws AccountSuspendedException {
		checkActivated(this);
		if (overdraftOption.toLowerCase().equals("monthly")) {
			monthlyProtection = true;
			noProtection = false;
			PPUProtection = false;
			accountLog.add(new AccountActivity(getSin(), getAccountNumber(), "Overdraft Option set to: monthly", LocalDate.now()));
		} else if (overdraftOption.toLowerCase().equals("pay per use")) {
			PPUProtection = true;
			monthlyProtection = false;
			noProtection = false;
			accountLog.add(new AccountActivity(getSin(), getAccountNumber(), "Overdraft Option set to: pay per use", LocalDate.now()));
		} else if (overdraftOption.toLowerCase().equals("none")) {
			noProtection = true;
			PPUProtection = false;
			monthlyProtection = false;
			accountLog.add(new AccountActivity(getSin(), getAccountNumber(), "Overdraft Option set to: none", LocalDate.now()));
		} else
			throw new IllegalArgumentException(overdraftOption + " : please enter 'monthly', 'pay per use', or 'none'");
	}

	public int withdrawAmount(int amount) throws OverdraftAmountExceededException, AccountSuspendedException {
		checkActivated(this);
		if (noProtection) {
			if (balance < amount) {
				balance -= NSF_FEE;
				accountLog.add(new AccountActivity(getSin(), getAccountNumber(), "Insufficient funds. NSF fee charged.", LocalDate.now()));
				throw new OverdraftAmountExceededException("Insufficient funds. NSF fee charged.");
			}
			accountLog.add(new AccountActivity(getSin(), getAccountNumber(), "Amount withdrawn: " + amount, LocalDate.now()));
			balance -= amount;
			return amount;
		} 
		else if (PPUProtection) {
			if ((balance - amount) < -OVERDRAFT_LIMIT) {
				accountLog.add(new AccountActivity(getSin(), getAccountNumber(), "Insufficient funds" + amount, LocalDate.now()));
				throw new OverdraftAmountExceededException("Insufficient funds.");
			} 
			else if (balance - amount < 0 && balance - amount >= -OVERDRAFT_LIMIT) {
				balance -= amount;
				balance -= OVERDRAFT_FEE;
				accountLog.add(new AccountActivity(getSin(), getAccountNumber(), "Amount withdrawn: " + amount + " Overdraft fee applied", LocalDate.now()));
				return amount;
			}
			accountLog.add(new AccountActivity(getSin(), getAccountNumber(), "Amount withdrawn: " + amount, LocalDate.now()));
			balance -= amount;
			return amount;
		} 
		else if (monthlyProtection) {
			if ((balance - amount) < -OVERDRAFT_LIMIT) {
				accountLog.add(new AccountActivity(getSin(), getAccountNumber(), "Insufficient funds" + amount, LocalDate.now()));
				throw new OverdraftAmountExceededException("Insufficient funds.");
			}
			accountLog.add(new AccountActivity(getSin(), getAccountNumber(), "Amount withdrawn: " + amount, LocalDate.now()));
			balance -= amount;
			return amount;
		}
		return 0;
	}
	

	@Override
	public void setLimit(int amount) {
		accountLog.add(new AccountActivity(getSin(), getAccountNumber(), "Overdraft Limit set to: " + amount, LocalDate.now()));
		CheckingAccount.OVERDRAFT_LIMIT = amount;	
	}

	@Override
	public int compareTo(BankAccount o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
