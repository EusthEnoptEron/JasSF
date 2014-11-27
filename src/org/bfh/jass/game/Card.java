package org.bfh.jass.game;

/**
 * Created by Simon on 2014/11/27.
 */
public class Card {
	private CardRank rank;
	private CardSuit suit;

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
}
