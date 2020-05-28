package dao;


public interface LoginDAO {

	String login(String email, String password);
	boolean getCustomerAccStatus(String email);
	boolean locateEmail(String email);
	boolean updatePass(String password, String email);
	boolean updateTempPass(String tempPassword, String email);

}
