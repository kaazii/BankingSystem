import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Bank {

	protected static ArrayList<AccountActivity> accountLog = BankAccount.accountLog;
	protected static Set <CheckingAccount> checkingAccLog = BankAccount.checkingAccLog;
	protected static Set <CreditAccount> creditAccLog = BankAccount.creditAccLog;
	protected static Set <BankAccount> allAccounts = BankAccount.allAccounts;
	
	
	public static void main(String[] args) throws AccountSuspendedException, OverdraftAmountExceededException, CreditLimitExceededException 
	{
		int choice, sin, limit, accountNum;
		String name, overdraft;
		Scanner input = new Scanner(System.in);
		choice = 0;
		
		System.out.println("Welcome to our Banking System. Please choose an option: ");
		
		while (choice != 5) {
			System.out.println("1. Create Checking Account. 2. Create Credit Account");
			choice = input.nextInt();
			
			switch(choice) 
			{
			
			case 1: System.out.println("Enter your name: ");
					name = input.next();
					System.out.println("Enter your sin number: ");
					sin = input.nextInt();
					System.out.println("Enter either 'monthly', 'pay per use', or 'none' for your overdraft option: ");
					overdraft = readLine();
					accountNum = BankAccount.createCheckingAccount(sin, name, overdraft);
					System.out.println("Account has been created. Your account number is : " + accountNum);
					System.out.println(allAccounts.size());
					break;
				
			case 2: System.out.println("two");
			}
		}
	}
		
	public static void sortAccountLog() 
	{
		int i, j;
		for (i = 1; i < accountLog.size(); i++) 
		{
			AccountActivity curr = accountLog.get(i);
			j = i - 1;
			
			while (j >= 0 && accountLog.get(j).getSin() > curr.getSin()) {
				accountLog.set(j + 1, accountLog.get(j));
				j--;
			}
			accountLog.set(j + 1, curr);
		}
	}
	
	public static String readLine() {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
		
	}

}