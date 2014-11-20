package org.bfh.jass.user;

import org.bfh.jass.user.LoginBean;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.AbortProcessingException;
//import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.Locale;
import javax.servlet.http.HttpSession;

public class LanguageChangedActionEvent implements ActionListener {

	public void processAction(ActionEvent e)
			throws AbortProcessingException{
		System.out.println("-------------------------");
		System.out.println("Change language 2: "+e.getComponent().getId());
		String language = e.getComponent().getId();
		FacesContext context = FacesContext.getCurrentInstance();
		Locale locale = new Locale(language);
		context.getViewRoot().setLocale(locale);
		System.out.println("Changed to locale 2: "+locale);
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		LoginBean lb = (LoginBean)(session.getAttribute("loginBean"));
		lb.setLocale(language);
		//LanguageBean lb = (LanguageBean)(session.getAttribute("languageBean"));
		//lb.setUserLocale(new Locale(language));
		System.out.println("-------------------------");



	}

}