package org.bfh.jass.game;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Represents a card that can be played.
 * Is defined by a rank and a suit.
 */
public class Card {
	private CardRank rank;
	private CardSuit suit;
	private boolean played;

	public static final CardRank[] NON_TRUMP_ORDER = new CardRank[]{
			CardRank.SIX,
			CardRank.SEVEN,
			CardRank.EIGHT,
			CardRank.NINE,
			CardRank.TEN,
			CardRank.JOKER,
			CardRank.QUEEN,
			CardRank.KING,
			CardRank.ACE
	};

	public static final CardRank[] TRUMP_ORDER = new CardRank[]{
			CardRank.SIX,
			CardRank.SEVEN,
			CardRank.EIGHT,
			CardRank.TEN,
			CardRank.QUEEN,
			CardRank.KING,
			CardRank.ACE,
			CardRank.NINE,
			CardRank.JOKER
	};

	public Card(CardSuit suit, CardRank rank) {
		this.rank = rank;
		this.suit = suit;
	}

	/**
	 * Gets the rank of this card.
	 * @return
	 */
	public CardRank getRank() {
		return rank;
	}

	/**
	 * Gets the suit of this card.
	 * @return
	 */
	public CardSuit getSuit() {
		return suit;
	}

	/**
	 * Gets how much this card is worth based on the current trump suit.
	 * @param trump
	 * @return
	 */
	public int getValue(CardSuit trump) {
		if(trump == suit) {
			switch(rank) {
				case NINE: return 14;
				case TEN: return 10;
				case JOKER: return 20;
				case QUEEN: return 3;
				case KING: return 4;
				case ACE: return 11;
				default: return 0;
			}
		} else {
			switch(rank) {
				case TEN: return 10;
				case JOKER: return 2;
				case QUEEN: return 3;
				case KING: return 4;
				case ACE: return 11;
				default: return 0;
			}
		}
	}

	/**
	 * Gets the order in which one card "sticht" the other when no primary suit is defined.
	 * @param trump
	 * @return
	 */
	public int getSortOrder(CardSuit trump) {
		if(trump != suit) {
			switch(rank) {
				case SIX: return 0;
				case SEVEN: return 1;
				case EIGHT: return 2;
				case NINE: return 3;
				case TEN: return 4;
				case JOKER: return 5;
				case QUEEN: return 6;
				case KING: return 7;
				case ACE: return 8;
			}
		} else {
			switch(rank) {
				case SIX: return 9;
				case SEVEN: return 10;
				case EIGHT: return 11;
				case TEN: return 12;
				case QUEEN: return 13;
				case KING: return 14;
				case ACE: return 15;
				case NINE: return 16;
				case JOKER: return 17;
			}
		}

		return 0;
	}

	/**
	 * Gets the order in which one card "sticht" the other.
	 * @param trump
	 * @param primarySuit
	 * @return
	 */
	public int getSortOrder(CardSuit trump, CardSuit primarySuit) {
		if(trump != suit && primarySuit != suit) {
			switch(rank) {
				case SIX: return 0;
				case SEVEN: return 1;
				case EIGHT: return 2;
				case NINE: return 3;
				case TEN: return 4;
				case JOKER: return 5;
				case QUEEN: return 6;
				case KING: return 7;
				case ACE: return 8;
				default: return 0;
			}
		}
		return getSortOrder(trump) + 9;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Card card = (Card) o;

		if (rank != card.rank) return false;
		if (suit != card.suit) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = rank != null ? rank.hashCode() : 0;
		result = 31 * result + (suit != null ? suit.hashCode() : 0);
		return result;
	}

	@Override
		 public String toString() {
		String suitName = StringUtils.capitalize(suit.name().toLowerCase());
		String rankName = StringUtils.capitalize(rank.name().toLowerCase());

		return suitName + rankName;
	}

	public void setPlayed(boolean val) {
		played = val;
	}
	public boolean isPlayed() {
		return played;
	}


}
