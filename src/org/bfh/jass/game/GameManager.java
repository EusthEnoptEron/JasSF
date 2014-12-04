package org.bfh.jass.game;

import org.apache.commons.lang.ArrayUtils;
import org.bfh.jass.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Simon on 2014/12/04.
 */
public class GameManager {
	private static GameManager _instance = null;
	private List<Game> games;

	private GameManager() {
		games = new ArrayList<Game>();
	}

	public static GameManager getInstance() {
		if(_instance == null) {
			_instance  = new GameManager();
		}
		return _instance;
	}

	public Game getGameByUser(User user) {
		for(Game game: games) {
			if(game.getCreator().getUserID() == user.getUserID())
				return game;
		}
		return null;
	}

	public Game getCurrentGame(User user) {
		for(Game game: games) {
			if(Arrays.asList(game.getPlayers()).contains(user)) {
				return game;
			}
		}
		return null;
	}

	@Deprecated
	public Game createGame(User creator) {
		Game game = new Game(creator);
		games.add(game);

		return game;
	}

	public void addGame(Game game) {
		games.add(game);
	}
}
