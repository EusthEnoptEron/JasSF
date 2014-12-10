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

	public void addGame(Game game) {
		games.add(game);
		game.create();
	}

	public Game[] getOpenGames() {
		List<Game> list = new ArrayList<Game>();
		for (Game game : games) {
			if(game.getState() == Game.GameState.WAITING) {
				list.add(game);
			}
		}
		return list.toArray(new Game[list.size()]);
	}
}
