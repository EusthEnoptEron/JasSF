package org.bfh.jass.game;

import java.util.Random;

/**
 * Created by Simon on 2014/12/04.
 */
public class ComputerPlayer extends Player {
	private Random random;
	public ComputerPlayer(Game game) {
		super(game);

		random = new Random();
	}

	@Override
	public void react() {
		GameRound.GameRoundState state = getGame().getRound().state;
		if(state == GameRound.GameRoundState.PICKING) {
			pickTrump();
		} else if(state == GameRound.GameRoundState.PLAYING) {
			playCard();
		}
	}

	private void pickTrump() {
		CardSuit[] suits = CardSuit.values();

		getGame().getRound().pickTrump(this, suits[ random.nextInt(suits.length) ]);
	}

	private void playCard() {
		if(canPlay()) {
			Card[] legalCards = getLegalCards();

			getGame().getRound().playCard(this, legalCards[ random.nextInt(legalCards.length)]);
		} else {
			getGame().getRound().pass(this);
		}
	}
}
