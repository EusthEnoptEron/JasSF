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
 * Created by Simon on 2014/12/04.
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

	public String getTitle() {
		if(game == null) return "";
		return game.getTitle();
	}
	public void setTitle(String title) {
		game.setTitle(title);
	}

	public boolean isCreator() {
		return game.getCreator() == user.getUser();
	}

	public boolean hasStarted() {
		return game != null && game.hasStarted();
	}

	public Card[] getCards() {
		return user.getUser().getCards();
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

	private Player fetchPlayer() {
		for(HumanPlayer player: game.getHumanPlayers()) {
			System.out.println("Compare: " + user.getUser().getUserID() + " with " + player.getUserId());
			if(player.getUserId() == user.getUser().getUserID()) {
				return player;
			}
		}
		System.out.println("User not found...");
		return null;
	}

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

	public boolean mustPickTrump() {
		return game.getRound().getState() == GameRound.GameRoundState.PICKING
				&& game.getRound().getCurrentPlayer() == player;
	}

	public String join(Game game) {
		// Leave current game
		if(game.isFull()) {
			// TODO: Message
			return "";
		} else {
			if(this.game != game)
				this.leave();
			this.game = game;
			this.player = this.game.addPlayer(user.getUser());
			return "lobby?faces-redirect=true";
		}
	}

	public void playCard(CardBean card) {
		game.getRound().playCard(player, card.getCard());
	}

	public PlayerBean getPlayerLeft() {
		return new PlayerBean(game.getNextPlayer(game.getNextPlayer(game.getNextPlayer(player))));
	}

	public PlayerBean getPlayerOpposite() {
		return new PlayerBean(game.getNextPlayer(game.getNextPlayer(player)));
	}
	public PlayerBean getPlayerRight() {
		return new PlayerBean(game.getNextPlayer(player));
	}
	public PlayerBean getPlayer() {
		return new PlayerBean(player);
	}

	public boolean isActing() {
		return player.isActing();
	}

	public void pickDiamonds() {
		game.getRound().pickTrump(player, CardSuit.DIAMONDS);
	}
	public void pickClubs() {
		game.getRound().pickTrump(player, CardSuit.CLUBS);
	}
	public void pickHearts() {
		game.getRound().pickTrump(player, CardSuit.HEARTS);
	}
	public void pickSpades() {
		game.getRound().pickTrump(player, CardSuit.SPADES);
	}


	public String getTrump() {
		CardSuit trump = game.getRound().getTrump();
		if(trump != null)
			return trump.name().toLowerCase();
		else return "";
	}

	public CardBean[] getPlayedCards() {
		Map<Player, Card> playedCards = game.getRound().getCardsOnTable();

		CardBean[] cards = new CardBean[playedCards.size()];

		int i = 0;
		for(Player player: playedCards.keySet()) {
			cards[i++] = new CardBean(player, playedCards.get(player));
		}
		return cards;
	}

	public void demandReaction() {
		game.getRound().demandReaction((HumanPlayer)player);
	}

	public boolean isOver() {
		return game.getState() == Game.GameState.CLOSING;
	}

	public String getWinnerTeam() {
		return game.getWinnerTeam().name();
	}

	public Player getWinner1() {
		return game.getPlayers(game.getWinnerTeam())[0];
	}
	public Player getWinner2() {
		return game.getPlayers(game.getWinnerTeam())[1];
	}

	public void leave() {
		if(game != null) {
			if(player != null) {
				game.removePlayer(player);
			}
		}
		game = null;
	}
	public boolean isAborted() {
		return game.getState() == Game.GameState.ABORTED;
	}

	public PlayerBean getPlayer0() {
		return new PlayerBean(game.getPlayers()[0]);
	}
	public PlayerBean getPlayer1() {
		return new PlayerBean(game.getPlayers()[1]);
	}
	public PlayerBean getPlayer2() {
		return new PlayerBean(game.getPlayers()[2]);
	}
	public PlayerBean getPlayer3() {
		return new PlayerBean(game.getPlayers()[3]);
	}

	public boolean isFree(int slot) {
		return game.isFree(slot);
	}

	public void takeSlot(int slot) {
		game.setPlayer(slot, player);
	}
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