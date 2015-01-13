package org.bfh.jass.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Helper class that represents a card deck.
 */
public class CardDeck {
	List<Card> cards;
	private Random random = new Random();

	/**
	 * Creates a new deck with all available cards.
	 */
	public CardDeck() {
		// Generate cards
		cards = new ArrayList<Card>();

		for(CardSuit suit: CardSuit.values()) {
			for(CardRank rank: CardRank.values()) {
				cards.add(new Card(suit, rank));
			}
		}
	}

	/**
	 * Shuffles cards. Not necessary, since draw() is already randomized.
	 * Adapted from http://bost.ocks.org/mike/shuffle/
	 * @deprecated use {@see draw()}
	 */
	public void shuffle() {
		Random random = new Random();

		Card t;
		int i;
		int m = cards.size();

		// While there remain elements to shuffle…
		while (m > 0) {
			// Pick a remaining element…
			i = (int) (random.nextDouble() * m--);

			// And swap it with the current element.
			t = cards.get(m);
			cards.set(m, cards.get(i));
			cards.set(i, t);
		}
	}

	/**
	 * Draws a card.
	 * @return card that was drawn
	 */
	public Card draw() {
		int i = random.nextInt(cards.size());
		Card card = cards.get(i);
		cards.remove(i);

		return card;
	}

	/**
	 * Draws a bunch of cards at once.
	 * @param count
	 * @return
	 */
	public Card[] draw(int count) {
		Card[] cards = new Card[count];

		for(int i = 0; i < count; i++) {
			cards[i] = draw();
		}
		return cards;
	}
}
