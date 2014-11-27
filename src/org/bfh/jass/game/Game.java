package org.bfh.jass.game;

import org.bfh.jass.user.User;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Simon on 2014/11/27.
 */
public class Game implements Serializable {
	private int score;
	private boolean started = false;
	private CardSuit trump;
	private GameRound match;

	Dictionary<Team, Integer> scores;

	public Game(User creator) {
		scores = new Hashtable<Team, Integer>();
		scores.put(Team.EVEN, 0);
		scores.put(Team.ODD, 0);
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int player) {
		playerTurn = player;
	}

	private int playerTurn;


	public void start() {
		started = true;
	}
	public boolean hasStarted() {
		return started;
	}

	public boolean isFull() {
		for(User u: players) {
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

	public User getCurrentPlayer() {
		return players[playerTurn];
	}

	public void addScore(Team team, int score) {
		scores.put(team, scores.get(team) + score);
	}

	public void startNewRound() {
		match = new GameRound(0);
	}

	public enum Team {
		EVEN,
		ODD
	}

	private GameState state;

	private User[] players = new User[4];

	public void evaluate(GameAction action) {
		state.evaluate(action);
	}


	public User[] getPlayers() {
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
			players[slot] = player;
		}
	}

	public GameState getState() {
		return state;
	}

	void changeState(GameState newSate) {
		if(state != null)
			state.onExit();

		state = newSate;

		state.onEnter();
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

}

