package org.bfh.jass.game.state;

import org.bfh.jass.game.*;

/**
 * State when waiting for new users to join.
 */
public class WaitingState extends GameState {

	public WaitingState(Game game) {
		super(game);
	}

	@Override
	protected void evaluateAction(GameAction action) {
		if(action instanceof JoinAction ||
				action instanceof StartGameAction) {
			action.doAction(game);
		}
	}

	@Override
	protected GameState testExitCondition() {
		if(!game.hasStarted() && game.isFull()) {
			game.start();
		}

		if(game.hasStarted()) {
			return new PlayingState(game);
		}
		return null;
	}

	@Override
	public void onExit() {
		// Choose the first picker!
		game.setPlayerTurn(0);
	}
}
