package service;

import pojo.CustomerPOJO;

public class PayTax implements PaymentModes {

	@Override
	public boolean paymentModes(CustomerPOJO refCustomer) {
		if (refCustomer.getAmount() <= 0) {
			System.out.println("Sorry! Invalid amount");
			System.out.println("Payment Unsuccessful");
			return false;
		} 
		return true;
	}

}
