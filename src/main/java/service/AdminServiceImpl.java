package service;

import java.util.Scanner;

import org.apache.log4j.Logger;
import controller.Controller;
import pojo.AdminPOJO;

public class AdminServiceImpl implements AdminService {
	Scanner sc = new Scanner(System.in);
	Controller refController = new Controller();
	final static Logger logger = Logger.getLogger(AdminServiceImpl.class);
	
	@Override
	public void menu(AdminPOJO refAdmin) {
		while (true) {
			System.out.println(
					"\n=====Admin Module===== \n1.View CustomerList \n2.Activate/Deactivate Customer Account \n3.Set Customer Credit Limit \n4.Approve/Deny Customer Credit Card Application \n5.Log Out");
			String option = sc.next();
			switch (option) {
			case "1":
				viewCustomerList();
				break;
			case "2":
				setAccountStatus();
				break;
			case "3":
				setCreditLimit();
				break;
			case "4":
				setCardStatus();
				break;
			case "5":
				return;
			default:
				System.out.println("Invalid Option");
				break;
			}
		}
	}

	@Override
	public void viewCustomerList() {
		refController.getList();
	}

	@Override
	public void setAccountStatus() {
		try {
			int custID;
			String option;
			System.out.println("Do you wish to (activate=1/deactivate=2)");
			option = sc.next();
			if (option.equals("1")) {
				System.out.println("===Customer Activation=== \nEnter Customer ID: ");
				custID = sc.nextInt();
				System.out.println("Wish to Activate A/c (Y/N)? ");
				option = sc.next();
				if (option.equalsIgnoreCase("y")) {
					if (refController.toActivateAcc(custID)) {
						System.out.println("Successfully Activate Account! ");
					}
				}
			} 
			else if (option.equals("2")) {
				System.out.println("===Customer Deactivation=== \nEnter Customer ID:");
				custID = sc.nextInt();

				System.out.println("Wish to Deactivate A/c (Y/N)? ");
				option = sc.next();
				if (option.equalsIgnoreCase("y")) {
					if (refController.toDeactivate(custID)) {
						System.out.println("Successfully Deactivate Account! ");
					}
				}
			} 
			else
				System.out.println("Invalid Input");
			
		}catch(Exception e) {
			logger.error(e);
		}
		
	}

	@Override
	public void setCreditLimit() {
		try {
			System.out.println("Enter customer ID: ");
			int custID = sc.nextInt();
			System.out.println("Enter credit limit: ");
			double creditLimit = sc.nextDouble();
			refController.setLimit(custID, creditLimit);
		}catch(Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void setCardStatus() {
		try {
			System.out.println("====Approve/Deny Credit Card Application====");
			System.out.println("Enter customer ID:");
			int custID = sc.nextInt();
			System.out.println("Do you wish to (Approve='a'/Deny='d')");
			String option = sc.next();

			if (option.equalsIgnoreCase("a")) {
				refController.approveCreditCard(custID);
			} 
			else if (option.equalsIgnoreCase("d")) {
				refController.deniedCreditCard(custID);
			} 
			else
				System.out.println("Invalid Input");
		}catch(Exception e) {
			logger.error(e);
		}
	
	}
}
