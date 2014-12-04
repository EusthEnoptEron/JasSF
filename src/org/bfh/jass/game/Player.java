package org.bfh.jass.game;

import org.bfh.jass.user.User;
import org.bfh.jass.user.UserAccessor;

import java.util.*;

/**
 * Created by Simon on 2014/12/04.
 */
public abstract class Player {
	private Game game;
	private Game.Team team;
	private Set<Card> cards = new HashSet<Card>();
	private int slot;

	protected Player(Game game) {

		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public Game.Team getTeam() {
		return team;
	}

	public void setTeam(Game.Team team) {
		this.team = team;
	}


	public void setCards(Card[] cards) {
		this.cards.clear();
		Collections.addAll(this.cards, cards);
	}

	public Card[] getCards() {
		return cards.toArray(new Card[cards.size()]);
	}

	public boolean hasCard(Card card) {
		return cards.contains(card);
	}

	public void removeCard(Card card) {
		cards.remove(card);
	}

	public Card[] getLegalCards() {
		List<Card> legalCards = new ArrayList<Card>();
		Card[] playedCards    = game.getRound().getPlayedCards();
		CardSuit firstSuit    = null;
		if(playedCards.length > 0)
			firstSuit = playedCards[0].getSuit();

		for(Card card: cards) {
			if(card.getSuit() == game.getTrump())
				legalCards.add(card);
			else if(firstSuit != null && card.getSuit() == firstSuit)
				legalCards.add(card);
		}

		return legalCards.toArray(new Card[legalCards.size()]);
	}

	public boolean canPlay() {
		return getLegalCards().length > 0;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public abstract void react();
}
