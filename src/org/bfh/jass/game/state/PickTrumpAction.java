package org.bfh.jass.game.state;

import org.bfh.jass.game.CardSuit;
import org.bfh.jass.game.Game;
import org.bfh.jass.game.GameAction;
import org.bfh.jass.user.User;

/**
 * Created by Simon on 2014/11/27.
 */
public class PickTrumpAction extends GameAction {
	CardSuit trump;
	protected PickTrumpAction(User user, CardSuit suit) {
		super(user);
		trump = suit;
	}

	@Override
	public void doAction(Game game) {
		game.setTrump(trump);
	}
}
