import java.time.LocalDate;

public class AccountActivity {
	
	private int sin;
	protected String activity;
	protected LocalDate date;
	
    public AccountActivity(int sin, int accountNum, String activity, LocalDate date){
    	this.sin = sin;
    	this.activity = activity;
    	this.date = date;
    }
    
    public int getSin(){
    	return this.sin;
    }
    
    public LocalDate getDate() {
    	return this.date;
    }
    
    public String getMessage() {
    	return this.activity;
    }
}
