package service;

import static org.junit.Assert.*;

import org.junit.Test;

import pojo.CustomerPOJO;

public class CustomerServiceImplTest {

	CustomerServiceImpl refCustServiceImpl = new CustomerServiceImpl();
	CustomerPOJO refCustomer = new CustomerPOJO();
	@Test
	public void testPayDetails() {
		refCustServiceImpl.viewPaymentDetails(refCustomer);
	}

	@Test
	public void testCreditCardApp() {
		refCustServiceImpl.creditCardApplication(refCustomer);
	}
	@Test
	public void testCheckCardStatus() {
		refCustServiceImpl.checkCardStatus(refCustomer);
	}
	@Test
	public void testCheckCardLimit() {
		refCustServiceImpl.creditCardLimit(refCustomer);
	}
	
}
