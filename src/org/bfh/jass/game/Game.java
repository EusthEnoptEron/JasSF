package org.bfh.jass.game;

import org.apache.commons.lang.ArrayUtils;
import org.bfh.jass.user.LoginBean;
import org.bfh.jass.user.User;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Simon on 2014/11/27.
 */
public class Game implements Serializable {
	Dictionary<Team, Integer> scores;
	private int score = 200;
	private boolean started = false;
	private GameRound round;
	private GameState state;
	private Player[] players = new Player[4];
	private User creator;
	private String title = "Default game";
	private Team winnerTeam;

	public Game(User creator) {
		scores = new Hashtable<Team, Integer>();
		scores.put(Team.EVEN, 0);
		scores.put(Team.ODD, 0);
		this.creator = creator;
		players[0] = new HumanPlayer(this, creator);
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
				players[i].setTeam( i % 2 == 0 ? Team.EVEN : Team.ODD );

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


	public void addScore(Team team, int score) {
		scores.put(team, scores.get(team) + score);
	}

	public GameRound getRound() {
		return round;
	}

	void startNewRound() {
		if(scores.get(Team.EVEN) >= score ||
			scores.get(Team.ODD) >= score) {
			state = GameState.CLOSING;

			setWinnerTeam(
					scores.get(Team.EVEN) > scores.get(Team.ODD)
					? Team.EVEN
					: Team.ODD
			);

			//TODO: save in DB
			GameManager.getInstance().closeGame(this);
		} else {
			round = null;
			state = GameState.PLAYING;
			round = new GameRound(this, players[0]);
		}
	}

	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Finds out which player follows after a given player
	 * @param player
	 * @return
	 */
	public Player getNextPlayer(Player player) {
		int index = ArrayUtils.indexOf(players, player);
		if(index == ArrayUtils.INDEX_NOT_FOUND) return null;

		index = (index + 1) % players.length;
		return players[index];
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

	public HumanPlayer[] getHumanPlayers() {
		List<HumanPlayer> humanPlayers = new ArrayList<HumanPlayer>();
		for(Player player:players) {
			if(player instanceof HumanPlayer)
				humanPlayers.add((HumanPlayer)player);
		}
		return humanPlayers.toArray(new HumanPlayer[humanPlayers.size()]);
	}

	public int getScore(Team team) {
		return scores.get(team);
	}

	public Team getWinnerTeam() {
		return winnerTeam;
	}

	public void setWinnerTeam(Team winnerTeam) {
		this.winnerTeam = winnerTeam;
	}

	public Player[] getPlayers(Team team) {
		if(team == Team.EVEN) {
			return new Player[]{
				players[0],
				players[2]
			};
		} else {
			return new Player[]{
				players[1],
				players[3]
			};
		}
	}

	public void abort() {
		if(state != GameState.CLOSING) {
			state = GameState.ABORTED;
			GameManager.getInstance().closeGame(this);
		}
	}

	public HumanPlayer addPlayer(User user) {
		for(int i = 0; i < players.length; i++) {
			if(players[i] == null) {
				players[i] = new HumanPlayer(this, user);
				return (HumanPlayer)players[i];
			}
		}
		return null;
	}


	public enum GameState {
		CONFIGURING,
		WAITING,
		PLAYING,
		CLOSING,
		ABORTED
	}

	public enum Team {
		EVEN,
		ODD
	}

}

