package org.bfh.jass.game;

import java.util.Random;

/**
 * A stupid computer player that can participate in a game in place of a human.
 */
public class ComputerPlayer extends Player {
	private Random random;
	private static final String[] NAMES = new String[] {
			"Rudi", "Hans", "Sepp", "Anton"
	};

	/**
	 * Creates a new computer player for a specific game.
	 * @param game
	 */
	public ComputerPlayer(Game game) {
		super(game);

		random = new Random();
	}

	/**
	 * React by either picking a trump or playing a card.
	 */
	@Override
	public void react() {
		GameRound.GameRoundState state = getGame().getRound().getState();
		if(state == GameRound.GameRoundState.PICKING) {
			pickTrump();
		} else if(state == GameRound.GameRoundState.PLAYING) {
			playCard();
		}
	}

	/**
	 * Gets the name of the player.
	 * @return
	 */
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
