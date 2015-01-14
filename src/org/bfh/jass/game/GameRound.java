package org.bfh.jass.game;

import org.apache.commons.lang.ArrayUtils;
import org.apache.myfaces.shared.util.HashMapUtils;
import org.bfh.jass.user.User;

import java.util.*;

/**
 * Represents a game round of 9 steps that starts with picking a trump and ends when all cards have been played.
 * This instance will be controlled by beans.
 */
public class GameRound {

	/**
	 * Gets the current state of the round.
	 * @return
	 */
	public GameRoundState getState() {
		return state;
	}

	public enum GameRoundState {
		PICKING,
		PLAYING,
		OVER
	}

	private Game game;
	public CardSuit getTrump() {
		return trump;
	}
	private CardSuit trump;

	// Linked to preserve insertion order.
	private LinkedHashMap<Player, Card> cards = new LinkedHashMap<Player, Card>();
	private int step = 0;

	private GameRoundState state;

	private Player currentPlayer = null;

	/**
	 * Starts a new game round in a game with a player starting.
	 * @param game
	 * @param startingPlayer
	 */
	public GameRound(Game game, Player startingPlayer) {
		this.game = game;
		currentPlayer = startingPlayer;
		state = GameRoundState.PICKING;

		// Distribute cards
		CardDeck deck = new CardDeck();
		for(Player player: game.getPlayers()) {
			player.setCards(deck.draw(9));
		}
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Picks a trump.
	 * @param player the player that sent the command
	 * @param trump
	 */
	public void pickTrump(Player player, CardSuit trump) {
		if(state == GameRoundState.PICKING && player == currentPlayer) {
			this.trump = trump;
			state = GameRoundState.PLAYING;
		}
	}

	public Card[] getPlayedCards() {
		Collection<Card> values = this.cards.values();
		return values.toArray(new Card[values.size()]);
	}

	public Map<Player, Card> getCardsOnTable() {
		return Collections.unmodifiableMap(cards);
	}

	/**
	 * Plays a card.
	 * @param player the player that sent the command
	 * @param card
	 */
	public void playCard(Player player, Card card) {
		if (state == GameRoundState.PLAYING && player == currentPlayer) {
			if (player.isPlayable(card)) {
				System.out.println(String.format("Player %d plays %s", game.getPlayerSlot(player), card));
				cards.put(player, card);
				player.removeCard(card);

				goToNextPlayer();
			}
		}
	}

	/**
	 * Gets the highest trump currently on the table.
	 * @return
	 */
	public Card getHighestTrump() {
		if(trump == null) return null;
		Card highestTrump = null;

		for(Card card : cards.values()) {
			if(card.getSuit() == trump) {
				if(highestTrump == null ||
		        card.getSortOrder(trump) > highestTrump.getSortOrder(trump)) {
					highestTrump = card;
				}
			}
		}
		return highestTrump;
	}

	public CardSuit getPrimarySuit() {
		Card[] playedCards = cards.values().toArray(new Card[0]);
		if(playedCards.length > 0)
			return playedCards[0].getSuit();
		else
			return null;
	}

	public boolean isPlayable(Card card) {
		return true;
	}

	private void goToNextPlayer() {
		if(++step == 4) {
			// Showdown
			setCurrentPlayer(null);
		} else {
			System.out.println(String.format("Go to player %d", game.getPlayerSlot(game.getNextPlayer(currentPlayer))));
			setCurrentPlayer(game.getNextPlayer(currentPlayer));
		}
	}

	private void showdown() {
		Card winningCard = null;
		Player winningPlayer = null;
		CardSuit primarySuit = getPrimarySuit();
		int score = 0;

		// Select winner
		for(Player player: cards.keySet()) {
			Card card = cards.get(player);
			if(winningCard == null
					|| card.getSortOrder(trump, primarySuit) > winningCard.getSortOrder(trump, primarySuit)) {
				winningCard = card;
				winningPlayer = player;
			}
			score += card.getValue(trump);
		}

		System.out.println(String.format("Player %d wins showdown with %s", game.getPlayerSlot(winningPlayer), winningCard));

		// TODO: player.setWonCards
		winningPlayer.addWonCards(getPlayedCards());
		game.addScore(winningPlayer.getTeam(), score);

		if(!allCardsPlayed()) {
			System.out.println("Start next match...");
			cards = new LinkedHashMap<Player, Card>();

			step = 0;
			setCurrentPlayer(winningPlayer);
		} else {
			System.out.println("Start new round...");
			state = GameRoundState.OVER;
			// Match's over
			game.startNewRound();
		}
	}

	private boolean allCardsPlayed() {
		return game.getPlayers()[0].getCards().length == 0 &&
				game.getPlayers()[1].getCards().length == 0 &&
				game.getPlayers()[2].getCards().length == 0 &&
				game.getPlayers()[3].getCards().length == 0;
	}

	public void demandReaction(HumanPlayer player) {
		if(state == GameRoundState.OVER) return;

		if(player.getUserId() == game.getCreator().getUserID()) {
			if(currentPlayer == null)
				showdown();
			else
				currentPlayer.react();
		}
	}

	private void setCurrentPlayer(Player player) {
		currentPlayer = player;
//		player.react();
//		winningPlayer.react();
	}

}
