package umk.zychu.inzynierka.controller.DTObeans;

import java.time.LocalDate;
import java.util.Date;

public class RegisterEventUser {
	
	public RegisterEventUser() {
		super();
		// TODO Auto-generated constructor stub
	}


	long userId;
	boolean allowed;
	boolean invited;
	String email;
	String inviter;
	Date dateOfBirth;
	String position;
	
	
	
	public String getInviter() {
		return inviter;
	}

	public void setInviter(String inviter) {
		this.inviter = inviter;
	}



	public void setUserId(long userId){
		this.userId = userId;
	}
	
	public long getUserId(){
		return userId;
	}
	
	public void setAllowed(boolean allowed){
		this.allowed = allowed;
	}
	
	public boolean getAllowed(){
		return this.allowed;
	}
	
	public void setInvited(boolean decision){
		this.invited = decision;
	}
	
	public boolean getInvited(){
		return this.invited;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setDateOfBirth(Date dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}
	
	public Date getDateOfBirth(){
		return this.dateOfBirth;
	}
	
	
	public void setPosition(String position){
		this.position = position;
	}
	
	
	public String getPosition(){
		return this.position;
	}
	

	public RegisterEventUser(long userId, boolean hasRight, boolean decision, String email, Date dateOfBirth, String position, String inviter){
		this.userId = userId;
		this.allowed = hasRight;
		this.invited = decision;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.position = position;
		this.inviter = inviter;
	}
	
}
