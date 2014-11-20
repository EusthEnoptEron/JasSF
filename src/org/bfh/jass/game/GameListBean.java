package org.bfh.jass.game;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.SetUtils;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ApplicationScoped
public class GameListBean implements Serializable {
	private List<GameSessionBean> games = new ArrayList<GameSessionBean>();

	public GameListBean() {
		GameSessionBean defaultGame = new GameSessionBean();
		defaultGame.setName("Default Game");
		defaultGame.setCreator(14);

		games.add(defaultGame);
	}
	/**
	 * Gets all active games.
	 * @return
	 */
	public List<GameSessionBean> getGames() {
		return ListUtils.unmodifiableList(games);
	}

	public void addGame(GameSessionBean game) {
		games.add(game);
	}

	public void removeGame(GameSessionBean game) {
		games.remove(game);
	}

	/**
	 * Removes outdated games from list.
	 * @TODO implement
	 */
	private void cleanList() {
	}
}
