package org.bfh.jass.game;

import org.bfh.jass.user.LoginBean;
import org.bfh.jass.user.User;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * Session-scoped bean that handles the game interaction.
 */
@ManagedBean
@SessionScoped
public class GameBean implements Serializable {
	@ManagedProperty("#{loginBean}")
	private LoginBean user;
	private Player player;
	public LoginBean getUser() {
		return user;
	}

	public void setUser(LoginBean user) {
		System.out.println("------------------------------");
		System.out.println("set bean");
		System.out.println("------------------------------");

		this.user = user;
	}

	private Game game;

	@PostConstruct
	private void init() {
		// Get an instance of the current game the user is in.
		game = GameManager.getInstance().getCurrentGame(user.getUser());
	}

	/**
	 * Starts the creation of a new game.
	 * When one has already been created or the user is playing one,
	 * he will be referred there.
	 * @return
	 */
	public String startCreation() {
		if (game == null) {
			// Make a tentative game
			game = new Game(user.getUser());
			player = game.addPlayer(user.getUser());
			return "new_game?faces-redirect=true";
		} else {
			return "lobby?faces-redirect=true";
		}
	}

	/**
	 * Gets the title of the current game or an empty string.
	 * @return
	 */
	public String getTitle() {
		if(game == null) return "";
		return game.getTitle();
	}

	/**
	 * Sets the current game title.
	 * @param title
	 */
	public void setTitle(String title) {
		game.setTitle(title);
	}

	/**
	 * Tests if the player is the creator of the current game.
	 * @return
	 */
	public boolean isCreator() {
		if(game == null) return false;
		return game.getCreator() == user.getUser();
	}

	/**
	 * Checks if the player is playing a game and if it has started.
	 * @return
	 */
	public boolean hasStarted() {
		return game != null && game.hasStarted();
	}

	public int getMaxScore() {
		return game.getScore();
	}

	public void setMaxScore(int score) {
		game.setScore(score);
	}

	public String create() {
		GameManager.getInstance().addGame(game);

		return "lobby?faces-redirect=true";
	}

	public String start() {
		game.start();
		return "game?faces-redirect=true";
	}


