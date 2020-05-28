package dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginDAOimplTest {

	LoginDAOimpl refLoginDAO = new LoginDAOimpl();
	
	@Test
	public void testLogin() {

		assertNotNull(refLoginDAO.login("john@gmail.com", ""));
		
	}
	
	@Test
	public void testUpdatePass() {

		assertTrue(refLoginDAO.updatePass("john@gmail.com", ""));
		
	}
	@Test
	public void testLocateEmail() {

		assertFalse(refLoginDAO.locateEmail("johnxx@gmail.com"));
		
	}
	@Test
	public void testUpdateTempPass() {

		assertTrue(refLoginDAO.updateTempPass("john@gmail.com", ""));
		
	}

}
