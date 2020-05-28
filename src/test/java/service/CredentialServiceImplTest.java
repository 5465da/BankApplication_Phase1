package service;

import static org.junit.Assert.*;

import org.junit.Test;

public class CredentialServiceImplTest {

	CredentialServiceImpl refCred = new CredentialServiceImpl();
	
	@Test
	public void testGenerateNewPass() {
		assertNotNull(refCred.generateNewPass());
	}
	
	@Test
	public void testHashing() {
		assertNotNull(refCred.hashing("passwordtohash"));
	}

}
