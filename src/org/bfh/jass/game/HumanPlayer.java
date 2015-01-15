package org.bfh.jass.game;

import org.bfh.jass.user.User;
import org.bfh.jass.user.UserAccessor;

/**
 * Represents a human player that can interact with the game.
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

	@Override
	public User getUser() {
		if(user == null) user = UserAccessor.getCurrentInstance().getUser(userId);
		return user;
	}

	/**
	 * Implementation of react method. Doesn't actually do anything.
	 */
	@Override
	public void react() {

	}

	@Override
	public String getName() {
		return user.getUsername();
	}
}
