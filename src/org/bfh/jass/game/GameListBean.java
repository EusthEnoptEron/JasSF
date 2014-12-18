package org.bfh.jass.game;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.SetUtils;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@RequestScoped
/**
 * Bean that is responsible for providing the games managed by the GameManager to the view.
 */
public class GameListBean implements Serializable {

	public GameListBean() {
	}

	/**
	 * Gets the list of games.
	 * @return list of games
	 */
	public Game[] getGames() {
		return GameManager.getInstance().getOpenGames();
	}

	/**
	 * Checks whether or not there are any open games.
	 * @return whether or not there are any open games
	 */
	public boolean hasGames() {
		return getGames().length > 0;
	}
}
