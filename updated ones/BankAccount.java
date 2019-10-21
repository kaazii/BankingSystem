import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public abstract class BankAccount implements Comparable<BankAccount>{ 

	protected static ArrayList<BankAccount> allAcc = new ArrayList<BankAccount>();
	protected static int ind;
	
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
        ind++;
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

    public void depositAmount(int amount, int accNum)
    {
    	if (this.accountNumber == accNum) {
    	accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Doposited: $" + amount, LocalDate.now()));
    	balance += amount;
    	}
    	else {
    		System.out.println("Please reverify the account number");
    	}
    }

    public int getBalance(int accNum) {
    	if (this.accountNumber == accNum) {
        accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Checked Balance: " + balance, LocalDate.now()));
    	return balance;
    	}
    	else {
    		System.out.println("Please reverify the account number");
    	}
    	return 0;
    }

    public void suspendAccount(int accNum){
    	if (this.accountNumber == accNum) {
    	accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Account Suspended", LocalDate.now()));
    	suspended = true;
    	}
    	else {
    		System.out.println("Please reverify the account number");
    	}
    }

    public void reactivateAccount(int accNum) throws AccountSuspendedException{
    	if (this.accountNumber == accNum) {
    	if (cancelled) {
    		accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Unable to reactivate. Account has previously been cancelled.", LocalDate.now()));
    		throw new AccountSuspendedException("Unable to reactivate. Account has previously been cancelled.");
    	}
    	accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Account Reactivated", LocalDate.now()));
        suspended = false;
    	}
    	else {
    		System.out.println("Please reverify the account number");
    	}
    }
    
    public void cancelAccount(int accNum) {
    	if (this.accountNumber == accNum) {
    	accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Account Cancelled", LocalDate.now()));
    	cancelled = true;
    	}
    	else {
    		System.out.println("Please reverify the account number");
    	}    	
    }
    
    public abstract void setLimit(int amount, int accNum);
    
    public void checkActivated(BankAccount acct, int accNum) throws AccountSuspendedException{
    	if (this.accountNumber == accNum) {
    	if (this.suspended) {
    		throw new AccountSuspendedException("Account is suspended");
    	}
    	else if (this.cancelled) {
    		throw new AccountSuspendedException("Account is cancelled");
    		}
    	}
    	else {
    		System.out.println("Please reverify the account number");
    	}  
    }
    
    public int getAccountNumber() {
    	return this.accountNumber;
    }
    
//    public static int getaAccountNumber() {
//    	return accountNumber;
//    }
//    
    public void terminateAccount(int accNum) {
    	if (this.accountNumber == accNum) {
    	if (this.balance < 0) {
    		DemandLoanAccount account = new DemandLoanAccount(this.sin, this.name, this.getBalance(this.accountNumber));
    		this.balance = 0;
    		accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Account Terminated", LocalDate.now()));
    	}
    	else {
    		accountLog.add(new AccountActivity(this.sin, this.getAccountNumber(), "Account Cancelled", LocalDate.now()));
    		this.cancelAccount(this.accountNumber);
    	}
    	}
    	else {
    		System.out.println("Please reverify the account number");
    	} 
    }
    
    public int getSin() {
    	return sin;
    }
    
    public static int createCheckingAccount(int sin, String name, String overdraft) throws AccountSuspendedException {
    	CheckingAccount acct = new CheckingAccount(sin, name, overdraft);
    	return acct.getAccountNumber();
    }
    
    public static int createCreditAccount(int sin, String name, int limit) throws AccountSuspendedException {
    	CreditAccount acct = new CreditAccount(sin, name, limit);
    	return acct.getAccountNumber();
    }
    
//    public static boolean check(int accNum){
//    	if (BankAccount.getaAccountNumber() == accNum) {
//    		return true;
//    	}
//    	return false;
//    }
    
    public abstract int withdrawAmount(int amount, int accNum) throws OverdraftAmountExceededException, CreditLimitExceededException, AccountSuspendedException;
    
    public void transferAmmount(BankAccount to, int amount, int accNum) throws OverdraftAmountExceededException, CreditLimitExceededException, AccountSuspendedException {
	int withdraw = this.withdrawAmount(amount, this.accountNumber);
	to.depositAmount(withdraw, to.getAccountNumber());
    }
}
