package org.bfh.jass.game.state;

import org.bfh.jass.game.*;
import org.bfh.jass.user.User;

/**
 * Created by Simon on 2014/11/27.
 */
public class CreatingState extends GameState {
	private boolean created = false;

	public CreatingState(Game game, User user) {
		super(game);
	}

	@Override
	protected void evaluateAction(GameAction action) {
		if(action instanceof CreateAction) {
			action.doAction(game);
			created = true;
		}
	}

	@Override
	protected GameState testExitCondition() {
		if(created) {
			return new WaitingState(game);
		}

		return null;
	}
}