package org.bfh.jass.game;

import org.bfh.jass.user.LoginBean;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpSession;

/**
 * Event handler that removes the player from his current game.
 */
public class GameExitEvent implements ActionListener {

	@Override
	public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
		System.out.println("-------------------------");
		System.out.println("Leave Game");
		FacesContext context = FacesContext.getCurrentInstance();
		GameBean gameBean = context.getApplication().evaluateExpressionGet(context, "#{gameBean}", GameBean.class);
		gameBean.leave();
	}
}
