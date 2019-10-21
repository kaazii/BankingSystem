import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public abstract class BankAccount implements Comparable<BankAccount>{ 

	protected static ArrayList<AccountActivity> accountLog = new ArrayList<AccountActivity>();
	
	protected static Set <CheckingAccount> checkingAccLog = new TreeSet<CheckingAccount>();
	protected static Set <CreditAccount> creditAccLog = new TreeSet<CreditAccount>();
	protected static Set <BankAccount> allAccounts = new TreeSet<BankAccount>();
	
    protected LocalDate currentTime;
    protected int balance;
    private int sin;
    private int accountNumber;
    private boolean suspended = false;
    private boolean cancelled = false;
    private String name;
    
    private Set<Integer> uniqueAccountNo = new TreeSet<Integer>();


    public BankAccount(int sin, String name){
        this.sin = sin;
        this.name = name;
        accountNumber =  getUniqueAccountNum();
        accountLog.add(new AccountActivity(sin, accountNumber, "Account Created", LocalDate.now()));
    }
    
    private int getUniqueAccountNum() {
    	while (true) 
    	{
    		int uniqueAccountNum = (int) (1000000 + Math.random() * 900000);
    		if (uniqueAccountNo.add(uniqueAccountNum)) {
    			uniqueAccountNo.add(uniqueAccountNum);
    			return uniqueAccountNum;
    		}
    	}
    }

    public void depositAmount(int amount)
    {
    	accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Doposited: $" + amount, LocalDate.now()));
    	balance += amount;
    }

    public int getBalance() {
        accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Checked Balance: " + balance, LocalDate.now()));
    	return balance;
    }

    public void suspendAccount(){
    	accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Account Suspended", LocalDate.now()));
    	suspended = true;
    }

    public void reactivateAccount() throws AccountSuspendedException{
    	if (cancelled) {
    		accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Unable to reactivate. Account has previously been cancelled.", LocalDate.now()));
    		throw new AccountSuspendedException("Unable to reactivate. Account has previously been cancelled.");
    	}
    	accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Account Reactivated", LocalDate.now()));
        suspended = false;
    }
    
    public void cancelAccount() {
    	accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Account Cancelled", LocalDate.now()));
    	cancelled = true;
    }
    
    public abstract void setLimit(int amount);
    
    public void checkActivated(BankAccount acct) throws AccountSuspendedException{
    	if (this.suspended) {
    		throw new AccountSuspendedException("Account is suspended");
    	}
    	else if (this.cancelled) {
    		throw new AccountSuspendedException("Account is cancelled");
    	}
    }
    
    public int getAccountNumber() {
    	return this.accountNumber;
    }
    
    public void terminateAccount() {
    	if (this.balance < 0) {
    		DemandLoanAccount account = new DemandLoanAccount(this.sin, this.name, this.getBalance());
    		this.balance = 0;
    		accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Account Terminated", LocalDate.now()));
    	}
    	else {
    		accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Account Cancelled", LocalDate.now()));
    		this.cancelAccount();
    	}
    }
    
    public int getSin() {
    	return sin;
    }
    
    public static int createCheckingAccount(int sin, String name, String overdraft) throws AccountSuspendedException {
    	CheckingAccount acct = new CheckingAccount(sin, name, overdraft);
    	return acct.getAccountNumber();
    }
    
    public abstract int withdrawAmount(int amount) throws OverdraftAmountExceededException, CreditLimitExceededException, AccountSuspendedException;
    
    public void transferAmmount(BankAccount to, int amount) throws OverdraftAmountExceededException, CreditLimitExceededException, AccountSuspendedException {
	int withdraw = this.withdrawAmount(amount);
	to.depositAmount(withdraw);
    }
}
