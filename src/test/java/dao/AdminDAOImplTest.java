package dao;

import static org.junit.Assert.*;

import org.junit.Test;

import pojo.AdminPOJO;
import pojo.CustomerPOJO;

public class AdminDAOImplTest {

	
	AdminDAOImpl adminDAORef = new AdminDAOImpl();
	AdminPOJO refAdmin = new AdminPOJO();
	
	@Test
	public void testActivateAcc() {
		
		assertFalse("Should fail - acct already activated",adminDAORef.activateAcc(222));
	}
	
	@Test
	public void testDeactivateAcc() {

		assertFalse("admin acct invalid, should not deactivate",adminDAORef.deactivateAcc(222));
		
	}
	@Test
	public void testVerifyCustId() {
	
		assertFalse("Customer ID does not exists!",adminDAORef.verifyCustomerID(2222222));
	}
	
	@Test
	public void testCheckCredit() {
		
		assertFalse("Customer ID does not exists!",adminDAORef.checkCredit(2222222));
		assertTrue("true",adminDAORef.checkCredit(3));
	}

}
