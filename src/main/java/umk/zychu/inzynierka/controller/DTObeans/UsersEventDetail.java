package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserEvent;

public class UsersEventDetail {

	UserEvent userEvent;
	
	User user;
	
	public UsersEventDetail(UserEvent userEvent, User user) {
		
		this.userEvent = userEvent;
		this.user = user;
	}
	
	public void setUserEvent(UserEvent userEvent){
		this.userEvent = userEvent;
	}
	
	public UserEvent getuserEvent(){
		return this.userEvent;
	}
	
	public void setuser(User user){
		this.user = user;
	}
	
	public User getUser(){
		
		return this.user;
	}
}
