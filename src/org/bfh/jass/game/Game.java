package org.bfh.jass.game;

import org.apache.commons.lang.ArrayUtils;
import org.bfh.jass.user.User;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Simon on 2014/11/27.
 */
public class Game implements Serializable {
	Dictionary<Team, Integer> scores;
	private int score;
	private boolean started = false;
	private CardSuit trump;
	private GameRound round;
	private GameState state;
	private Player[] players = new Player[4];
	private User creator;
	private String title;

	public Game(User creator) {
		scores = new Hashtable<Team, Integer>();
		scores.put(Team.EVEN, 0);
		scores.put(Team.ODD, 0);
		this.creator = creator;
		state = GameState.CONFIGURING;
	}

	public User getCreator() {
		return creator;
	}

	/**
	 * Finds the slot number of a player.
	 * @param player
	 * @return the slot number of the player or ArrayUtils.INDEX_NOT_FOUND (-1).
	 */
	public int getPlayerSlot(Player player) {
		return ArrayUtils.indexOf(players, player);
	}

	public void create() {
		state = GameState.WAITING;
	}

	public void start() {
		if(!hasStarted()) {
			// Fill empty slots with computer players
			for(int i = 0; i < players.length; i++) {
				if(players[i] == null)
					players[i] = new ComputerPlayer(this);
			}

			this.state = GameState.PLAYING;
			startNewRound();
		}
	}

	public boolean hasStarted() {
		return state.ordinal() >= GameState.PLAYING.ordinal();
	}

	public boolean isFull() {
		for(Player u: players) {
			if(u == null) return false;
		}
		return true;
	}

	public CardSuit getTrump() {
		return trump;
	}

	public void setTrump(CardSuit trump) {
		this.trump = trump;
	}

	public void addScore(Team team, int score) {
		scores.put(team, scores.get(team) + score);
	}

	public GameRound getRound() {
		return round;
	}

	void startNewRound() {
		round = null;

		if(scores.get(Team.EVEN) >= score ||
				scores.get(Team.ODD) >= score) {
			state = GameState.CLOSING;
			// TODO: Select winner

		} else {
			state = GameState.PLAYING;
			round = new GameRound(this, 0);
		}
	}

	public Player[] getPlayers() {
		return players;
	}

	public boolean isFree(int slot) {
		if(slot < 4 && slot >= 0) {
			return players[slot] == null;
		}
		return false;
	}

	public void setPlayer(int slot, User player) {
		if(isFree(slot)) {
			players[slot] = new HumanPlayer(this, player);
		}
	}

	public GameState getState() {
		return state;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public enum GameState {
		CONFIGURING,
		WAITING,
		PLAYING,
		CLOSING
	}

	public enum Team {
		EVEN,
		ODD
	}

}

