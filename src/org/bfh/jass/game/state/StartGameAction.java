package org.bfh.jass.game.state;

import org.bfh.jass.game.Game;
import org.bfh.jass.game.GameAction;
import org.bfh.jass.user.User;

/**
 * Forces the game to start NOW
 */
public class StartGameAction extends GameAction {
	protected StartGameAction(User user) {
		super(user);
	}

	@Override
	public void doAction(Game game) {
		game.start();
	}
}
