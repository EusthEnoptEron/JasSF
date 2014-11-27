package org.bfh.jass.game.state;

import org.bfh.jass.game.Game;
import org.bfh.jass.game.GameAction;
import org.bfh.jass.user.User;

/**
 * Created by Simon on 2014/11/27.
 */
public class JoinAction extends GameAction {
	private int position;
	public JoinAction(User user, int position) {
		super(user);

		this.position = position;
	}

	@Override
	public void doAction(Game game) {
		game.setPlayer(position, getUser());
	}
}