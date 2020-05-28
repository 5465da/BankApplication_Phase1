package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import connection.DBConnection;

public class EmailDAOImpl implements EmailDAO {
	final static Logger logger = Logger.getLogger(EmailDAOImpl.class);
	static Connection con = DBConnection.getConnection();
	Scanner sc = new Scanner(System.in);

	@Override
	public String[] sendMail(String recipient) {
		try {
			PreparedStatement ps = con.prepareStatement("select * from email");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String email = rs.getString("email");
				String password = rs.getString("password");
				return new String[] {email , password};
			}
			
		} catch (SQLException e) {
			logger.warn(e);
		}
		return null;
	}

}
