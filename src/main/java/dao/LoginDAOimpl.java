package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import application.BankApplicationConsole;
import connection.DBConnection;

public class LoginDAOimpl implements LoginDAO {
	final static Logger logger = Logger.getLogger(LoginDAOimpl.class);
	static Connection con = DBConnection.getConnection();
	ResultSet rs;

	@Override
	public String login(String emailInput, String passwordInput) {

		try {
			PreparedStatement getCredential = con.prepareStatement(
					"select * from user_login where email=" + "'" + emailInput + "'" + " AND (password" + "=" + "'"
							+ passwordInput + "'" + " || temp_password=" + "'" + passwordInput + "')");

			rs = getCredential.executeQuery();
			if (rs.next()) {
				String email = rs.getString("email");
				String pass = rs.getString("password");
				boolean isAdmin = rs.getBoolean("is_admin");
				String temp_pass = rs.getString("temp_password");
				
				if(passwordInput.equals(pass)) {
					if(isAdmin) {
						return "admin";
					}
					else
						return getCustomerAccStatus(email) ? "cust" : "notActive";
				}
				else if(passwordInput.equals(temp_pass)) {
					return "temp";
				}
			}

		} catch (SQLException e) {
			logger.error(e);
		}
		return "";
	}

	@Override
	public boolean getCustomerAccStatus(String email) {
		String userEmail = "'" + email + "'";
		try {
			PreparedStatement getStatus = con
					.prepareStatement("select account_status from customer where User_login_email=" + userEmail);
			rs = getStatus.executeQuery();
			while (rs.next()) {
				if (rs.getString("account_status").equals("active")) {
					return true;
				}
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

	@Override
	public boolean updateTempPass(String tempPassword, String email) {
		try {
			PreparedStatement pass = con.prepareStatement("update user_login set temp_password =" + "'" + tempPassword
					+ "'" + " where email = " + "'" + email + "'");
			pass.executeUpdate();
			logger.info("Successful update temporary password from database");
			return true;
		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

	@Override
	public boolean updatePass(String password, String email) {

		try {
			PreparedStatement pass = con
					.prepareStatement("select password,temp_password from user_login where email =" + "'" + email + "'");
			rs = pass.executeQuery();
			if (rs.next()) {
				if (password.equals(rs.getString("password"))) {
					System.out.println("Sorry, you are unable to reuse an old password");
					return false;
				}
				else if(password.equals(rs.getString("temp_password"))) {
					System.out.println("You have entered temp password!");
					return false;
				}
			}
			PreparedStatement updatePass = con.prepareStatement("update user_login set password =" + "'" + password
					+ "'" + ",temp_password =" + "'" + 0 + "'" + " where email=" + "'" + email + "'");
			updatePass.executeUpdate();
			logger.info("Successful update password from database");
			return true;
		} catch (SQLException e) {
			logger.error(e);
		}

		return false;
	}

	@Override
	public boolean locateEmail(String email) {
		try {
			PreparedStatement getEmail = con
					.prepareStatement("select email from user_login where email =" + "'" + email + "'");
			rs = getEmail.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return false;
	}

}
