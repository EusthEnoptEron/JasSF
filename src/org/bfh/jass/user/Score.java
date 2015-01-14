package org.bfh.jass.user;

import org.bfh.jass.game.Card;
import org.bfh.jass.game.Game;

import java.lang.String;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;
import java.security.*;
import java.security.spec.*;

public class Score {
	private int gameID;
	private int teamID;
	private int requiredScore;
	private String gameName;
	
	private int teamID2;
	private int score;
	private int score2;
	
	private String winner;
	
	private String playerTeam;

	protected Score(int gameID, String gameName, int requiredScore)
	{
		this.gameID = gameID;
		this.requiredScore = requiredScore;
		this.gameName = gameName;
		
		/*if((teamID % 2)== 0)
		{
			playerTeam = "Team 1";
		}
		else
		{
			playerTeam = "Team 2";
		}*/
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getGameID() {
		return this.gameID;
	}
	
	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	public int getTeamID() {
		return this.teamID;
	}
	
	public void setTeamID2(int teamID) {
		this.teamID2 = teamID;
	}

	public int getTeamID2() {
		return this.teamID2;
	}
	
	public void setRequiredScore(int requiredScore) {
		this.requiredScore = requiredScore;
	}

	public int getRequiredScore() {
		return this.requiredScore;
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameName() {
		return this.gameName;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return this.score;
	}
	
	public void setScore2(int score) {
		this.score2 = score;
	}

	public int getScore2() {
		return this.score2;
	}
	
	public void setWinner(String winner)
	{
		this.winner = winner;
	}
	
	public String getWinner()
	{
		if(score > score2)
		{
			return this.winner = "Team 1";
		}
		else if(score == score2)
		{
			return this.winner = "Tie";
		}
		return this.winner = "Team 2";
	}
	
	public void setPlayerTeam(int teamID)
	{
		if(this.teamID == teamID)
		{
			this.playerTeam = "Team 1";
		}
		else
		{
			this.playerTeam = "Team 2";
		}
		
	}
	
	public void setPlayerTeam(String playerTeam)
	{
		this.playerTeam = playerTeam;
	}
	
	public String getPlayerTeam()
	{
		return this.playerTeam;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Score score = (Score) o;

		if (gameID != score.gameID) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return gameID;
	}
}