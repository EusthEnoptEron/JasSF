package org.bfh.jass.game;

import org.apache.commons.lang.ArrayUtils;
import org.apache.myfaces.shared.util.HashMapUtils;
import org.bfh.jass.user.User;

import java.util.*;

/**
 * Created by Simon on 2014/11/27.
 */
public class GameRound {

	public GameRoundState getState() {
		return state;
	}

	public enum GameRoundState {
		PICKING,
		PLAYING
	}

	public static final int MAX_STEPS = 9;
	private Game game;

	public CardSuit getTrump() {
		return trump;
	}

	private CardSuit trump;

	// Linked to preserve insertion order.
	private LinkedHashMap<Player, Card> cards = new LinkedHashMap<Player, Card>();
	private int step = 0;

	GameRoundState state;

	private Player currentPlayer = null;

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

	public void pickTrump(Player player, CardSuit trump) {
		if(state == GameRoundState.PICKING) {
			if (player == getCurrentPlayer()) {
				this.trump = trump;
				state = GameRoundState.PLAYING;
			}
		}
	}

	public Card[] getPlayedCards() {
		Collection<Card> values = this.cards.values();
		return values.toArray(new Card[values.size()]);
	}

	public Map<Player, Card> getCardsOnTable() {
		return Collections.unmodifiableMap(cards);
	}

	public void playCard(Player player, Card card) {
		if (state == GameRoundState.PLAYING) {

			if (player == getCurrentPlayer()) {
				if (isPlayable(card)) {
					System.out.println(String.format("Player %d plays %s", game.getPlayerSlot(player), card));
					cards.put(player, card);
					player.removeCard(card);

					goToNextPlayer();
				}
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
			showdown();
		} else {
			System.out.println(String.format("Go to player %d", game.getPlayerSlot(game.getNextPlayer(currentPlayer))));

			setCurrentPlayer(game.getNextPlayer(currentPlayer));
		}
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
		if(player.getUserId() == game.getCreator().getUserID()) {
			currentPlayer.react();
		}
	}

	private void setCurrentPlayer(Player winningPlayer) {
		currentPlayer = winningPlayer;
//		winningPlayer.react();
	}

}
