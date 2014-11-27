package org.bfh.jass.game;

import org.bfh.jass.user.User;
import org.bfh.jass.user.UserAccessor;

/**
 * Created by Simon on 2014/11/27.
 */
public abstract class GameAction {
	private User user;

	protected GameAction(User user) {
		this.user = user;
	}

	public int getUserId() {
		return user.getUserID();
	}
	public User getUser() {
		return user;
	}

	public abstract void doAction(Game game);
}