package service;

import org.junit.Test;

import pojo.AdminPOJO;

public class AdminServiceImplTest {

	AdminServiceImpl refAdminServiceImpl = new AdminServiceImpl();
	AdminPOJO refAdmin = new AdminPOJO();
	
	@Test
	public void testViewCustomerList() {
		
		refAdminServiceImpl.viewCustomerList();
		
	}
	
	@Test
	public void testAccStatus() {
		
		refAdminServiceImpl.setAccountStatus();
		
	}
	
	@Test
	public void testCreditLimit() {
		
		refAdminServiceImpl.setCreditLimit();	
	}
	
	@Test
	public void testCardStatus() {
		
		refAdminServiceImpl.setCardStatus();	
	}
	

}
