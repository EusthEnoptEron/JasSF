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

	Map<Integer, ArrayList<Score>> scoreMap;

	private HistoryAccessor() {
		conn = DatabaseManager.getConnection();
		scoreMap = new HashMap<Integer, ArrayList<Score>>();
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
		if (scoreMap.containsKey(userId))
		{
			return scoreMap.get(userId);
		} else {
			ArrayList<Score> scores = null;
			try {
				scores = accessScores(userId);
			} catch (SQLException e) {
			}
			if (scores != null) {
				scoreMap.put(userId, scores);
			}
			return scores;
		}

	}

	
	private ArrayList<Score> accessScores(int userId) throws SQLException {
		Statement s = conn.createStatement();
		
		s.executeQuery("SELECT name, teamId, gameId, score, requiredScore FROM games JOIN teams ON (games.id = gameId) JOIN teams_users ON teams.id = teamId WHERE teams_users.userId = " + userId);
				
		ResultSet rs = s.getResultSet();
		int count = 0, winCount = 0, lossCount = 0;
		ArrayList<Score> res = new ArrayList<>();
		
		int teamID = 0;
		int gameID = 0;
		while (rs.next()) {
			
			String name = rs.getString("name");
			teamID = rs.getInt("teamId");
			gameID = rs.getInt("gameId");
			int score = rs.getInt("score");
			int requiredScore = rs.getInt("requiredScore");
			

			++count;
			res.add(new Score(gameID, teamID, name, requiredScore, score));
			
			
			System.out.println(
					"teamid = " + teamID
							+ ", name = " + name
							+ ", score = " + score
							+ ", reqscore = " + requiredScore
							+ ", gameid = " + gameID);
			
		}
		System.out.println(count + " rows were retrieved");
		
		
		//s.executeQuery("SELECT teams.Id, score FROM games JOIN teams ON (teams.gameId = games.id) WHERE NOT teams.Id = " + teamID + "AND games.id = " + gameID);
		
		int count2 = 0;
		int teamID2 = 0;
		int score2 = 200;
		
		rs = s.getResultSet();
		while(rs.next())
		{
			teamID2 = rs.getInt("teamId");
			score2 = rs.getInt("score");
			
			System.out.println(
					"teamid2 = " + teamID2
							+ ", score2 = " + score2);
			
			count2++;
		}
		
		for(Score ress : res)
		{
			ress.setTeamID2(teamID2);
			ress.setScore2(score2);
		}
		
		rs.close();
		s.close();
		System.out.println(count2 + " rows were retrieved");
		return res;

	}
}