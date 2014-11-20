package org.bfh.jass.game;

import org.bfh.jass.user.User;
import org.bfh.jass.user.UserAccessor;

/**
 * Created by Simon on 2014/11/20.
 */
public class GameSession {
	private int id;
	private String name;
	private String state;
	private User creator;
	private int team1Points;
	private int team2Points;
	private int winner;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public int getTeam1Points() {
		return team1Points;
	}

	public void setTeam1Points(int team1Points) {
		this.team1Points = team1Points;
	}

	public int getTeam2Points() {
		return team2Points;
	}

	public void setTeam2Points(int team2Points) {
		this.team2Points = team2Points;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public void commitChanges() {

	}
}
