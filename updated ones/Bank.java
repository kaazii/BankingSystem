import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Bank {

	protected static ArrayList<AccountActivity> accountLog = BankAccount.accountLog;
	protected static Set<CheckingAccount> checkingAccLog = BankAccount.checkingAccLog;
	protected static Set<CreditAccount> creditAccLog = BankAccount.creditAccLog;
	protected static Set<BankAccount> allAccounts = BankAccount.allAccounts;

	public static void main(String[] args) throws AccountSuspendedException, OverdraftAmountExceededException, CreditLimitExceededException 
	{
		int choice, sin, limit, accountNum, amount;
		String name, overdraft;
		Scanner input = new Scanner(System.in);
		choice = 0;

		System.out.println("Welcome to our Banking System. Please choose an option: ");

		while (choice != 25) {
			System.out.println("1. Create Checking Account \n2. Create Credit Account \n3. Access existing account");
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
			//System.out.println(allAccounts.size());



			while (choice != 11) {	

				System.out.println("\nPlease choose an option: ");			
				System.out.println("1. Withdraw amount");
				System.out.println("2. Deposit amount");
				System.out.println("3. Get balance");
				System.out.println("4. Transfer amount");
				System.out.println("5. Cancel account");
				System.out.println("6. Suspend account");
				System.out.println("7. Reactivate account");
				System.out.println("8. Terminate account");
				System.out.println("9. Set Overdraft Limit");
				System.out.println("10. Set Overdraft option");
				System.out.println("11. Get back to main menu");

				choice = input.nextInt();


				switch(choice)
				{

				case 1:  System.out.println("Please enter the amount to be withdrawn: ");
						 amount = input.nextInt();
						 BankAccount.allAcc.get(BankAccount.ind-1).withdrawAmount(amount, accountNum);
						 System.out.println("$" + amount + " has been withdrawn");
						 break;

				case 2:  System.out.println("Please enter the amount to be deposited: ");
						 amount = input.nextInt();
						 BankAccount.allAcc.get(BankAccount.ind-1).depositAmount(amount, accountNum);
						 System.out.println("$" + amount + " has been deposited");
						 break;

				case 3:  System.out.println("Account Balance is $" + BankAccount.allAcc.get(BankAccount.ind-1).getBalance(accountNum));
						 break;
					
				case 4:  System.out.println("Please enter the amount to be deposited: ");
						 amount = input.nextInt();
						 BankAccount.allAcc.get(BankAccount.ind-1).depositAmount(amount, accountNum);
						 System.out.println("$" + amount + " has been deposited");
						 break;
						
				case 5:  BankAccount.allAcc.get(BankAccount.ind-1).cancelAccount(accountNum);
						 System.out.println("Account has been cancelled");
						 choice = 11;
						 break;
				
				case 6:  BankAccount.allAcc.get(BankAccount.ind-1).cancelAccount(accountNum);
						 break;
				
				case 7:  BankAccount.allAcc.get(BankAccount.ind-1).reactivateAccount(accountNum);
						 System.out.println("Account has been reactivated");
						 break;
						
				case 8:  BankAccount.allAcc.get(BankAccount.ind-1).terminateAccount(accountNum);
						 System.out.println("Account has been terminated");
						 break;
				
				case 9:  System.out.println("Please enter the overdraft limit to be set: ");
						 amount = input.nextInt();						
						 BankAccount.allAcc.get(BankAccount.ind-1).setLimit(amount, accountNum);
						 System.out.println("Overdraft limit has been set to: $" + amount);
						 break;
				
				case 10: System.out.println("Enter either 'monthly', 'pay per use', or 'none' for your overdraft option: ");
						 overdraft = readLine();						
						 BankAccount.allAcc.get(BankAccount.ind-1);
						 System.out.println("Overdraft limit has been set to: $");
						 break;
				}
			}


			break;

			case 2: System.out.println("Enter your name: ");
			name = input.next();
			System.out.println("Enter your sin number: ");
			sin = input.nextInt();
			System.out.println("Enter your credit limit: ");
			limit = input.nextInt();
			accountNum = BankAccount.createCreditAccount(sin, name, limit);
			System.out.println("Account has been created. Your account number is : " + accountNum);
			//System.out.println(allAccounts.size());

			System.out.println("Please choose an option: ");			
			System.out.println("1. Withdraw amount");
			System.out.println("2. Deposit amount");
			System.out.println("3. Get balance");
			System.out.println("4. Transfer amount");
			System.out.println("5. Cancel account");
			System.out.println("6. Suspend account");
			System.out.println("7. Reactivate account");
			System.out.println("8. Terminate account");
			System.out.println("9. Set Credit Limit");

			break;
			}
			System.out.println("Please choose an option: ");			
			System.out.println("1. Withdraw amount");
			System.out.println("2. Deposit amount");
			System.out.println("3. Get balance");
			System.out.println("4. Transfer amount");
			System.out.println("5. Cancel account");
			System.out.println("6. Suspend account");
			System.out.println("7. Reactivate account");
			System.out.println("7. Terminate account");
		}
	}

	public static void sortAccountLog() {
		int i, j;
		for (i = 1; i < accountLog.size(); i++) {
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