import java.time.LocalDate;

public class DemandLoanAccount {
		
	private int sin;
	private String name;
	private int indebtedness;
	//private LocalDate currentTime;
	
	public DemandLoanAccount(int sin, String name, int indebtedness) {
		this.sin = sin;
		this.name = name;
		this.indebtedness = indebtedness;
	}	
	
	public void depositAmmount(int amount) {
		this.indebtedness += amount;
	}
	
	public int getIndebtedness() {
		return this.indebtedness;
	}
}