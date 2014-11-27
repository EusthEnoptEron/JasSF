package org.bfh.jass.game.state;

import org.bfh.jass.game.Game;
import org.bfh.jass.game.GameAction;
import org.bfh.jass.user.User;

/**
 * Created by Simon on 2014/11/27.
 */
public class CreateAction extends GameAction {
	private final int score;

	protected CreateAction(User user, int score) {
		super(user);
		this.score = score;
	}

	@Override
	public void doAction(Game game) {
		game.setScore(score);
	}
}
