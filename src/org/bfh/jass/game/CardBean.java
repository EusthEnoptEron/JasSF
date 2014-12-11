package org.bfh.jass.game;

import org.apache.commons.lang.StringUtils;
import org.bfh.jass.user.LoginBean;

/**
 * Created by Simon on 2014/12/11.
 */
public class CardBean {
	private Card card;
	private Game game;

	public CardBean(Game game, Card card) {
		this.card = card;
		this.game = game;
	}

	public boolean isPlayable() {
		return game.getRound().isPlayable(card);
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
