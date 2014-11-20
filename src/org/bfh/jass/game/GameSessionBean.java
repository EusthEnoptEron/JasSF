package org.bfh.jass.game;

import org.bfh.jass.user.User;
import org.bfh.jass.user.UserAccessor;

import javax.annotation.ManagedBean;

@ManagedBean
public class GameSessionBean {
	private int id;
	private String name;
	private User creator;
	private GameSession session;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatorName() {
		return creator.getUsername();
	}

	public void setCreator(int id) {
		creator = UserAccessor.getCurrentInstance().getUser(id);
	}
}