	/**
	 * Checks if the should start, starts it and redirects to the game page.
	 */
	public void checkForStart() {
		if(game.isFull()) {
			game.start();
		}

		if(game.hasStarted()) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("game.xhtml?faces-redirect=true");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks if the current game is running.
	 * @TODO use hasStarted()
	 * @return
	 */
	public boolean isRunning() {
		return game != null &&
				game.getState() == Game.GameState.PLAYING;
	}

	/**
	 * Checks if the player is in charge of picking a trump card.
	 * @return
	 */
	public boolean mustPickTrump() {
		return game.getRound().getState() == GameRound.GameRoundState.PICKING
				&& game.getRound().getCurrentPlayer() == player;
	}

	/**
	 * Joins a new game.
	 * @param game
	 * @return
	 */
	public String join(Game game) {
		if(game.isFull()) {
			// TODO: Message
			return "";
		} else {
			// Leave current game
			if(this.game != game)
				this.leave();
			this.game = game;
			this.player = this.game.addPlayer(user.getUser());
			return "lobby?faces-redirect=true";
		}
	}

	/**
	 * Plays a card.
	 * @param card
	 */
	public void playCard(CardBean card) {
		game.getRound().playCard(player, card.getCard());
	}

	/**
	 * Gets the player that sits on the left-hand side
	 * @return
	 */
	public PlayerBean getPlayerLeft() {
		return new PlayerBean(game.getNextPlayer(game.getNextPlayer(game.getNextPlayer(player))));
	}

	/**
	 * Gets the player that sits on the opposite side
	 * @return
	 */
	public PlayerBean getPlayerOpposite() {
		return new PlayerBean(game.getNextPlayer(game.getNextPlayer(player)));
	}

	/**
	 * Gets the player that sits to the right
	 * @return
	 */
	public PlayerBean getPlayerRight() {
		return new PlayerBean(game.getNextPlayer(player));
	}

	/**
	 * Gets the player object of *this* user
	 * @return
	 */
	public PlayerBean getPlayer() {
		return new PlayerBean(player);
	}

	/**
	 * Checks if it's *this* user's turn
	 * @return
	 */
	public boolean isActing() {
		return player.isActing();
	}

	/**
	 * Picks diamonds for the trump suit.
	 */
	public void pickDiamonds() {
		if(game != null)
			game.getRound().pickTrump(player, CardSuit.DIAMONDS);
	}

	/**
	 * Picks clubs for the trump suit.
	 */
	public void pickClubs() {
		if(game != null)
			game.getRound().pickTrump(player, CardSuit.CLUBS);
	}

	/**
	 * Picks hearts for the trump suit.
	 */
	public void pickHearts() {
		if(game != null)
			game.getRound().pickTrump(player, CardSuit.HEARTS);
	}

	/**
	 * Picks spades for the trump suit.
	 */
	public void pickSpades() {
		if(game != null)
			game.getRound().pickTrump(player, CardSuit.SPADES);
	}


	/**
	 * Gets the current trump as string.
	 * @return
	 */
	public String getTrump() {
		if(game == null) return "";

		CardSuit trump = game.getRound().getTrump();
		if(trump != null)
			return trump.name().toLowerCase();
		else return "";
	}

	/**
	 * Gets the played cards of this user.
	 * @return
	 */
	public CardBean[] getPlayedCards() {
		Map<Player, Card> playedCards = game.getRound().getCardsOnTable();

		CardBean[] cards = new CardBean[playedCards.size()];

		int i = 0;
		for(Player player: playedCards.keySet()) {
			cards[i++] = new CardBean(player, playedCards.get(player));
		}
		return cards;
	}

	/**
	 * Demand reaction from CPUs (this handles the speed at which
	 * CPUs act)
	 */
	public void demandReaction() {
		if(game != null)
			game.getRound().demandReaction((HumanPlayer)player);
	}

	/**
	 * Checks if the game's over.
	 * @return
	 */
	public boolean isOver() {
		if(game == null) return true;
		return game.getState() == Game.GameState.CLOSING;
	}

	/**
	 * Gets the winner team.
	 * @return
	 */
	public String getWinnerTeam() {
		if(game == null) return "";
		if(game.getState() != Game.GameState.CLOSING)
			return "";

		return game.getWinnerTeam().name();
	}

	/**
	 * Gets the first winner of the winning team.
	 * @return
	 */
	public Player getWinner1() {
		return game.getPlayers(game.getWinnerTeam())[0];
	}

	public Player getWinner2() {
		return game.getPlayers(game.getWinnerTeam())[1];
	}

	/**
	 * Leaves the current game.
	 */
	public void leave() {
		if(game != null) {
			if(player != null) {
				game.removePlayer(player);
			}
		}
		game = null;
	}

	/**
	 * Checks if the game has been aborted.
	 * @return
	 */
	public boolean isAborted() {
		if(game == null) return false;
		return game.getState() == Game.GameState.ABORTED;
	}

	/**
	 * Gets the first player of team even.
	 * @return
	 */
	public PlayerBean getPlayer0() {
		return new PlayerBean(game.getPlayers()[0]);
	}

	/**
	 * Gets the first player of team odd.
	 * @return
	 */
	public PlayerBean getPlayer1() {
		return new PlayerBean(game.getPlayers()[1]);
	}

	/**
	 * Gets the second player of team even.
	 * @return
	 */
	public PlayerBean getPlayer2() {
		return new PlayerBean(game.getPlayers()[2]);
	}

	/**
	 * Gets the second player of team odd.
	 * @return
	 */
	public PlayerBean getPlayer3() {
		return new PlayerBean(game.getPlayers()[3]);
	}

	/**
	 * Checks if a certain slot in a game is free.
	 * @param slot
	 * @return
	 */
	public boolean isFree(int slot) {
		return game.isFree(slot);
	}

	/**
	 * Takes a slot.s
	 * @param slot
	 */
	public void takeSlot(int slot) {
		if(isFree(slot)) {
			game.setPlayer(slot, player);
		}
	}

	/**
	 * Checks the game should abort.
	 * @param event
	 * @return
	 */
	public boolean checkForAbort(ComponentSystemEvent event) {
		if(game == null || game.getState().ordinal() < Game.GameState.PLAYING.ordinal()) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("overview.xhtml?faces-redirect=true");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;

		}
		return true;
	}
}