package org.bfh.jass.game;

import org.apache.commons.lang.ArrayUtils;
import org.bfh.jass.user.User;

import java.util.*;

/**
 * A generic player that takes part in the game.
 */
public abstract class Player {
	private Game game;
	private Game.Team team;
	private Set<Card> cards = new HashSet<Card>();
	private Set<Card> wonCards = new HashSet<Card>();

	protected Player(Game game) {

		this.game = game;
	}

	/**
	 * Gets the game the player is playing.
	 * @return
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * GEts the team the player is playing in.
	 * @return
	 */
	public Game.Team getTeam() {
		return getSlot() % 2 == 0 ? Game.Team.EVEN : Game.Team.ODD;
	}

	/**
	 * Sets the card the player has.
	 * @param cards
	 */
	public void setCards(Card[] cards) {
		this.cards.clear();
		this.wonCards.clear();

		Collections.addAll(this.cards, cards);
	}

	/**
	 * Adds some cards to the list of cards the player has won in the current round.
	 * @param cards
	 */
	public void addWonCards(Card[] cards) {
		wonCards.addAll(Arrays.asList(cards));
	}

	/**
	 * Gets the cards the user has won in the current round.
	 * @return
	 */
	public Card[] getWonCards() {
		return wonCards.toArray(new Card[wonCards.size()]);
	}

	/**
	 * Gets the current hand of the player.
	 * @return
	 */
	public Card[] getCards() {
		return cards.toArray(new Card[cards.size()]);
	}

	/**
	 * Determines whether or not the player still has any cards.
	 * @param card
	 * @return
	 */
	public boolean hasCard(Card card) {
		return cards.contains(card);
	}

	/**
	 * Removes a card from the hand of the player.
	 * @param card
	 */
	public void removeCard(Card card) {
		cards.remove(card);
	}

	/**
	 * Checks if the user has any cards of a specific suit.
	 * @param suit
	 * @return
	 */
	public boolean hasCardsOfSuit(CardSuit suit) {
		for(Card card : cards) {
			if(card.getSuit() == suit) return true;
		}
		return false;
	}

	/**
	 * Gets a list of cards filtered by the suit.
	 * @param suit
	 * @return
	 */
	public List<Card> getCardsOfSuit(CardSuit suit) {
		List<Card> legalCards = new ArrayList<Card>();
		for(Card card: cards) {
			if(card.getSuit() == suit) legalCards.add(card);
		}

		return legalCards;
	}

	/**
	 * Gets a list of cards the player may play.
	 * @return
	 */
	public Card[] getLegalCards() {
		CardSuit trump = game.getRound().getTrump();
		CardSuit suit  = game.getRound().getPrimarySuit();
		List<Card> legalCards = new ArrayList<Card>();

		if(suit == null) {
			// We're first. We may play everything
			legalCards.addAll(cards);
		} else if(suit == trump) {
			// The first card played was a trump, so we may play trump
			// cards even if they're lower in value
			legalCards.addAll(getCardsOfSuit(suit));
			if(legalCards.size() == 0) {
				// If we don't have any trump cards, we may play anything
				legalCards.addAll(cards);
			}
		} else {
			Card highestTrump = game.getRound().getHighestTrump();
			boolean mustPlaySuit = hasCardsOfSuit(suit);

			for (Card card : cards) {
				if(!mustPlaySuit || card.getSuit() == suit || card.getSuit() == trump) {
					if (card.getSuit() == trump) {
						// We're dealing with a trump card... only allow when higher than all
						// trumps on the table
						if (highestTrump == null || card.getSortOrder(trump) > highestTrump.getSortOrder(trump)) {
							legalCards.add(card);
						}
					} else {
						// Otherwise we're fine
						legalCards.add(card);
					}
				}
			}
			if(legalCards.size() == 0) {
				// an empty list here means that we only have trumps left and neiher of them can win
				legalCards.addAll(cards);
			}
		}
		return legalCards.toArray(new Card[legalCards.size()]);
	}

	/**
	 * Determines whether the player can play or not.
	 * @return
	 */
	public boolean canPlay() {
		return getLegalCards().length > 0;
	}

	/**
	 * Gets the slot of the player.
	 * @return
	 */
	public int getSlot() {
		return game.getPlayerSlot(this);
	}

	/**
	 * Forces the player to react (only used by computer players)
	 */
	public abstract void react();

	/**
	 * Checks if it's the turn of this player.
	 * @return
	 */
	public boolean isActing() {
		return game.getRound().getCurrentPlayer() == this;
	}

	/**
	 * Checks if a specific card in the hand of the player is playable.
	 * @param card
	 * @return
	 */
	public boolean isPlayable(Card card) {
		return ArrayUtils.contains(getLegalCards(), card);
	}

	/**
	 * Gets the name of the player.
	 * @return
	 */
	public abstract String getName();

	/**
	 * Gets the user object associated to this player or NULL if it's a computer player.
	 * @return
	 */
	public User getUser() {
		return null;
	}
}
