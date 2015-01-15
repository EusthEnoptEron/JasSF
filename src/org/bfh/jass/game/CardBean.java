package org.bfh.jass.game;

import org.apache.commons.lang.StringUtils;
import org.bfh.jass.user.LoginBean;

/**
 * GUI glue for cards
 */
public class CardBean {
	private Card card;
	private Player player;

	public CardBean(Player player, Card card) {
		this.card = card;
		this.player = player;
	}

	public boolean isPlayable() {
		return player.isActing() && player.isPlayable(card);
	}

	public String getResourcePath() {

		String suitName = StringUtils.capitalize(card.getSuit().name().toLowerCase());
		String rankName = StringUtils.capitalize(card.getRank().name().toLowerCase());

		return "cards/card" + suitName + rankName;

	}

	public Card getCard() {
		return card;
	}
}
