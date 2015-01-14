/*
  Modification:
  add the functionalities about the recognition of the chief (only answer to one's boss)
  Author: Emmanuel Benoist
  Date:  Octobre 7, 2010
 */


package org.bfh.jass.user;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.faces.context.FacesContext;
import java.security.*;
import java.security.spec.*;
import javax.faces.event.ComponentSystemEvent;
import java.io.IOException;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private User user;

	private String name;

	private String password;

	private Date dateOfBirth;

	private String greeting;

	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}



	public String getName() { return name;}

	public void setName(String name) { this.name = name; }



	public String getPassword() { return password;}

	public void setPassword(String password) { this.password = password; }

	public Date getDateOfBirth() { return dateOfBirth;}

	public void setDateOfBirth(Date date) { this.dateOfBirth = date; }


	public String getGreeting() { return greeting;}

	public void setGreeting(String greeting) { this.greeting = greeting; }


	public String login(){
		user = UserAccessor.getCurrentInstance().getUser(name);
		System.out.println("Access to login");
		boolean pwCheck = false;
		
		if(user != null)
		{
			try
			{
				pwCheck = Encryptor.validatePassword(password, user.getPassword());
				
			}
			catch(NoSuchAlgorithmException | InvalidKeySpecException e)
			{
				e.printStackTrace();
			}
			if(pwCheck)
			{
				dateOfBirth = user.getDateOfBirth();
				System.out.println("login accepted");
				greeting="Welcome";
				return "overview?faces-redirect=true";
			}
		}
		System.out.println("login refused");
		greeting="Login impossible (wrong username or Password)";
		return "login?faces-redirect=true";
		//if (name!=null && password!=null && name.equals("Emmanuel") && password.equals("Emmanuel"))

	}
	public String register(){
		try{
			user = UserAccessor.getCurrentInstance().createNewUser(name, password, dateOfBirth);
			if(user == null){
				greeting="Impossible to register";
				System.out.println(greeting);
				return "register?faces-redirect=true";
			}

			greeting = "Register OK, proceed to login";
			System.out.println(greeting);
			user = null;
			return "login?faces-redirect=true";
		}
		catch(RuntimeException e){
			greeting="Impossible to register";
			System.out.println("Exception :"+greeting);
			return "register?faces-redirect=true";
		}

	}

	public String modify(){
		if(user != null){
			user.setUsername(name);
			try
			{
			user.setPassword(Encryptor.createHash(password));
			}
			catch(NoSuchAlgorithmException | InvalidKeySpecException e)
			{
				e.printStackTrace();
			}
			user.setDateOfBirth(dateOfBirth);
			user.commitChanges();
			return "overview?faces-redirect=true";
		}
		return "login?faces-redirect=true";
	}
	public String cancel(){
		if(user !=null){
			name = user.getUsername();
			password = user.getPassword();
			dateOfBirth = user.getDateOfBirth();
			return "overview?faces-redirect=true";
		}
		return "login?faces-redirect=true";
	}
	
	public void checkAuthorization(ComponentSystemEvent event)
	{
		if(!isLoggedIn())
		{
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("login.xhtml?faces-redirect=true");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public void checkAuthorization(ComponentSystemEvent event)
	{
		if(!isLoggedIn())
		{
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("login.xhtml?faces-redirect=true");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

	public boolean isLoggedIn() {
		return user != null;
	}

}
