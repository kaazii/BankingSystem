import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
class CheckingAccountTest {

	@Test
	void test() throws OverdraftAmountExceededException, AccountSuspendedException {
		CheckingAccount acct = new CheckingAccount(123, "Kazi", "pay per use");
		acct.depositAmount(300);
		assertEquals(300, acct.getBalance());
  }
	
	@Test
	void test2() throws OverdraftAmountExceededException, AccountSuspendedException {
		CheckingAccount acct = new CheckingAccount(123, "Kazi", "none");
		acct.depositAmount(300);
		 Assertions.assertThrows(OverdraftAmountExceededException.class, () -> {
		        int withdraw2 = acct.withdrawAmount(301);
		 });
	}
	
	@Test
	void test3() throws AccountSuspendedException, OverdraftAmountExceededException, CreditLimitExceededException {
		CheckingAccount acct = new CheckingAccount(123, "Kazi", "none");
		CreditAccount acct2 = new CreditAccount(123, "Kazi", 1000);
		 Assertions.assertThrows(OverdraftAmountExceededException.class, () -> {
				acct.transferAmmount(acct2, 1);
		 });
		 assertEquals(-40, acct.getBalance());
	}
}