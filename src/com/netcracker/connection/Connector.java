package com.netcracker.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
/**
 * 
 * @author acersanya
 *	Basic class for DriverManager
 */

public class Connector {
	private static final ResourceBundle CONFIG_BUNDLE = ResourceBundle.getBundle("src/db.properties");

	/**
	 * Gets the connection.
	 * 
	 * @param properties
	 *            the properties
	 * @return the connection
	 */
	public Connection getConnection(Properties properties) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(CONFIG_BUNDLE.getString("url"), properties);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return connection;
	}
}