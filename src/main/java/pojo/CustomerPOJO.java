package pojo;

public class CustomerPOJO {
	
	private String email;
	private String password ;
	private int customerID;
	private String creditCardApproved;
	private double amount;
	private double balance;
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCardStatus() {
		return creditCardApproved;
	}
	public void setCardStatus(String creditCardApproved) {
		this.creditCardApproved = creditCardApproved;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amt) {
		this.amount = amt;
	}
	public void setCustID(int id) {
		this.customerID = id;
	}
	public int getCustID() {
		return customerID;
	}
	

}
