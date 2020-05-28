package dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import pojo.CustomerPOJO;

public class CustomerDAOImplTest {
	CustomerDAOImpl custDAORef = new CustomerDAOImpl();
	CustomerPOJO refCustomer = new CustomerPOJO();
	
	//please note, these test are built on specific DB data
	//may not work if your DB has different data
	
	@Test
	public void testRegCust() {
		
		refCustomer.setCustID(2);
		refCustomer.setEmail("john@gmail.com");
		refCustomer.setCardStatus("active");
		refCustomer.setAmount(9);
	
		assertFalse("customer is already registered",custDAORef.registerCustomer("john@gmail.com", "123", "john"));
	}
	
	@Test
	public void testGetCustomerLimit() {
		
		refCustomer.setCustID(2);
		refCustomer.setEmail("john@gmail.com");
		refCustomer.setCardStatus("active");
		refCustomer.setAmount(9);
		
		assertTrue("limit should return",custDAORef.getCustomerLimit(refCustomer));
		
	}
	@Test
	public void testApplyCard() {
		
		refCustomer.setCustID(2);
		refCustomer.setEmail("john@gmail.com");
		refCustomer.setCardStatus("active");
		refCustomer.setAmount(9);
		
		assertFalse("this customer already has a credit card",custDAORef.custApplyCreditCard(refCustomer));
		
		
	}
	@Test
	public void testCardStatus() {
		
		refCustomer.setCustID(2);
		refCustomer.setEmail("john@gmail.com");
		refCustomer.setCardStatus("active");
		refCustomer.setAmount(9);
		
		assertTrue("should have a card",custDAORef.getCustCardStatus(refCustomer));
		
	}

}
