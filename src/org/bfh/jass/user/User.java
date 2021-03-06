package org.bfh.jass.user;

import org.bfh.jass.game.Card;
import org.bfh.jass.game.Game;

import java.lang.String;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.security.*;
import java.security.spec.*;

public class User {
	int id;

	String username;
	String password;

	Date dateOfBirth;
	protected User(int id, String u, String p, Date d) {
		this.id = id;
		username = u;
		
		this.password = p;
		dateOfBirth = d;
	}

	public void setUserID(int userID) {
		this.id = userID;
	}

	public int getUserID() {
		return id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String pwd) {
		this.password = pwd;
	}

	public String getPassword() {
		return password;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void commitChanges() {
		UserAccessor.getCurrentInstance().commitUser(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id;
	}
}