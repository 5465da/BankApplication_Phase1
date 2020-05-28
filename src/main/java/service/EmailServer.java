package service;

public interface EmailServer {
	
	boolean sendMail(String recipient, String email, String password, String newPassword);
}
