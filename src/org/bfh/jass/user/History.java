package org.bfh.jass.user;

import org.apache.commons.lang.ArrayUtils;
import org.bfh.jass.user.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manage class that manages the games currently running.
 */
public class History {
	private static History _instance = null;
	private List<Score> scores;
	
	private double winRatio;
	private int winCount;
	private int lossCount;
	private int noOfGamesPlayed;

	private History() {
		scores = new ArrayList<Score>();
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
		this.noOfGamesPlayed = winCount + lossCount;
	}
	
	public int getNoOfGamesPlayed()
	{
		return this.noOfGamesPlayed;
	}
	
	public void calculateWinRatio()
	{
		double div = lossCount;
		if(div == 0)
		{
			div = 1;
		}
		
		winRatio = winCount / div;
	}
	
	public double getWinRatio()
	{
		return this.winRatio;
	}
	
	/**
	 * Gets the instance of the manager.
	 * @return
	 */
	public static History getInstance() {
		if(_instance == null) {
			_instance  = new History();
		}
		return _instance;
	}

	/**
	 * Gets all open games.
	 * @return
	 */
	public Score[] getScoresByUser(User user) {
		List<Score> list = HistoryAccessor.getCurrentInstance().getScores(user.getUserID());
		return list.toArray(new Score[list.size()]);
	}
}
