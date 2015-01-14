package org.bfh.jass.user;

import org.bfh.jass.DAOProperties;
import org.bfh.jass.DatabaseManager;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.sql.*;
import java.security.*;
import java.security.spec.*;


/**
 * UserAccessor to fetch user data from the DB.
 */
public class UserAccessor {

	private static UserAccessor currentInstance;

	Connection conn = null;

	Map<String, User> userMap;

	private UserAccessor() {
		conn = DatabaseManager.getConnection();
		userMap = new HashMap<String, User>();
	}

	public static UserAccessor getCurrentInstance() {
		if (currentInstance == null) {
			currentInstance = new UserAccessor();
		}
		return currentInstance;
	}

	/**
	 * Gets a username from the database.
	 * @param username name of the user
	 * @return user object
	 */
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


				String sql = "INSERT INTO `user` (`id` ,`username` ,`password` ,`dateofbirth` ) VALUES (NULL , ?, ?, ?)";

				s = conn.prepareStatement(sql);
				s.setString(1, username);
				s.setString(2, Encryptor.createHash(password));
								
				java.sql.Date sqlDate = new java.sql.Date(dateOfBirth.getTime());


				s.setDate(3, sqlDate);
				s.executeUpdate();
				s.close();
				return getUser(username);
			} catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
				System.out.println(e);
			}


			return null;
		}
	}

	protected void commitUser(User user) {
		try {
			int count;
			PreparedStatement s;
			String sql = "UPDATE `user` SET `username` = ?,`password` = ?,`dateOfBirth` = ? WHERE `user`.`id` =? LIMIT 1 ;";
			s = conn.prepareStatement(sql);
			s.setString(1, user.getUsername());
			
			System.out.println("pwtohash: " + user.getPassword());
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
		s.executeQuery("SELECT id, username, password, dateOfBirth FROM user where username='" + username + "'");
		ResultSet rs = s.getResultSet();
		int count = 0;
		User res = null;
		while (rs.next()) {
			int userID = rs.getInt("id");
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


	public User getUser(int id) {
		try {
			PreparedStatement s = conn.prepareStatement(
				"SELECT id, username, password, dateOfBirth FROM user where id=?"
			);
			s.setInt(1, id);

			ResultSet rs = s.executeQuery();
			int count = 0;
			User res = null;
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				Date dateOfBirth = rs.getDate("dateOfBirth");
				res = new User(id, username, password, dateOfBirth);

			}
			rs.close();
			s.close();
			return res;
		} catch(SQLException e) {
			return null;
		}

	}
}