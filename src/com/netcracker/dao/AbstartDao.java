package com.netcracker.dao;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author acersanya
 *	Main class in Dao hierarchy. Childern class will extend this class
 */

public abstract class AbstartDao {

	
	public static void close(Statement st) {
	try {
		if (st != null) {
			st.close();
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

}