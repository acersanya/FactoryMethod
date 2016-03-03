package com.netcracker.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 
 * @author acersanya
 * 	Connection pool class
 * 	Define basic pool
 */

public class JdbcConnectionPool {

	final int MAX_POOL_SIZE = 100;
	private static List<Connection> avaibleConnection = new ArrayList<>();
	private static final ResourceBundle CONFIG_BUNDLE = ResourceBundle.getBundle("com/netcracker/config/db");

	public JdbcConnectionPool() {
		initializePool();
	}

	private void initializePool() {
		while (!checkIfConnectionPoolIsFull()) {
			avaibleConnection.add(createNewConnectionPool());
		}
	}

	private synchronized boolean checkIfConnectionPoolIsFull() {
		if (avaibleConnection.size() < MAX_POOL_SIZE) {
			return false;
		}
		return true;
	}

	private Connection createNewConnectionPool() {
		Properties properties = new Properties();
		properties.setProperty("user", CONFIG_BUNDLE.getString("user"));
		properties.setProperty("password", CONFIG_BUNDLE.getString("pass"));
		properties.setProperty("useUnicode", CONFIG_BUNDLE.getString("unicode"));
		properties.setProperty("characterEncoding", CONFIG_BUNDLE.getString("encoding"));
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(CONFIG_BUNDLE.getString("url"),properties);
			return connection;
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized Connection getConnectionFromPool() {
		Connection connection = null;
		if (avaibleConnection.size() > 0) {
			connection = (Connection) avaibleConnection.get(0);
			avaibleConnection.remove(0);
		}
		return connection;
	}

	public static synchronized void returnConnection(Connection connection) {
		avaibleConnection.add(connection);
	}



}
