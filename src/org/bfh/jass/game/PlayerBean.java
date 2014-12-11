package org.bfh.jass.game;

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
			cards[i] = new CardBean(player.getGame(), realCards[i]);
		}
		return cards;
	}

	public boolean isActing() {
		return player.isActing();
	}
}
