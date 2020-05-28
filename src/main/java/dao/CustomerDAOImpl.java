package dao;

import pojo.CustomerPOJO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import connection.DBConnection;

import controller.Controller;

public class CustomerDAOImpl implements CustomerDAO {
	Scanner input = new Scanner(System.in);
	final static Logger logger = Logger.getLogger(CustomerDAOImpl.class);
	static Controller refAppControl = new Controller();
	private double balanceAmt = 0;
	private double creditLimit = 0;
	private ArrayList<Double> amount = new ArrayList<Double>();
	static Connection con = DBConnection.getConnection();
	ResultSet rs;

	@Override
	public boolean registerCustomer(String email, String password, String name) {

		if (email == "" || password == "" || name == "") {
			return false;
		} else {
			try {
				// Checking if email have already existed in the database
				PreparedStatement ps = con
						.prepareStatement("select email from user_login where email=" + "'" + email + "'");
				rs = ps.executeQuery();
				while (rs.next()) {
					System.out.println("This email has already registered!");
					return false;
				}

				PreparedStatement regCust = con.prepareStatement("insert into user_login values(" + "'" + email + "'"
						+ "," + "'" + "user" + "'" + "," + "'" + password + "'" + "," + 0 + "," + 0 + ")");
				regCust.executeUpdate();

				// now insert a row in the customer table but to do so, we must
				// get max value and increment customer_id +1 first
				// because customer_id has to be unique
				PreparedStatement getMax = con.prepareStatement("select max(customer_id)+1 from customer");
				rs = getMax.executeQuery();
			
				int customerID = 0;
				while (rs.next()) {
					customerID = rs.getInt(1);
				}
				PreparedStatement regCust1 = con.prepareStatement("insert into customer values(" + customerID + ","
						+ 1000 + "," + "'" + "inactive" + "'" + "," + "'" + email + "'" + "," + "'" + name + "'" + ")");
				regCust1.executeUpdate();
				logger.info("Register successfully");
				return true;

			} catch (SQLException e) {
				logger.error(e);
			}
			logger.info("Register unsuccessful");
			return false;
		}
	}

