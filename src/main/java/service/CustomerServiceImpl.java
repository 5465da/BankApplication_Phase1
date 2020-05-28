package service;

import java.util.Scanner;

import org.apache.log4j.Logger;

import controller.Controller;
import pojo.CustomerPOJO;

public class CustomerServiceImpl implements CustomerService, PaymentService {
	
	final static Logger logger = Logger.getLogger(CustomerServiceImpl.class);
	Scanner sc = new Scanner(System.in);
	int mobileNum, accountNo, taxAcc;
	double amt;
	Controller refController = new Controller();

	@Override
	public void menu(CustomerPOJO refCustomer) {
		while (true) {
			System.out.println(
					"\n=====Customer Module===== \n1.Pay Mobile Bill \n2.Transfer Money \n3.Pay Tax \n4.View Payment history"
							+ "\n5.Apply for Credit Card  \n6.Check Card Approval Status \n7.Check Credit Card Limit \n8.Check Balance \n9.Log Out\n");
			String option = sc.next();
			switch (option) {
			case "1":
				payMobileBill(refCustomer);
				break;
			case "2":
				transferMoney(refCustomer);
				break;
			case "3":
				payTax(refCustomer);
				break;
			case "4":
				viewPaymentDetails(refCustomer);
				break;
			case "5":
				creditCardApplication(refCustomer);
				break;
			case "6":
				checkCardStatus(refCustomer);
				break;
			case "7":
				creditCardLimit(refCustomer);
				break;
			case "8":
				viewBalance(refCustomer);
				break;
			case "9":
				System.out.println("Thank You for Banking with us!");
				return;
			default:
				System.out.println("Invalid Input");
				break;
			}
		}
	}
	@Override
	public void viewBalance(CustomerPOJO refCustomer) {
		System.out.println("\nYour Balance: $" + refCustomer.getBalance());
	}

	@Override
	public void checkCardStatus(CustomerPOJO refCustomer) {
		if(refController.getCardStatus(refCustomer)) {
			System.out.println("Your Credit card is active");
		}
	}
	
	PaymentFactory refPaymentFactory = new PaymentFactory();
	
	@Override
	public void payMobileBill(CustomerPOJO refCustomer) {
		try {
			System.out.println("Enter Mobile no : ");
			mobileNum = sc.nextInt();

			System.out.println("Bill Amount : ");
			amt = sc.nextDouble();
			refCustomer.setAmount(amt);

			 
			 PaymentModes MobileBill = refPaymentFactory.getPayment("1");
			 if (MobileBill != null) {
				 boolean result = MobileBill.paymentModes(refCustomer);
				 if(result) {
					 refController.custPayment(1, refCustomer);
				 }
			 }
		}catch(Exception e) {
			logger.error(e);
		}
	
	}
	

	@Override
	public void transferMoney(CustomerPOJO refCustomer) {
		try {
			System.out.println("Enter Account No : ");
			accountNo = sc.nextInt();

			System.out.println("Amount to transfer : ");
			amt = sc.nextDouble();
			refCustomer.setAmount(amt);
			
			PaymentModes TransferAmount = refPaymentFactory.getPayment("2");
			if(TransferAmount != null) {
				boolean result = TransferAmount.paymentModes(refCustomer);
				if(result) {
					refController.custPayment(2, refCustomer);
				}
			}
		}catch(Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void payTax(CustomerPOJO refCustomer) {
		try {
			System.out.println("Tax Account : ");
			taxAcc = sc.nextInt();

			System.out.println("Amount : ");
			amt = sc.nextDouble();
			refCustomer.setAmount(amt);
			
			PaymentModes PayTax = refPaymentFactory.getPayment("3");
			if(PayTax != null) {
				boolean result = PayTax.paymentModes(refCustomer);
				if(result) {
					refController.custPayment(3, refCustomer);
				}
			}
		}catch(Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void creditCardApplication(CustomerPOJO refCustomer) {
		try {
			System.out.println("\n====Apply for Credit Card====");
			System.out.println("Do you wish to apply for credit card? (y/n)");
			String option = sc.next();
			if(option.equalsIgnoreCase("y")) {
				if(refController.applyCreditCard(refCustomer)) {
					System.out.println("Your credit card application has been sent for approval!");
				}
				else {
					System.out.println("Application Unsuccessful!");
				}
			}
			else if(option.equalsIgnoreCase("n")) {
				return;
			}
			else
				System.out.println("Invalid Input");
		}catch(Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void creditCardLimit(CustomerPOJO refCustomer) {
		 if(!refController.getLimit(refCustomer)) {
			System.out.println("You have no credit card");
		}
	}

	@Override
	public void viewPaymentDetails(CustomerPOJO refCustomer) {
		refController.viewDetails(refCustomer);
	}

}
