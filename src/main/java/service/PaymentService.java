package service;

import pojo.CustomerPOJO;

public interface PaymentService {
	
	void payMobileBill(CustomerPOJO refCustomer);
	void transferMoney(CustomerPOJO refCustomer);
	void payTax(CustomerPOJO refCustomer);
	
	
}
