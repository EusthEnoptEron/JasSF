package org.bfh.jass.user;

import org.bfh.jass.DAOProperties;
import org.bfh.jass.DatabaseManager;

import org.bfh.jass.user.Score;
import org.bfh.jass.user.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.sql.*;
import java.security.*;
import java.security.spec.*;


/**
 * UserAccessor to fetch user data from the DB.
 */
public class HistoryAccessor {

	private static HistoryAccessor currentInstance;

	Connection conn = null;

	private HistoryAccessor() {
		conn = DatabaseManager.getConnection();
	}

	public static HistoryAccessor getCurrentInstance() {
		if (currentInstance == null) {
			currentInstance = new HistoryAccessor();
		}
		return currentInstance;
	}

	/**
	 * Gets a username from the database.
	 * @param username name of the user
	 * @return user object
	 */
	public ArrayList<Score> getScores(int userId) {
		ArrayList<Score> scores = null;
		try {
			scores = accessScores(userId);
		} catch (SQLException e) {
			System.err.println("Query failed: " + e.getMessage());
		}
		return scores;
	}

	
	private ArrayList<Score> accessScores(int userId) throws SQLException {
		/*PreparedStatement s = conn.prepareStatement(
				"SELECT name, teamId, gameId, score, requiredScore " +
						"FROM games " +
						"   JOIN teams ON (games.id = gameId) " +
						"   JOIN teams_users ON teams.id = teamId " +
						"WHERE teams_users.userId = ?"
		);

		s.setInt(1, userId);

		ResultSet rs = s.executeQuery();

		int count = 0;
	
		ArrayList<Score> res = new ArrayList<>();
		ArrayList<Integer> teamID = new ArrayList<>();
		ArrayList<Integer> gameID = new ArrayList<>();
		while (rs.next()) {
			String name = rs.getString("name");
			int tempTeamID = rs.getInt("teamId");
			int tempGameID = rs.getInt("gameId");
			int score = rs.getInt("score");
			int requiredScore = rs.getInt("requiredScore");

			++count;
			res.add(new Score(tempGameID, tempTeamID, name, requiredScore, score));
			
			
			System.out.println(
					"teamid = " + teamID
				+ ", name = " + name
				+ ", score = " + score
				+ ", reqscore = " + requiredScore
				+ ", gameid = " + gameID);
			
		}
		System.out.println(count + " rows were retrieved");

		PreparedStatement getOtherTeam = conn.prepareStatement("SELECT teams.Id as teamId, score " +
				"FROM games " +
				"JOIN teams ON (teams.gameId = games.id) " +
				"WHERE teams.Id != ? AND games.id = ?");

		getOtherTeam.setInt(1, teamID);
		getOtherTeam.setInt(2, gameID);

		int count2 = 0;
		int teamID2 = 0;
		int score2 = 0;
		
		rs = getOtherTeam.executeQuery();
		while(rs.next())
		{
			teamID2 = rs.getInt("teamId");
			score2 = rs.getInt("score");
			
			for(Score ress : res)
			{
				ress.setTeamID2(teamID2);
				ress.setScore2(score2);
			}
			
			System.out.println(
					"teamid2 = " + teamID2
					+ ", score2 = " + score2);
			count2++;
		}*/
		
		
		
		
		// Variante 2		
		
		try
		{
		PreparedStatement s = conn.prepareStatement(
				"SELECT name, gameId, requiredScore " +
						"FROM games " +
						"   JOIN teams ON (games.id = gameId) " +
						"   JOIN teams_users ON teams.id = teamId " +
						"WHERE teams_users.userId = ?"
		);

		s.setInt(1, userId);

		ResultSet rs = s.executeQuery();

		int count = 0;
	
		ArrayList<Score> res = new ArrayList<>();
		ArrayList<Integer> gameID = new ArrayList<>();
		while (rs.next()) {
			String name = rs.getString("name");
			int tempGameID = rs.getInt("gameId");
			int requiredScore = rs.getInt("requiredScore");

			++count;
			res.add(new Score(tempGameID, name, requiredScore));
			
			gameID.add(tempGameID);
			
			System.out.println(
				"name = " + name
				+ ", reqscore = " + requiredScore
				+ ", gameid = " + tempGameID);
			
		}
		System.out.println(count + " rows were retrieved");
		
		for(int i = 0; i < res.size(); i++)
		{
			PreparedStatement getTeams = conn.prepareStatement("SELECT teams.Id as teamId, score " +
					"FROM games " +
					"JOIN teams ON (teams.gameId = games.id) " +
					"WHERE games.id = ?");
	
			getTeams.setInt(1, gameID.get(i));
	
			int count2 = 0;
			int[] teamID = new int[2];
			int[] score = new int[2];
			
			rs = getTeams.executeQuery();
			while(rs.next())
			{
				teamID[count2] = rs.getInt("teamId");
				score[count2] = rs.getInt("score");
				
				System.out.println(
						"teamid = " + teamID[count2]
						+ ", score = " + score[count2]);
				count2++;
			}
			
			System.out.println(count2 + " rows were retrieved");
			
			res.get(i).setTeamID(teamID[0]);
			res.get(i).setTeamID2(teamID[1]);
			res.get(i).setScore(score[0]);
			res.get(i).setScore2(score[1]);
			
			PreparedStatement getPlayerTeam = conn.prepareStatement(
					"SELECT teams.Id as teamId " +
							"FROM games " +
							"   JOIN teams ON (games.id = gameId) " +
							"   JOIN teams_users ON teams.id = teamId " +
							"WHERE teams_users.userId = ? AND games.id = ?"
			);
			
			getPlayerTeam.setInt(1, userId);
			getPlayerTeam.setInt(2, gameID.get(i));
			
			int count3 = 0;
			rs = getPlayerTeam.executeQuery();
			while(rs.next())
			{
				res.get(i).setPlayerTeam(rs.getInt("teamId"));
				count3++;
			}
			System.out.println(count3 + " rows were retrieved");
		}
		
		
		rs.close();
		s.close();
		return res;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}