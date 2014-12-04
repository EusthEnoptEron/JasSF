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

	public enum GameState {
		CONFIGURING,
		WAITING,
		PLAYING,
		CLOSING
	}

	private int score;
	private boolean started = false;
	private CardSuit trump;
	private GameRound round;
	public enum Team {
		EVEN,
		ODD
	}

	private GameState state;

	private Player[] players = new Player[4];


	private User creator;

	Dictionary<Team, Integer> scores;

	public Game(User creator) {
		scores = new Hashtable<Team, Integer>();
		scores.put(Team.EVEN, 0);
		scores.put(Team.ODD, 0);
		this.creator = creator;
		state = GameState.CONFIGURING;
	}

	public void start() {
		started = true;
	}
	public boolean hasStarted() {
		return started;
	}

	public boolean isFull() {
		for(Player u: players) {
			if(u == null) return false;
		}
		return true;
	}

	public void setTrump(CardSuit trump) {
		this.trump = trump;
	}

	public CardSuit getTrump() {
		return trump;
	}

	public void addScore(Team team, int score) {
		scores.put(team, scores.get(team) + score);
	}

	public GameRound getRound() {
		return round;
	}

	public void startNewRound() {
		round = null;

		if(scores.get(Team.EVEN) >= score ||
				scores.get(Team.ODD) >= score) {
			state = GameState.CLOSING;
			// TODO: Select winner

		} else {
			state = GameState.PLAYING;
			round = new GameRound(0);
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

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

}

