package Client;

public class Account {
	private String ID;
	int balance;
	
	public Account(String iD, int balance) {
		super();
		ID = iD;
		this.balance = balance;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
}
