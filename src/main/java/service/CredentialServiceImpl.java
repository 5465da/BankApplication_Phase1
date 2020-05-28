package service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.log4j.Logger;



public class CredentialServiceImpl implements CredentialService {
	final static Logger logger = Logger.getLogger(CredentialServiceImpl.class);
	@Override
	public String generateNewPass() {
		String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 6) { 
			int index = (int) (rnd.nextFloat() * alphanumeric.length());
			salt.append(alphanumeric.charAt(index));
		}
		String newPassword = salt.toString();
		return newPassword;
	}
		
	public String hashing(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			   byte[] messageDigest = md.digest(password.getBytes()); 
	           BigInteger no = new BigInteger(1, messageDigest); 
	           String hashtext = no.toString(16); 
	            while (hashtext.length() < 32) { 
	                hashtext = "0" + hashtext; 
	            } 
	            return hashtext; 
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		} 
		return password;
	}
}
