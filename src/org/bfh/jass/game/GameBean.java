package org.bfh.jass.game;

import org.bfh.jass.user.LoginBean;
import org.bfh.jass.user.User;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created by Simon on 2014/12/04.
 */
@ManagedBean
@SessionScoped
public class GameBean implements Serializable {
	@ManagedProperty("#{loginBean}")
	private LoginBean user;

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

	public void checkForStart() {
		if(game.isFull()) {
			game.start();
			// TODO: redirect
		}
	}

	public String join(Game game) {
		this.game = game;

		return "lobby?faces-redirect=true";
	}
}