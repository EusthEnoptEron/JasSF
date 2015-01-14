/*
  Modification:
  add the functionalities about the recognition of the chief (only answer to one's boss)
  Author: Emmanuel Benoist
  Date:  Octobre 7, 2010
 */


package org.bfh.jass.user;
import org.bfh.jass.user.LoginBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.faces.context.FacesContext;
import java.security.*;
import java.security.spec.*;
import javax.faces.event.ComponentSystemEvent;
import java.io.IOException;

@ManagedBean
@SessionScoped
public class HistoryBean implements Serializable {
	@ManagedProperty("#{loginBean}")
	private LoginBean user;
	public LoginBean getUser() {
		return user;
	}
	
	public void setUser(LoginBean user) {
		System.out.println("------------------------------");
		System.out.println("set bean");
		System.out.println("------------------------------");

		this.user = user;
	}

	public HistoryBean() {
	}

	/**
	 * Gets the list of games.
	 * @return list of games
	 */
	public Score[] getScores() {
		return History.getInstance().getScoresByUser(user.getUser());
	}
	
	public History getHistory()
	{
		return History.getInstance();
	}

	/**
	 * Checks whether or not there are any open games.
	 * @return whether or not there are any open games
	 */
	public boolean hasScores() {
		return getScores().length > 0;
	}
}
