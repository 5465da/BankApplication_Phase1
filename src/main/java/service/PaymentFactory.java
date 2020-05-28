package service;

public class PaymentFactory {
	public PaymentModes getPayment(String paymentTypes) {
		if(paymentTypes == null) {
			return null;
		}
		
		if(paymentTypes.equals("1")) {
			return new MobileBill();
		} else if (paymentTypes.equals("2")) {
			return new TransferAmount();
		} else if (paymentTypes.equals("3")) {
			return new PayTax();
		} 
		
		return null;
	}
}
