package org.bfh.jass.game.state;

import org.bfh.jass.game.Card;
import org.bfh.jass.game.Game;
import org.bfh.jass.game.GameAction;
import org.bfh.jass.user.User;

/**
 * Created by Simon on 2014/11/27.
 */
public class PlayCardAction extends GameAction {
	private Card card;
	protected PlayCardAction(User user, Card card) {
		super(user);

		this.card = card;
	}

	@Override
	public void doAction(Game game) {
		//game.playCard
	}
}
