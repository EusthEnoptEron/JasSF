package org.bfh.jass.user;

import org.bfh.jass.User;
import org.bfh.jass.UserAccessor;

import java.util.Date;

public class Tester{
    public static void main(String[] args){
	User bie1 = UserAccessor.getCurrentInstance().getUser("bie1");
	if(bie1 == null){
	    System.out.println("bie1 = null");

	}
	System.out.println("Username = "+bie1.getUsername());	
	User bie2 = UserAccessor.getCurrentInstance().getUser("bie1");
	if(bie2 == null){
	    System.out.println("bie2 = null");

	}
	System.out.println("Username = "+bie2.getUsername());
	
	System.out.println("Change Password of BIE1");
	bie1.setPassword("xxx2");

	System.out.println("pwd bie2 = "+bie2.getPassword());
	bie1.commitChanges();

	User bie3 = UserAccessor.getCurrentInstance().createNewUser("bie5","test3",new Date("2010/01/03"));

	User bie4 = UserAccessor.getCurrentInstance().getUser("bie5");
	System.out.println("Username = "+bie4.getUsername());	
	

    }


}