import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BankTest {

	@Test
	void test() throws AccountSuspendedException {
		CheckingAccount acc =  new CheckingAccount(123, "Kazi", "none");
		int accNum = acc.getAccountNumber();
		assertEquals(1, BankAccount.allAccounts.size());
		BankAccount acc1 = null;
		for (BankAccount x : BankAccount.allAccounts) {
			acc1 = x;
		}
		assertEquals(accNum, acc1.getAccountNumber());
	}

}
