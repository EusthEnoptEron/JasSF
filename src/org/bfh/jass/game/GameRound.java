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

	// Linked to preserve insertion order.
	private LinkedHashMap<Player, Card> cards = new LinkedHashMap<Player, Card>();
	private int step = 1;

	GameRoundState state;

	public GameRound(int playerTurn) {
		this.playerTurn = playerTurn;
		state = GameRoundState.PICKING;

		// Distribute cards
		CardDeck deck = new CardDeck();
		for(Player player: game.getPlayers()) {
			player.setCards(deck.draw(9));
		}
	}

	public Player getCurrentPlayer() {
		return game.getPlayers()[playerTurn];
	}

	public void pickTrump(Player player, CardSuit trump) {
		if(state == GameRoundState.PICKING) {
			if (player == getCurrentPlayer()) {
				this.trump = trump;
				state = GameRoundState.PLAYING;
			}
		}
	}

	public Card[] getPlayedCards() {
		return (Card[]) this.cards.values().toArray();
	}

	public void playCard(Player player, Card card) {
		if(state == GameRoundState.PLAYING) {
			if (player == getCurrentPlayer()) {
				cards.put(player, card);
				player.removeCard(card);

				if (cards.size() == 4) {
					showdown();
				} else {
					goToNextPlayer();
				}
			}
		}
	}

	private void goToNextPlayer() {
		int nextTurn = (playerTurn + 1) % 4;
		setCurrentPlayer(game.getPlayers()[nextTurn]);
	}

	private void showdown() {
		Card winningCard = null;
		Player winningPlayer = null;
		int score = 0;

		// Select winner
		for(Player player: cards.keySet()) {
			Card card = cards.get(player);
			if(winningCard == null
					|| card.getSortOrder(trump) > winningCard.getSortOrder(trump)) {
				winningCard = card;
				winningPlayer = player;
			}
			score += card.getValue(trump);
		}

		// TODO: player.setWonCards
		game.addScore(winningPlayer.getTeam(), score);

		if(step < MAX_STEPS) {
			cards = new LinkedHashMap<Player, Card>();
			step++;

			setCurrentPlayer(winningPlayer);
		} else {
			// Match's over
			game.startNewRound();
		}
	}

	private void setCurrentPlayer(Player winningPlayer) {
		playerTurn = winningPlayer.getSlot();
		winningPlayer.react();
	}


}
