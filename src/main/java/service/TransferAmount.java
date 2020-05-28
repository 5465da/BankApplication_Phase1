package service;

import pojo.CustomerPOJO;

public class TransferAmount implements PaymentModes {

	@Override
	public boolean paymentModes(CustomerPOJO refCustomer) {
		if (refCustomer.getAmount() <= 0) {
			System.out.println("Invalid Amount");
			System.out.println("Payment Unsuccessful");
			return false;
		} 
		return true;
	}

}
