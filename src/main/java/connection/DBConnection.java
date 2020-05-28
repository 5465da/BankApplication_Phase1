package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBConnection {
	final static Logger logger = Logger.getLogger(DBConnection.class);
	private static final String DB_DRIVER_CLASS="driverClassName";
	private static final String DB_USERNAME ="username";
	private static final String DB_PASSWORD ="password";
	private static final String DB_URL = "url";
	
	private static Connection connection = null;
	private static Properties properties = null;
	static {
		try {
			properties = new Properties();
			properties.load(new FileInputStream("src/connection.properties")); // give path to call .properties file
			Class.forName(properties.getProperty(DB_DRIVER_CLASS));
			connection = DriverManager.getConnection(properties.getProperty(DB_URL),properties.getProperty(DB_USERNAME),
			properties.getProperty(DB_PASSWORD));
			
		}
		catch(ClassNotFoundException |SQLException|IOException e) {
			logger.error(e);
		}
	}//end of static block
	
	public static Connection getConnection() {
		return connection;
	}
	
}