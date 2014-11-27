package org.bfh.jass.game;

import org.bfh.jass.user.User;

import java.util.*;

/**
 * Created by Simon on 2014/11/27.
 */
public class GameRound {
	public enum GameRoundState {
		PICKING,
		PLAYING
	}

	public static final int MAX_STEPS = 9;

	private int playerTurn = 0;
	private Game game;
	private CardSuit trump;
	private Dictionary<Integer, Card> cards = new Hashtable<Integer, Card>();
	private int step = 1;

	GameRoundState state;

	public GameRound(int playerTurn) {
		this.playerTurn = playerTurn;
		state = GameRoundState.PICKING;

		// Distribute cards
		CardDeck deck = new CardDeck();
		for(User player: game.getPlayers()) {
			player.setCards(deck.draw(9));
		}
	}

	public User getCurrentPlayer() {
		return game.getPlayers()[playerTurn];
	}

	public void pickTrump(User user, CardSuit trump) {
		if(state == GameRoundState.PICKING) {
			if (getCurrentPlayer().getUserID() == user.getUserID()) {
				this.trump = trump;
				state = GameRoundState.PLAYING;
			}
		}
	}
	public void playCard(User user, Card card) {
		if(state == GameRoundState.PLAYING) {
			if (getCurrentPlayer().getUserID() == user.getUserID()) {
				cards.put(playerTurn, card);
				user.removeCard(card);

				if (cards.size() == 4) {
					showdown();
				} else {
					playerTurn = (playerTurn + 1) % 4;
				}
			}
		}
	}

	private void showdown() {
		Card winningCard = null;
		int winningPlayer = 0;
		int score = 0;

		// Select winner
		Enumeration<Integer> keys = cards.keys();
		while(keys.hasMoreElements()) {
			int player = keys.nextElement();
			Card card = cards.get(player);
			if(winningCard == null
					|| card.getSortOrder(trump) > winningCard.getSortOrder(trump)) {
				winningCard = card;
				winningPlayer = player;
			}
			score += card.getValue(trump);
		}

		// TODO: player.setWonCards
		game.addScore(game.getPlayers()[winningPlayer].getTeam(), score);

		if(step < MAX_STEPS) {
			playerTurn = winningPlayer;
			cards = new Hashtable();
			step++;
		} else {
			// Match's over
			game.startNewRound();
		}
	}


}
