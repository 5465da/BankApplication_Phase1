package service;

public interface CredentialService {

	String generateNewPass();
	String hashing(String password);

}
