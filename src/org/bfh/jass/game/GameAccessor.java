package org.bfh.jass.game;

import org.bfh.jass.user.User;

/**
 * User: Simon
 * Date: 18.12.2014
 * Time: 10:29
 */
public class GameAccessor {
	private static GameAccessor instance = null;
	private GameAccessor() { }

	public static GameAccessor getInstance() {
		if (instance == null) instance = new GameAccessor();

		return instance;
	}


	public GameResult getGames(User user) {

		return null;
	}

	public void saveGame(Game game) {

	}
}
