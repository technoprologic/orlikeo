package umk.zychu.inzynierka.controller.DTObeans;

import java.util.List;

public class RegisterEventForm {
	
	private long graphicId;
	
	private int usersLimit;
	
	private List<RegisterEventUser> userFriends;

	
	
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
