package org.bfh.jass.game;

import org.apache.commons.lang.ArrayUtils;
import org.bfh.jass.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manage class that manages the games currently running.
 */
public class GameManager {
	private static GameManager _instance = null;
	private List<Game> games;

	private GameManager() {
		games = new ArrayList<Game>();
	}

	/**
	 * Gets the instance of the manager.
	 * @return
	 */
	public static GameManager getInstance() {
		if(_instance == null) {
			_instance  = new GameManager();
		}
		return _instance;
	}

	/**
	 * Gets a game created by a specific user.
	 * @param user user object
	 * @return a game or NULL
	 */
	public Game getGameByUser(User user) {
		for(Game game: games) {
			if(game.getCreator().getUserID() == user.getUserID())
				return game;
		}
		return null;
	}

	/**
	 * Gets the game a specific user is playing
	 * @param user
	 * @return game or NULL
	 */
	public Game getCurrentGame(User user) {
		for(Game game: games) {
			for(Player player: game.getPlayers()) {
				if(player instanceof HumanPlayer) {
					if(user.getUserID() == ((HumanPlayer)player).getUserId()) {
						return game;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Adds a game to the list.
	 * @param game
	 */
	public void addGame(Game game) {
		games.add(game);
		game.create();
	}

	/**
	 * Gets all open games.
	 * @return
	 */
	public Game[] getOpenGames() {
		List<Game> list = new ArrayList<Game>();
		for (Game game : games) {
			if(game.getState() == Game.GameState.WAITING) {
				list.add(game);
			}
		}
		return list.toArray(new Game[list.size()]);
	}

	/**
	 * Closes a game.
	 * @param game
	 */
	public void closeGame(Game game) {
		games.remove(game);

		if(game.getState() == Game.GameState.CLOSING) {
			// Write to DB...
			GameAccessor.getInstance().saveGame(game);
		}
	}
}
