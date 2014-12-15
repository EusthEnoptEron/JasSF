package org.bfh.jass.game;

import java.util.Random;

/**
 * Created by Simon on 2014/12/04.
 */
public class ComputerPlayer extends Player {
	private Random random;
	private static final String[] NAMES = new String[] {
			"Rudi", "Hans", "Sepp", "Anton"
	};
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

	@Override
	public String getName() {
		return NAMES[getSlot()] + " (Computer)";
	}

	private void pickTrump() {
		CardSuit[] suits = CardSuit.values();
		getGame().getRound().pickTrump(this, suits[ random.nextInt(suits.length) ]);
	}

	private void playCard() {
		Card[] legalCards = getLegalCards();
		getGame().getRound().playCard(this, legalCards[ random.nextInt(legalCards.length)]);
	}
}
