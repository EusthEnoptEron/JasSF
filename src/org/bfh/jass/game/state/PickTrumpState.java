package org.bfh.jass.game.state;

import org.bfh.jass.game.CardDeck;
import org.bfh.jass.game.Game;
import org.bfh.jass.game.GameAction;
import org.bfh.jass.game.GameState;
import org.bfh.jass.user.User;

import javax.smartcardio.CardException;

/**
 * Created by Simon on 2014/11/27.
 */
public class PickTrumpState extends GameState {
	private PickTrumpState(Game game) {
		super(game);
	}

	@Override
	public void onEnter() {
		// Distribute cards
		CardDeck deck = new CardDeck();
		for(User player: game.getPlayers()) {
			player.setCards(deck.draw(9));
		}
	}

	@Override
	protected void evaluateAction(GameAction action) {
		if(action instanceof PickTrumpAction
		&& action.getUserId() == game.getCurrentPlayer().getUserID()) {
			action.doAction(game);
		}
	}

	@Override
	protected GameState testExitCondition() {
		if(game.getTrump() != null) {
			return new PlayingState(game);
		}
		return null;
	}
}
