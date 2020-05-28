package service;

import static org.junit.Assert.*;

import org.junit.Test;

public class ServiceMenuImplTest {

	ServiceMenuImpl refServiceMenu = new ServiceMenuImpl();
	
	@Test
	public void testEmailValidation() {
	
		assertFalse(refServiceMenu.emailValidation("kkkk@@@@"));
		
	}

}
