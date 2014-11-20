package org.bfh.jass;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Simon on 2014/11/20.
 */
public class DatabaseManager {
	private static Connection conn = null;

	private DatabaseManager(){}

	public static Connection getConnection() {
		if(conn == null) {
			try {
				DAOProperties props = new DAOProperties("db.mysql");
				String userName = props.getProperty("username", true);
				String password = props.getProperty("password", false);
				String url = props.getProperty("url", true);
				Class.forName(props.getProperty("driver", true)).newInstance();
				conn = DriverManager.getConnection(url, userName, password);
				System.out.println("Database connection established");
			} catch (Exception e) {
				System.err.println("Cannot connect to database server" + e.toString());
			}
		}
		return conn;
	}
}
