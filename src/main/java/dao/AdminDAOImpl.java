package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import connection.DBConnection;

public class AdminDAOImpl implements AdminDAO {
	final static Logger logger = Logger.getLogger(AdminDAOImpl.class);
	static Connection con = DBConnection.getConnection();
	ResultSet rs;

	@Override
	public void getCustList() {
		
		String format = "%1$-10s|%2$-25s|%3$-20s|%4$-20s|%5$-35s|%6$-20s|%7$-20s\n";
		System.out.format(format,
				"CustomerID","Name","Balance","Account Status","Email","CreditCard Status","Credit Limit");
		try {
			PreparedStatement customerList = con.prepareStatement(
					"select customer.*,coalesce(creditcard.creditcard_status,0) as creditStatus,coalesce(creditcard.creditcard_limit,0) as credit_limit from customer left join creditcard on customer.customer_id = creditcard.customer_id");
			rs = customerList.executeQuery();
			while (rs.next()) {
				String cusID = rs.getString("customer_id");
				double balance = rs.getDouble("balance");
				String accountStatus = rs.getString("account_status");
				String email = rs.getString("User_login_email");
				String status = rs.getString("creditStatus");
				String name = rs.getString("name");
				double limit = rs.getDouble("credit_limit");
				if (status.equals("0")) {
					status = "nil";
				}
				System.out.format(format, cusID , name , balance , accountStatus , email
						, status , limit);
			}
		} 
		catch (SQLException e) {
			logger.error(e);
		}
	}

	@Override
	public void setCreditLimit(int custID, double creditLimit) {
		try {
			if (verifyCustomerID(custID)) {
				boolean result = checkCredit(custID);
				if (result) {
					PreparedStatement setCustomerLimit = con
							.prepareStatement("Update creditcard set creditcard_limit = " + creditLimit
									+ " where customer_id =" + custID);
					setCustomerLimit.executeUpdate();
					System.out.println("Successfully set customer credit limit!");
					logger.info("Successfully set customer credit limit!");
				} else {
					System.out.println("Customer doesnt have credit card!");
					logger.info("Customer doesnt have credit card!");
				}
			}
		} 
		catch (SQLException e) {
			logger.error(e);
		}
	}

	@Override
	public boolean activateAcc(int custID) {
		try {
			if (verifyCustomerID(custID)) {
				PreparedStatement updateCustomerAccount = con.prepareStatement("UPDATE customer SET account_status ="
						+ "'" + "active" + "'" + " WHERE customer_id =" + custID);
				updateCustomerAccount.executeUpdate();
				return true;
			}
		} 
		catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

	@Override
	public boolean deactivateAcc(int custID) {
		try {
			if (verifyCustomerID(custID)) {
				PreparedStatement updateCustomerAccount = con.prepareStatement("UPDATE customer SET account_status ="
						+ "'" + "inactive" + "'" + " WHERE customer_id =" + custID);
				updateCustomerAccount.executeUpdate();

				PreparedStatement updateCustCredit = con.prepareStatement(
						"Update creditcard set creditcard_status =" + "'" + "inactive" + "'" + " where customer_id =" + custID);
				
				 updateCustCredit.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

	public boolean verifyCustomerID(int custID) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select customer_id from customer where customer_id=" + custID);
			rs = ps.executeQuery();
			if (!rs.next()) {
				System.out.println("Customer ID does not exists!");
				return false;
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return true;
	}

	@Override
	public void activateCreditCard(int custID) {
		try {
			if (!verifyCustomerID(custID)) {
				return;
			}
			PreparedStatement status = con.prepareStatement("select * from creditcard where customer_id =" + custID);
			ResultSet rs = status.executeQuery();
			
			if (!rs.next()) {
				System.out.println("This customer does not have a creditcard application - Customer ID:" + custID);
				return;
			}

			if(rs.getString("creditcard_status").equals("rejected")) {
				System.out.println("Customer application is already rejected!");
				return;
			}
			
			PreparedStatement checkCustID = con
					.prepareStatement("select customer_id from customer where customer_id =" + custID);
			ResultSet rs2 = checkCustID.executeQuery();
			
			if (rs2.next()) {
				PreparedStatement activateCard = con.prepareStatement(
						"Update creditcard set creditcard_status=" + "'active'" + ", creditcard_limit =" + "'" + 1000
								+ "'" + " where customer_id = " + "'" + custID + "'");
				activateCard.executeUpdate();
				System.out.println("Customer ID: " + custID + " Application Approved!");
				logger.info("Approved creditcard Application!");
				
			} 
			else {
				System.out.println("CustomerID does not exists!");
			}
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	@Override
	public void rejectCreditCard(int custID) {
		try {
			if (!verifyCustomerID(custID)) {
				return;
			}
		
			PreparedStatement status = con.prepareStatement("select * from creditcard where customer_id =" + custID);
			ResultSet rs = status.executeQuery();

			if (!rs.next()) {
				System.out.println("This customer does not have a creditcard application - Customer ID:" + custID);
				return;
			}
			if(rs.getString("creditcard_status").equals("rejected")) {
				System.out.println("Customer application is already rejected!");
				return;
			}
			
			PreparedStatement checkCustID = con
					.prepareStatement("select customer_id from customer where customer_id =" + custID);
			rs = checkCustID.executeQuery();
			
			if (rs.next()) {
				PreparedStatement activateCard = con.prepareStatement("Update creditcard set creditcard_status="
						+ "'rejected'" + " where customer_id = " + "'" + custID + "'");
				activateCard.executeUpdate();
				
				System.out.println("Customer ID: " + custID + " Application denied!");
				logger.info("Application denied!");
				
			} else {
				System.out.println("CustomerID does not exists!");
			}
		} catch (SQLException e) {
			logger.error(e);
		}

	}

	// Checks if customer have credit card or other status
	@Override
	public boolean checkCredit(int custID) {
		try {

			PreparedStatement getStatus = con.prepareStatement("select * from creditcard where customer_id =" + custID);
			rs = getStatus.executeQuery();
			if (rs.next()) {
				String cardStatus = rs.getString("creditcard_status");
				if (cardStatus.equals("pending")) {
					System.out.println("Credit Card status is pending");
					return false;
				} 
				else if (cardStatus.equals("rejected")) {
					System.out.println("Credit Card status is rejected");
					return false;
				} 
				else if (cardStatus.equals("inactive")) {
					System.out.println("Credit Card status is inactive");
					return false;
				} 
				else
					return true;
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}
	

}
