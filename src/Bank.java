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
		int choice, sin, limit, accountNum = 0, amount, withdrawn = 0, deposited = 0, balance = 0, transfer = 0;
		String name, overdraft;
		Scanner input = new Scanner(System.in);
		choice = 0;
		
		System.out.println("Welcome to our Banking System. Please choose an option: ");
		
		while (choice != 13) {
			
			System.out.println("\nPlease choose an option: ");		
			System.out.println("1. Create checking account");
			System.out.println("2. Create credit account");
			System.out.println("3. Withdraw amount");
			System.out.println("4. Deposit amount");
			System.out.println("5. Get balance");
			System.out.println("6. Transfer amount");
			System.out.println("7. Cancel account");
			System.out.println("8. Suspend account");
			System.out.println("9. Reactivate account");
			System.out.println("10. Terminate account");
			System.out.println("11. Set Overdraft Limit");
			System.out.println("12. Set Overdraft option");
			System.out.println("13. Exit the system");
			
			choice = input.nextInt();
			
			switch(choice) 
			{
			
			case 1: System.out.println("Enter your name: ");
					name = readLine();
					System.out.println("Enter your sin number: ");
					sin = input.nextInt();
					System.out.println("Enter either 'monthly', 'pay per use', or 'none' for your overdraft option: ");
					overdraft = readLine();
					if (checkExistsChecking(sin)) {
						System.out.println("A checking account with sin number " + sin + " already exists. You may only have one account of each type.");
						break;
					}
					accountNum = BankAccount.createCheckingAccount(sin, name, overdraft);
					System.out.println("Account has been created. Your account number is: " + accountNum);
					System.out.println(BankAccount.allAccounts.size());
					System.out.println(BankAccount.checkingAccLog.size());
					break;
				
			case 2: System.out.println("Enter your name: ");
					name = readLine();
					System.out.println("Enter your sin number: ");
					sin = input.nextInt();
					System.out.println("Enter your desired credit limit");
					limit = input.nextInt();
					if (checkExistsCredit(sin)) {
						System.out.println("A credit account with sin number " + sin + " already exists. You may only have one account of each type.");
						break;
					}
					accountNum = BankAccount.createCreditAccount(sin, name, limit);
					System.out.println("Account has been created. Your account number is: " + accountNum);
					System.out.println(BankAccount.allAccounts.size());
					System.out.println(BankAccount.creditAccLog.size());
					break;
					
			case 3: System.out.println("Enter your account number");
					accountNum = input.nextInt();
					if (!checkExists(accountNum)) {
						System.out.println("An account with number: " + accountNum + " was not found.");
						break;
					}
					System.out.println("Enter the amount to be withdrawn");
					amount = input.nextInt();
					for (BankAccount acc : allAccounts) {
						if (acc.getAccountNumber() == accountNum) {
							withdrawn = acc.withdrawAmount(amount);
							System.out.println(amount + "$ has been withdrawn from your account. Your balance is " + acc.getBalance() + "$");
							break;
						}
					}
					break;

			case 4: System.out.println("Enter your account number");
					accountNum = input.nextInt();
					if (!checkExists(accountNum)) {
						System.out.println("An account with number: " + accountNum + " was not found.");
						break;
					}
					System.out.println("Enter the amount to be deposited");
					amount = input.nextInt();
					for (BankAccount acc : allAccounts) {
						if (acc.getAccountNumber() == accountNum) {
							acc.depositAmount(amount);;
							deposited = amount;
							System.out.println(amount + "$ has been deposited to your account. Your balance is " + acc.getBalance() + "$");
							break;
						}
					}
					break;
			
			
			case 5: System.out.println("Enter your account number");
					accountNum = input.nextInt();
				 	if (!checkExists(accountNum)) {
						System.out.println("An account with number: " + accountNum + " was not found.");
						break;
					} 
					for (BankAccount acc : allAccounts) {
						if (acc.getAccountNumber() == accountNum) {
							balance = acc.getBalance();
							System.out.println("Your balance is: " + balance + "$");
						}
					}
					break;
					
			case 6: System.out.println("Enter your account number");
					accountNum = input.nextInt();
					System.out.println("Enter the transfer account number");
					transfer = input.nextInt();
					System.out.println("Enter the amount");
					amount = input.nextInt();
					if (!checkExists(accountNum)) {
						System.out.println("An account with number: " + accountNum + " was not found.");
						break;
					}
					if (!checkExists(transfer)) {
						System.out.println("An account with number: " + accountNum + " was not found.");
						
					}
					BankAccount transferAcc = null;
					for (BankAccount acc : allAccounts) {
						if (acc.getAccountNumber() == transfer) {
							transferAcc = acc;
						}
					}
					for (BankAccount acc : allAccounts) {
						if (acc.getAccountNumber() == accountNum) {
							acc.transferAmmount(transferAcc, amount);
							System.out.println(amount + "$ was transfered to the desired account");
						}
					}
					break;
					
			}
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

	public static Boolean checkExistsChecking(int sin) {
		for (CheckingAccount x : BankAccount.checkingAccLog) {
			if (x.getSin() == sin)
				return true;
		}
		return false;
	}

	public static Boolean checkExistsCredit(int sin) {
		for (CreditAccount x : BankAccount.creditAccLog) {
			if (x.getSin() == sin)
				return true;
		}
		return false;
	}

	public static Boolean checkExists(int accNum) {
		for (BankAccount acc : BankAccount.allAccounts) {
			if (acc.getAccountNumber() == accNum)
				return true;
		}
		return false;
	}
}