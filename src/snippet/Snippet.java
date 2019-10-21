package snippet;

public class Snippet {
	public static void main(String[] args) {
		if (overdraftOption.toLowerCase().equals("monthly"))
		    monthlyProtection = true;
		else if (overdraftOption.toLowerCase().equals("pay per use"))
		    PPUProtection = true;
		else if (overdraftOption.toLowerCase().equals("none"))
		    noProtection = true;
		else
		    throw new IllegalArgumentException(overdraftOption + " : please enter 'monthly', 'pay per use', or 'none'");
	}
}

