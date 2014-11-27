package org.bfh.jass.game;

/**
 * Created by Simon on 2014/11/27.
 */
public abstract class GameState {
	protected Game game;

	protected GameState(Game game) {
		this.game = game;
	}

	public void onEnter() {}
	public void onExit() {}
	public void evaluate(GameAction action) {
		evaluateAction(action);

		GameState newState = testExitCondition();
		if(newState != null) {
			game.changeState(newState);
		}
	}

	protected abstract void evaluateAction(GameAction action);
	protected abstract GameState testExitCondition();
}
