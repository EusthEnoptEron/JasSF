package org.bfh.jass.game;

import org.bfh.jass.user.User;

/**
 * User: Simon
 * Date: 18.12.2014
 * Time: 10:32
 */
public class GameResult {
	User[] team1;
	User[] team2;

	int team1Score;
	int team2Score;

	int requiredScore;

	User creator;

	public GameResult(User[] team1, User[] team2, int team1Score, int team2Score, int requiredScore, User creator) {
		this.team1 = team1;
		this.team2 = team2;
		this.team1Score = team1Score;
		this.team2Score = team2Score;
		this.requiredScore = requiredScore;
		this.creator = creator;
	}

	public User[] getTeam1() {
		return team1;
	}

	public User[] getTeam2() {
		return team2;
	}

	public int getTeam1Score() {
		return team1Score;
	}

	public int getTeam2Score() {
		return team2Score;
	}

	public int getRequiredScore() {
		return requiredScore;
	}

	public User getCreator() {
		return creator;
	}
}
