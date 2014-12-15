package org.bfh.jass.game;

import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Created by Simon on 2014/12/04.
 */
public abstract class Player {
	private Game game;
	private Game.Team team;
	private Set<Card> cards = new HashSet<Card>();
	private Set<Card> wonCards = new HashSet<Card>();

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

	public void addWonCards(Card[] cards) {
		wonCards.addAll(Arrays.asList(cards));
	}

	public Card[] getWonCards() {
		return wonCards.toArray(new Card[wonCards.size()]);
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

	public boolean hasValidTrump() {
		CardSuit trump = game.getRound().getTrump();
		Card highestTrump = game.getRound().getHighestTrump();

		if(trump == null) return false;

		for(Card card : cards) {
			if(card.getSuit() == trump) {
				if(highestTrump == null) return true;
				else if(highestTrump.getSortOrder(trump) <
						card.getSortOrder(trump))
					return true;
			}
		}
		return false;
	}
	public boolean hasCardsOfSuit(CardSuit suit) {
		for(Card card : cards) {
			if(card.getSuit() == suit) return true;
		}
		return false;
	}

	public List<Card> getCardsOfSuit(CardSuit suit) {
		List<Card> legalCards = new ArrayList<Card>();
		for(Card card: cards) {
			if(card.getSuit() == suit) legalCards.add(card);
		}

		return legalCards;
	}

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

	public boolean canPlay() {
		return getLegalCards().length > 0;
	}

	public int getSlot() {
		return game.getPlayerSlot(this);
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public abstract void react();

	public boolean isActing() {
		return game.getRound().getCurrentPlayer() == this;
	}

	public boolean isPlayable(Card card) {
		return ArrayUtils.contains(getLegalCards(), card);
	}

	public abstract String getName();
}
