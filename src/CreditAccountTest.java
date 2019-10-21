import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreditAccountTest {

	@Test
	void test() throws CreditLimitExceededException, AccountSuspendedException {
		CreditAccount acct = new CreditAccount(123, "kazi", 1001);
		int withdrawal = acct.withdrawAmount(1000);
		assertEquals(1000, withdrawal); 
		acct.suspendAccount();
		 Assertions.assertThrows(AccountSuspendedException.class, () -> {
		        int withdraw2 = acct.withdrawAmount(298);
		    }); 
		 assertEquals(-1000, acct.balance);
	}
	
	@Test
	void test2() {
		
	}

}
