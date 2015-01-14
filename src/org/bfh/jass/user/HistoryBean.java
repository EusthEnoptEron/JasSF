package org.bfh.jass.user;

import org.apache.commons.lang.ArrayUtils;
import org.bfh.jass.user.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.bfh.jass.user.LoginBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import java.io.IOException;

/**
 * Manage class for a players match history
 */
@ManagedBean
@SessionScoped
public class HistoryBean implements Serializable {
	@ManagedProperty("#{loginBean}")
	private LoginBean user;
	public LoginBean getUser() {
		return user;
	}
	
	public void setUser(LoginBean user) {
		System.out.println("------------------------------");
		System.out.println("set bean");
		System.out.println("------------------------------");

		this.user = user;
	}
	
	
	private static HistoryBean _instance = null;
	private List<Score> scores;
	
	private double winRatio;
	private int winCount;
	private int lossCount;
	private int noOfGamesPlayed;

	private HistoryBean() {
		this.scores = new ArrayList<Score>();
		
	}
	
	public void calculateWins()
	{
		int wins = 0;
		for(Score score : scores)
		{
			if(score.getWinner().equals(score.getPlayerTeam()))
			{
				wins++;
			}
		}
		
		this.winCount = wins;
		this.lossCount = scores.size() - this.winCount;
		
		calculateWinRatio();
		calculateNoOfGamesPlayed();
	}

	public void setWinCount(int winCount)
	{
		this.winCount = winCount;
		calculateWinRatio();
		calculateNoOfGamesPlayed();
	}
	
	public int getWinCount()
	{
		return this.winCount;
	}
	
	public void setLossCount(int lossCount)
	{
		this.lossCount = lossCount;
		calculateWinRatio();
		calculateNoOfGamesPlayed();
	}
	
	public int getLossCount()
	{
		return this.lossCount;
	}
	
	public void calculateNoOfGamesPlayed()
	{
		this.noOfGamesPlayed = this.winCount + this.lossCount;
	}
	
	public int getNoOfGamesPlayed()
	{
		return this.noOfGamesPlayed;
	}
	
	/**
	 * Calculates the winratio for a player
	 */
	public void calculateWinRatio()
	{
		double div = this.lossCount;
		if(div == 0)
		{
			div = 1;
		}
		
		this.winRatio = this.winCount / div;
	}
	
	public double getWinRatio()
	{
		return this.winRatio;
	}
	
	/**
	 * Gets the instance of the bean.
	 * @return
	 */
	public static HistoryBean getInstance() {
		if(_instance == null) {
			_instance  = new HistoryBean();
		}
		return _instance;
	}

	/**
	 * Gets all open games.
	 * @return
	 */
	public Score[] getScoresByUser(User user) {
		this.scores = HistoryAccessor.getCurrentInstance().getScores(user.getUserID());
		calculateWins();
		return this.scores.toArray(new Score[scores.size()]);
	}
	
	/**
	 * Checks whether or not there are any played games.
	 * @return whether or not there are any played games
	 */
	public boolean hasScores() {
		return this.scores.size() > 0;
	}
}
