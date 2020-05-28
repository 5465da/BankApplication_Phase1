package service;

import java.util.Scanner;

import org.apache.log4j.Logger;

import controller.Controller;
import pojo.AdminPOJO;
import pojo.CustomerPOJO;

public class ServiceMenuImpl implements ServiceMenu {

	AdminPOJO refAdmin;
	CustomerPOJO refCustomer;
	Controller refController;
	final static Logger logger = Logger.getLogger(ServiceMenuImpl.class);
	Scanner sc = new Scanner(System.in);

	@Override
	public void menu() {
		while (true) {
			System.out.println("\n=====Menu===== \n1:Login \n2:ForgetPassword \n3:Register new customer \n4:Exit");
			String option = sc.next();
			switch (option) {
			case "1":
				login();
				break;
			case "2":
				forgetPassword();
				break;
			case "3":
				registerCustomer();
				break;
			case "4":
				System.exit(1);
				break;
			default:
				System.out.println("Invalid Input");
				break;
			}
		}
	}

	@Override
	public void registerCustomer() {
		Scanner input = new Scanner(System.in);
		refController = new Controller();
		try {
			String email, password = null, name;
			while (true) {
				System.out.println("====Registration====\nEnter Name: ");
				name = input.nextLine();
				System.out.println("Enter Email: ");
				email = sc.next();
				if (!emailValidation(email)) {
					System.out.println("Invalid Email address, Please try again");
					continue;
				}
				do {
					System.out.println("Enter Password: ");
					password = sc.next();
					if (password.length() >= 8) {
						break;
					} 
					else {
						System.out.println("Please enter 8 character password length");
					}	
				} while (password.length() < 8);
				break;
			}
			password = refController.tocsHashing(password);
			boolean regSuccess = refController.registerCustomer(email, password, name);
			if (regSuccess) {
				System.out.println("Customer registered!");
			} else {
				System.out.println("Registration Failed");
			}
		}
		catch(Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void login() {
		refController = new Controller();
		try {
			System.out.println("\n====LOGIN====\nEnter Email ID: ");
			String email = sc.next();
			System.out.println("Enter Password: ");
			String password = sc.next();
			password = refController.tocsHashing(password);
			String loginResult = refController.toLogin(email, password);

			if (loginResult.equals("admin")) {
				System.out.println("Sucessfully Login..");
				refController.toAdminModule(refAdmin);
				logger.info("Admin login successfully");
			} 
			else if (loginResult.equals("notActive")) {
				System.out.println("Your Account is not activated by the Administrator");
				logger.info("Your Account is not activated by the Administrator");
			} 
			else if (loginResult.equals("cust")) {
				refCustomer = new CustomerPOJO();
				refCustomer.setEmail(email);
				refCustomer.setPassword(password);
				refController.getCustomerDetails(refCustomer);
				System.out.println("Successfully Login..");
				logger.info("Customer login successfully");
				refController.toCustomerModule(refCustomer);
			}
			else if (loginResult.equals("temp")) {
				changePassword(email);
			} 
			else {
				System.out.println("Sorry wrong credentials");
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void changePassword(String email) {
		try {
			String password, rePassword;
			System.out.println("\n=====Change Password=====");
			do {
				System.out.println("Enter new password");
				password = sc.next();
				if (password.length() >= 8) {
					break;
				} 
				else {
					System.out.println("Please enter 8 character password length");
				}
			} while (password.length() < 8);

			do {
				System.out.println("Re-type Password: ");
				rePassword = sc.next();
				if (password.equals(rePassword)) {
					break;
				} 
				else {
					System.out.println("Password doesn't match!!");
				}
			} while (!password.equals(rePassword));
			password = refController.tocsHashing(password);
			
			if (refController.updatePass(password, email)) {
				System.out.println("Successfully update your Password");
				login();
			} 
			else {
				System.out.println("Fail to change Password");
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void forgetPassword() {
		refController = new Controller();
		try {
			System.out.println("Enter Email Address: ");
			String email = sc.next();
			boolean result = refController.verifyEmail(email);
			if (result) {
				String newPassword = refController.tocsNewPass();
				String[] emailDetails = refController.getEmailCredential(email);
				EmailServerImpl EmailService = new EmailServerImpl();
				if (EmailService.sendMail(email, emailDetails[0], emailDetails[1], newPassword)) {
					newPassword = refController.tocsHashing(newPassword);
					refController.tempPass(newPassword, email);
					System.out.println("Successfully generated temp password and sent to your email!");
					logger.info("Successfully generated temp password and sent to your email");
				} 
				else {
					System.out.println("Unsuccessfully resetting password");
					logger.debug("Unsuccessfully resetting password");
				}
			} 
			else {
				System.out.println("Email does not exists!");
				logger.debug("Email does not exists!");
			}
		}catch(Exception e) {
			logger.error(e);
		}
	}

	static boolean emailValidation(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

}
