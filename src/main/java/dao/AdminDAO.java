package dao;

public interface AdminDAO {
	
	void getCustList();
	boolean activateAcc(int cust_id);
	boolean deactivateAcc(int cust_id);
	void activateCreditCard(int custID);
	void rejectCreditCard(int custID);
	void setCreditLimit(int custID, double creditLimit);
	boolean checkCredit(int custID);
	
}
