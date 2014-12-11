package org.bfh.jass.game;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Simon on 2014/11/27.
 */
public class Card {
	private CardRank rank;
	private CardSuit suit;

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

	public CardRank getRank() {
		return rank;
	}

	public CardSuit getSuit() {
		return suit;
	}

	public int getValue(CardSuit trump) {
		if(trump == suit) {
			switch(rank) {
				case SIX: return 6;
				case SEVEN: return 7;
				case EIGHT: return 8;
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

	public int getSortOrder(CardSuit trump) {
		if(trump == suit) {
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

}
