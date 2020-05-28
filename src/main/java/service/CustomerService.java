package service;

import pojo.CustomerPOJO;

public interface CustomerService {
	void menu(CustomerPOJO refCustomer);
	void creditCardApplication(CustomerPOJO refCustomer);
	void creditCardLimit(CustomerPOJO refCustomer);
	void viewPaymentDetails(CustomerPOJO refCustomer);
	void checkCardStatus(CustomerPOJO refCustomer);
	void viewBalance(CustomerPOJO refCustomer);
	
}
