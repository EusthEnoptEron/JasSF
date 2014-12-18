package org.bfh.jass.game;

import org.apache.commons.lang.ArrayUtils;
import org.bfh.jass.user.LoginBean;
import org.bfh.jass.user.User;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Represents a game of Jass.
 */
public class Game {
	Dictionary<Team, Integer> scores;
	private int score = 200;
	private boolean started = false;
	private GameRound round;
	private GameState state;
	private Player[] players = new Player[4];
	private User creator;
	private String title = "Default game";
	private Team winnerTeam;
	private int trumpPicker;
	private GameResult result = null;

	/**
	 * Starts a new game.
	 * @param creator the user that intends to start the game
	 */
	public Game(User creator) {
		scores = new Hashtable<Team, Integer>();
		scores.put(Team.EVEN, 0);
		scores.put(Team.ODD, 0);
		this.creator = creator;
		state = GameState.CONFIGURING;

		trumpPicker = new Random().nextInt(players.length);
	}

	/**
	 * Gets the creator of the game.
	 * @return
	 */
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

	/**
	 * Changes from CONFIGURING to WAITING
	 */
	public void create() {
		if(state == GameState.CONFIGURING)
			state = GameState.WAITING;
	}

	/**
	 * Changes from WAITING to PLAYING
	 */
	public void start() {
		if(state == GameState.WAITING) {
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

	/**
	 * Checks if the game has started.
	 * @return
	 */
	public boolean hasStarted() {
		return state.ordinal() >= GameState.PLAYING.ordinal();
	}

	/**
	 * Checks if the game is full (= all slots are occupied)
	 * @return
	 */
	public boolean isFull() {
		for(Player u: players) {
			if(u == null) return false;
		}
		return true;
	}

	/**
	 * Gets the number of players playing in the game.
	 * (CPUs are only counted after the game has started)
	 * @return
	 */
	public int getPlayerCount() {
		int count =0;
		for(Player player : players) {
			if(player != null)
				count++;
		}
		return count;
	}

	/**
	 * Adds score to a certain team.
	 * @param team
	 * @param score
	 */
	void addScore(Team team, int score) {
		scores.put(team, scores.get(team) + score);
	}

	/**
	 * Gets the current round. During the PLAYING phase this is never NULL.
	 * @return
	 */
	public GameRound getRound() {
		return round;
	}

	/**
	 * Starts a new round.
	 */
	void startNewRound() {
		if(scores.get(Team.EVEN) >= score ||
			scores.get(Team.ODD) >= score) {
			state = GameState.CLOSING;


			setWinnerTeam(
					scores.get(Team.EVEN) > scores.get(Team.ODD)
					? Team.EVEN
					: Team.ODD
			);

//			Player[] evenPlayers = getPlayers(Team.EVEN);
//			Player[] oddPlayers  = getPlayers(Team.ODD);
//			result = new GameResult(
//					new User[]{
//							evenPlayers[0].getUser(),
//							evenPlayers[1].getUser()
//					},
//					new User[] {
//							oddPlayers[0].getUser(),
//							oddPlayers[1].getUser()
//					}, getScore(Team.EVEN), getScore(Team.ODD), score, creator);

			GameManager.getInstance().closeGame(this);
		} else {
			trumpPicker = (trumpPicker + players.length - 1)  % players.length;
			round = null;
			state = GameState.PLAYING;
			round = new GameRound(this, players[trumpPicker]);
		}
	}

	/**
	 * Gets the player list.
	 * @return
	 */
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

	/**
	 * Checks if a certain slot is still unoccupied.
	 * @param slot
	 * @return
	 */
	public boolean isFree(int slot) {
		if(slot < 4 && slot >= 0) {
			return players[slot] == null;
		}
		return false;
	}

	/**
	 * Occupies a slot.
	 * @param slot slot index
	 * @param player the player that wants to occupy the slot
	 */
	public void setPlayer(int slot, Player player) {
		if(isFree(slot)) {
			int currentSlot = getPlayerSlot(player);
			if(currentSlot >= 0) {
				players[currentSlot] = null;
				players[slot] = player;
			}
		}
	}

	/**
	 * Gets the current state of the game.
	 * @return
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * Gets the score required to win.
	 * @return
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the score required to win.
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Gets the title of the game.
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the game.
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * Gets a list of human players playing the game.
	 * @return
	 */
	public HumanPlayer[] getHumanPlayers() {
		List<HumanPlayer> humanPlayers = new ArrayList<HumanPlayer>();
		for(Player player:players) {
			if(player instanceof HumanPlayer)
				humanPlayers.add((HumanPlayer)player);
		}
		return humanPlayers.toArray(new HumanPlayer[humanPlayers.size()]);
	}

	/**
	 * Gets the score of a certain team.
	 * @param team
	 * @return
	 */
	public int getScore(Team team) {
		return scores.get(team);
	}

	public Team getWinnerTeam() {
		return winnerTeam;
	}

	public void setWinnerTeam(Team winnerTeam) {
		this.winnerTeam = winnerTeam;
	}

	/**
	 * Gets the player of a certain team.
	 * @param team
	 * @return
	 */
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

	/**
	 * Aborts the game.
	 */
	public void abort() {
		if(state != GameState.CLOSING) {
			state = GameState.ABORTED;
			GameManager.getInstance().closeGame(this);
		}
	}

	/**
	 * Adds a user to the game and gives him an arbitrary slot.
	 * @param user
	 * @return
	 */
	public HumanPlayer addPlayer(User user) {
		for(int i = 0; i < players.length; i++) {
			if(players[i] == null) {
				players[i] = new HumanPlayer(this, user);
				return (HumanPlayer)players[i];
			}
		}
		return null;
	}

	/**
	 * Removes a player from the game. This might cause the game to abort if it has already started.
	 * @param player
	 */
	public void removePlayer(Player player) {
		for(int i = 0; i < players.length; i++) {
			if(players[i] == player) {
				players[i] = null;

				if(state == GameState.PLAYING) {
					abort();
				}
				if(state == GameState.WAITING && getPlayerCount() == 0) {
					abort();
				}

				return;
			}
		}
	}


	/**
	 * Game states.
	 */
	public enum GameState {
		CONFIGURING,
		WAITING,
		PLAYING,
		CLOSING,
		ABORTED
	}

	/**
	 * Teams
	 */
	public enum Team {
		EVEN,
		ODD
	}

}

