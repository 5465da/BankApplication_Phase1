package dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class EmailDAOImplTest {

	EmailDAOImpl refEmailDAO = new EmailDAOImpl();
	
	@Test
	public void test() {
		//fail("Not yet implemented");
		String recipient = "john@gmail";
		String [] random = refEmailDAO.sendMail(recipient);
		assertArrayEquals(random, refEmailDAO.sendMail(recipient));
		
	}

}
