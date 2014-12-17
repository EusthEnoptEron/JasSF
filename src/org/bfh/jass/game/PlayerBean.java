package org.bfh.jass.game;

import java.util.Map;

/**
 * Created by Simon on 2014/12/11.
 */
public class PlayerBean {
	private Player player;
	public PlayerBean(Player player) {
		this.player = player;
	}

	public CardBean[] getCards() {
		Card[] realCards = player.getCards();
		CardBean[] cards = new CardBean[realCards.length];
		for(int i = 0; i < cards.length; i++) {
			cards[i] = new CardBean(player, realCards[i]);
		}
		return cards;
	}

	public boolean isActing() {
		return player.isActing();
	}

	public boolean canPlay() {
		return player.canPlay();
	}

	public CardBean getPlayedCard() {
		if(player == null) return null;
		Map<Player, Card> cards = player.getGame().getRound().getCardsOnTable();
		Card myCard = cards.get(player);
		if(myCard == null)
			return null;
		else
			return new CardBean(player, myCard);
	}

	public String getName() {
		return player.getName();
	}

	public int getScore() {
		return player.getGame().getScore(player.getTeam());
	}

	public boolean hasWonCards() {
		return player.getWonCards().length > 0;
	}

	public String getTeam() {
		return "team-" + player.getTeam().name().toLowerCase();
	}
}
