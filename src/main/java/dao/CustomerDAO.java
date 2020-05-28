package dao;

import java.sql.SQLException;

import pojo.CustomerPOJO;

public interface CustomerDAO {
	void custPayment(int option, CustomerPOJO refCustomer) throws SQLException;
	boolean custApplyCreditCard(CustomerPOJO refCustomer);
	void getCustomerID(CustomerPOJO refCustomer);
	boolean getCustomerLimit(CustomerPOJO refCustomer);
	boolean getCustCardStatus(CustomerPOJO refCustomer);
	boolean registerCustomer(String email, String password, String name);
}
