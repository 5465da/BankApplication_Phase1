package service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class EmailServerImpl implements EmailServer {
	final static Logger logger = Logger.getLogger(EmailServerImpl.class);
	
	@Override
	public boolean sendMail(String recipient,String email,String password,String newPassword) {
		System.out.println("\nPlease Wait");
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});
		Message message = prepareMessage(session, email, recipient,newPassword);
		try {
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return false;
	}

	public static Message prepareMessage(Session session, String email, String recipient,String newPassword) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(email));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("Temporary Password delivery");
			message.setText("Bank Application \nYour temporary password: " + newPassword);
			return message;
		} catch (AddressException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

}
