package service;

import static org.junit.Assert.*;

import org.junit.Test;

import pojo.CustomerPOJO;

public class PaymentsTest {
	
	MobileBill MBref = new MobileBill();
	TransferAmount xferRef = new TransferAmount();
	PayTax payTaxRef = new PayTax();
	CustomerPOJO refCustomer = new CustomerPOJO();
	
	@Test
	public void testPaymentModesMobile() {
		
		assertFalse(MBref.paymentModes(refCustomer));
	}
	
	
	@Test
	public void testPaymentModesXfer() {
		
		assertFalse(xferRef.paymentModes(refCustomer));
	}
	
	@Test
	public void testPaymentModesTax() {
		
		assertFalse(payTaxRef.paymentModes(refCustomer));
	}

}
