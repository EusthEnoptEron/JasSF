package org.bfh.jass.game;

import org.bfh.jass.user.User;

/**
 * User: Simon
 * Date: 18.12.2014
 * Time: 10:32
 */
public class GameResult {

	GameResult.Team[] teams;
	int requiredScore;
	User creator;

	public GameResult(GameResult.Team[] teams, int requiredScore, User creator) {
		this.teams = teams;
		this.requiredScore = requiredScore;
		this.creator = creator;
	}

	public GameResult.Team getTeam(int index) {
		return teams[index];
	}

	public Team[] getTeams() {
		return teams;
	}

	public Team getWinningTeam() {
		Team winner = null;
		for(Team team: teams) {
			if(winner == null || team.getScore() > winner.getScore()) {
				winner = team;
			}
		}
		return winner;
	}

	public int getRequiredScore() {
		return requiredScore;
	}

	public User getCreator() {
		return creator;
	}


	public static class Team {
		private int id;
		private User[] users;
		private int score;

		public Team(int id, User[] users, int score) {
			this.id = id;
			this.users = users;
			this.score = score;
		}

		public Team(int id, Player[] players, int score) {
			users = new User[players.length];
			for(int i = 0; i < players.length; i++) {
				users[i] = players[i].getUser();
			}

			this.id  = id;
			this.score = score;
		}

		public User[] getUsers() {
			return users;
		}

		public void setUsers(User[] users) {
			this.users = users;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public int getId() {
			return id;
		}
	}
}