	public void custPayment(int option, CustomerPOJO refCustomer) throws SQLException {

		// first check the status of the creditcard - cant pay if you dont have one
		if (!getCustCardStatus(refCustomer)) {
			return;
		} else {
			// get the remaining limit
			PreparedStatement getLimit = con.prepareStatement(
					"select creditcard_limit from creditcard where customer_id = " + refCustomer.getCustID());
			rs = getLimit.executeQuery();
			while (rs.next()) {
				creditLimit = rs.getDouble(1);
			}
			// get the balance
			PreparedStatement getCustID = con.prepareStatement(
					"select customer.balance,creditcard.creditcard_limit as credit_limit from customer left join creditcard on customer.customer_id = creditcard.customer_id where customer.customer_id ="
							+ refCustomer.getCustID());
			rs = getCustID.executeQuery();
			while (rs.next()) {
				balanceAmt = rs.getDouble("balance");
				creditLimit = rs.getDouble("credit_limit");
			}
			// get the amount to pay
			double amt = refCustomer.getAmount();
			if (amt > creditLimit) {
				System.out.println("Amount exceeds credit limit.");
				return;
			}
			if (amt > balanceAmt) {
				System.out.println("Amount exceeds balance ");
				return;
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String date = "'" + dtf.format(now) + "'";
			PreparedStatement getMax = con
					.prepareStatement("select max(payment_id), max(transfer_id)  from payment_detail");
			rs = getMax.executeQuery();

			int payID = 0, txID = 0;
			while (rs.next()) {
				payID = rs.getInt(1);
				txID = rs.getInt(2);
			}
			payID += 1;
			txID += 1;

			if (option == 1) {

				PreparedStatement mobileSQL = con.prepareStatement("insert into payment_detail values (" + payID
						+ ", 'Phone Bill', " + amt + ", " + date + " ," + refCustomer.getCustID() + ", " + txID + ");");
				mobileSQL.executeUpdate();
				
				System.out.println("Mobile Bill paid");
			} else if (option == 2) {

				PreparedStatement xferSQL = con
						.prepareStatement("insert into payment_detail values (" + payID + ", 'Transfer Amount', " + amt
								+ ", " + date + " ," + refCustomer.getCustID() + ", " + txID + ");");
				xferSQL.executeUpdate();
				System.out.println("Money transfered!");

			} else if (option == 3) {

				PreparedStatement payTax = con.prepareStatement("insert into payment_detail values(" + payID
						+ ", 'Pay Tax', " + amt + ", " + date + ", " + refCustomer.getCustID() + ", " + txID + ")");
				payTax.executeUpdate();
				System.out.println("Tax Paid!");
			} else {
				System.out.println("payment error");
			}
			balanceAmt -= amt;
			creditLimit -= amt;
			
			PreparedStatement updateCust = con.prepareStatement(
					"update customer set balance =" + balanceAmt + " where customer_id = " + refCustomer.getCustID());
			updateCust.executeUpdate();
			
			PreparedStatement updateLimit = con.prepareStatement("update creditcard set creditcard_limit ="
					+ creditLimit + " where customer_id = " + refCustomer.getCustID());
			
			updateLimit.executeUpdate();
			refCustomer.setBalance(balanceAmt);
			System.out.println("Payment Successful!");
			logger.info("Custome payment successful");
		}
	}

	@Override
	public boolean custApplyCreditCard(CustomerPOJO refCustomer) {
		int creditCardID = 0;
		try {

			PreparedStatement ps = con
					.prepareStatement("select * from creditcard where customer_id =" + refCustomer.getCustID());
			rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getString("creditcard_status").equals("pending")) {
					System.out.println("You have already submitted a Credit Card Application");
					return false;
				}
				else if(rs.getString("creditcard_status").equals("active")) {
					System.out.println("You already have a credit card!");
					return false;
				}
				else if (rs.getString("creditcard_status").equals("rejected") || rs.getString("creditcard_status").equals("inactive")) {
					
					PreparedStatement updateApp = con.prepareStatement("update creditcard set creditcard_status = "
							+ "'" + "pending" + "'" + "," + "creditcard_limit="+  0.00 + " where customer_id=" + refCustomer.getCustID());
					updateApp.executeUpdate();
					return true;
				}

			} else {
				PreparedStatement getUniqueID = con.prepareStatement("select max(creditCard_id)+1 from creditcard ");
				rs = getUniqueID.executeQuery();

				while (rs.next()) {
					creditCardID = rs.getInt(1);
				}
				PreparedStatement insertCustApplication = con.prepareStatement("insert into creditcard values("
						+ creditCardID + ", 'pending' ," + 0.00 + "," + refCustomer.getCustID() + ")");
				
				insertCustApplication.executeUpdate();
				
				logger.info("Successfully apply for credit card");
				return true;
			}

		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

	@Override
	public void getCustomerID(CustomerPOJO refCustomer) {
		String email = "'" + refCustomer.getEmail() + "'";
		try {
			PreparedStatement getID = con
					.prepareStatement("select * from customer where User_login_email=" + email);

			ResultSet rs = getID.executeQuery();
			while (rs.next()) {
				refCustomer.setBalance(rs.getDouble("balance"));
				refCustomer.setCustID(rs.getInt("customer_id"));
			}

		} catch (SQLException e) {
			logger.error(e);
		}
	}

	@Override
	public boolean getCustomerLimit(CustomerPOJO refCustomer) {
		try {
			String creditStatus = "";
			PreparedStatement getCardStatus = con.prepareStatement(
					"select creditcard_status from creditcard where customer_id=" + refCustomer.getCustID());
			rs = getCardStatus.executeQuery();
			if (rs.next()) {
				creditStatus = rs.getString("creditcard_status");
			}
			if (creditStatus.equals("rejected") || creditStatus.equals("pending") || creditStatus.equals("inactive") || creditStatus.equals("")) {
				return false;
			}
			PreparedStatement getLimit = con.prepareStatement(
					"select creditcard_limit from creditcard where customer_id = " + refCustomer.getCustID());
			rs = getLimit.executeQuery();
			if (rs.next()) {
				System.out.println("Your remaining card Limit is : " + rs.getDouble(1));
				return true;
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

	public void getPaymentDetails(CustomerPOJO refCustomer) {
		int execute = 0;
		String format = "%1$-10s|%2$-25s|%3$-20s|%4$-30s|%5$-20s|%6$-20s\n";
		System.out.format(format,"Payment ID" , "Payment Type" , "Payment Amount" ,
				"Payment Date & Time ", "Customer ID ", "Transfer ID");
		try {
			amount.clear();
			PreparedStatement getDetails = con
					.prepareStatement("SELECT * FROM payment_detail where customer_id = " + refCustomer.getCustID());

			ResultSet rs = getDetails.executeQuery();
			
			while (rs.next()) {
				int paymentID = 0;
				String paymentType = null;
				double amount = 0;
				String dateTime = null;
				int custID = 0;
				int transferID = 0;

				paymentID = rs.getInt(1);
				paymentType = rs.getString(2);
				amount = rs.getDouble(3);
				dateTime = rs.getString(4);
				custID = rs.getInt(5);
				transferID = rs.getInt(6);
				
				
				this.amount.add(amount);
				System.out.format(format, paymentID , paymentType , amount , dateTime ,
						 custID , transferID);	
				++execute;
			}
			if(execute >= 1) {
				double sum = amount.stream().mapToDouble(d->d).sum();
				System.out.println("\nTotal bill paid amount: $" + sum);
				
			}
			else {
				System.out.println("No Payment History");
			}
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	@Override
	public boolean getCustCardStatus(CustomerPOJO refCustomer) {
		String status = "";
		try {
			PreparedStatement getStatus = con.prepareStatement(
					"select creditcard_status from creditcard where customer_id =" + refCustomer.getCustID());
			ResultSet rs = getStatus.executeQuery();
			while (rs.next()) {
				status = rs.getString(1);
			}
			if (status.equals("pending")) {
				System.out.println("Your card application is still pending");
				return false;
			}
			else if (status.equals("rejected")) {
				System.out.println("Your application was rejected. You may try applying again");
				return false;
			} 
			else if (status.equals("")) {
				System.out.println("You have not applied for credit card");
				return false;
			}
			 else if (status.equals("inactive")) {
					System.out.println("your creditcard was deactivated. You may try applying again.");
					return false;
			}
			return true;

		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}
}
