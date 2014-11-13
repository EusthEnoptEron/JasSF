package org.bfh.jass;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.sql.*;

/*
TO DO: Change the insert method for accessing the date.
Must update the userID.

Remove the Tester  and the Connect classes

Write a Login page, then a hidden page, then a register page

 */


public class UserAccessor {

	private static UserAccessor currentInstance;

	Connection conn = null;

	Map<String, User> userMap;

	private UserAccessor() {
		conn = null;

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

		userMap = new HashMap<String, User>();
	}

	public static UserAccessor getCurrentInstance() {
		if (currentInstance == null) {
			currentInstance = new UserAccessor();
		}
		return currentInstance;
	}

	public User getUser(String username) {
		if (userMap.containsKey(username)) {
			return userMap.get(username);
		} else {
			User u = null;
			try {
				u = accessUser(username);
			} catch (SQLException e) {
			}
			if (u != null) {
				userMap.put(username, u);
			}
			return u;
		}

	}

	public User createNewUser(String username, String password, Date dateOfBirth) {
		User oldUser = getUser(username);
		if (oldUser != null) {
			throw new RuntimeException("User already Exists");
		} else {

			try {
				PreparedStatement s;


				String sql = "INSERT INTO `user` (`userID` ,`username` ,`password` ,`dateofbirth` )VALUES (NULL , ?, ?, ?)";
				//String sql = "INSERT INTO `user` (`userID` ,`username` ,`password` )VALUES (NULL , ?, ?)";

				s = conn.prepareStatement(sql);
				s.setString(1, username);
				s.setString(2, password);
				java.sql.Date sqlDate = new java.sql.Date(dateOfBirth.getTime());
				//String myString = DateFormat.getDateInstance(DateFormat.SHORT).format(dateOfBirth);


				s.setDate(3, sqlDate);
				s.executeUpdate();
				s.close();
				return getUser(username);
			} catch (SQLException e) {
				System.out.println(e);
			}


			return null;
		}
	}

	protected void commitUser(User user) {
		try {
			int count;
			PreparedStatement s;
			String sql = "UPDATE `user` SET `username` = ?,`password` = ?,`dateOfBirth` = ? WHERE `user`.`userID` =? LIMIT 1 ;";
			s = conn.prepareStatement(sql);
			s.setString(1, user.getUsername());
			s.setString(2, user.getPassword());
			java.sql.Date sqlDate = new java.sql.Date(user.getDateOfBirth().getTime());
			s.setDate(3, sqlDate);
			s.setInt(4, user.getUserID());
			count = s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return;
	}

	private User accessUser(String username) throws SQLException {
		Statement s = conn.createStatement();
		s.executeQuery("SELECT userID, username, password, dateOfBirth FROM user where username='" + username + "'");
		ResultSet rs = s.getResultSet();
		int count = 0;
		User res = null;
		while (rs.next()) {
			int userID = rs.getInt("userID");
			String username2 = rs.getString("username");
			String password = rs.getString("password");
			Date dateOfBirth = rs.getDate("dateOfBirth");
			res = new User(userID, username, password, dateOfBirth);
			System.out.println(
					"id = " + userID
							+ ", name = " + username2
							+ ", date = " + dateOfBirth);
			++count;
		}
		rs.close();
		s.close();
		System.out.println(count + " rows were retrieved");
		return res;

	}


}