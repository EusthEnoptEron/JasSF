package org.bfh.jass;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.AbortProcessingException;
//import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class LogoutActionEvent implements ActionListener {

	public void processAction(ActionEvent e)
			throws AbortProcessingException{
		System.out.println("-------------------------");
		System.out.println("Logout ");
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		LoginBean loginBean = (LoginBean)(session.getAttribute("loginBean"));
		loginBean.setUser(null);
		loginBean.setName(null);
		loginBean.setPassword(null);
		loginBean.setDateOfBirth(null);
		System.out.println("-------------------------");



	}

}