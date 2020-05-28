package service;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
public class EmailServerImplTest {

	EmailServerImpl refEmail = new EmailServerImpl();
	
	@Test
	public void testSendMail() {
	
		assertFalse(refEmail.sendMail("john", "john@gmail.com", "123456", "123456"));
	}
	
	@Test
	public void testPrepMsg() {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("john@gmail.com", "dddd");
			}
		});
		//assertNull(EmailServerImpl.prepareMessage(session, "john@gmail.com", "123", "dddd"));
	}

}
