package umk.zychu.inzynierka.controller.DTObeans;

import java.util.List;

public class RegisterEventForm {
	
	protected long graphicId;
	
	protected int usersLimit;
	
	protected List<RegisterEventUser> userFriends;

	protected String organizerEmail;
	
	
	public String getOrganizerEmail() {
		return organizerEmail;
	}
	public void setOrganizerEmail(String organizerEmail) {
		this.organizerEmail = organizerEmail;
	}
	public void setGraphicId(long id){
		this.graphicId = id;
	}
	public long getGraphicId(){
		return graphicId;
	}
	
	public void setUsersLimit(int limit){
		this.usersLimit = limit;
	}
	public int getUsersLimit(){
		return usersLimit;
	}
	
	public void setUserFriends(List<RegisterEventUser> usersList){
		this.userFriends = usersList;
	}
	
	public List<RegisterEventUser> getUserFriends(){
		return this.userFriends;
	}
	
	public RegisterEventForm(){}

}
