package org.bfh.jass.game;

import org.bfh.jass.user.User;
import org.bfh.jass.user.UserAccessor;

/**
 * Created by Simon on 2014/12/04.
 */
public class HumanPlayer extends Player {
	private int userId;
	private User user;

	public HumanPlayer(Game game, int userId) {
		super(game);
		this.userId = userId;
	}
	public HumanPlayer(Game game, User user) {
		super(game);
		this.user = user;
		this.userId = user.getUserID();
	}

	public int getUserId() {
		return userId;
	}

	public User getUser() {
		if(user == null) user = UserAccessor.getCurrentInstance().getUser(userId);
		return user;
	}

	@Override
	public void react() {

	}
}
