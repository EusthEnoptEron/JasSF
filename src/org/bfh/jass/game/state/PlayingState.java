package org.bfh.jass.game.state;

import org.bfh.jass.game.Game;
import org.bfh.jass.game.GameAction;
import org.bfh.jass.game.GameState;
import org.bfh.jass.user.User;

/**
 * Created by Simon on 2014/11/27.
 */
public class PlayingState extends GameState {

	public PlayingState(Game game) {
		super(game);
	}

	@Override
	protected void evaluateAction(GameAction action) {
		if(action instanceof PlayCardAction
				&& action.getUserId() == game.getCurrentPlayer().getUserID()) {
			action.doAction(game);
		}
	}

	@Override
	protected GameState testExitCondition() {

		return null;
	}
}
