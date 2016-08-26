package umk.zychu.inzynierka.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity 
@Table(name = "friendship")
public class Friendship extends BaseEntity implements Serializable{

	@ManyToOne 
    @JoinColumn(name="requester_id", referencedColumnName = "id")
	@JsonIgnore
    private User friendRequester;
	
	@ManyToOne 
    @JoinColumn(name="target_id", referencedColumnName = "id")
	@JsonIgnore
    private User friendAccepter;


	
	@Column(name = "state")
    Integer state;
    
	public Friendship() {
		super();
	}
		
	public Friendship(User friendRequester, User friendAccepter, Integer stateId) {
		super();
/*		this.actionUser = friendRequester;
		if(friendRequester.getId() < friendAccepter.getId()){
			User tmpUser = friendRequester;
			friendRequester = friendAccepter;
			friendAccepter = tmpUser;
		}*/
		this.actionUser = friendRequester;
		this.friendRequester = friendRequester;
		this.friendAccepter = friendAccepter;
		this.state = stateId;
	}
	
	public User getFriendRequester() {
		return friendRequester;
	}

	public void setFriendRequester(User friendRequester) {
		this.friendRequester = friendRequester;
	}

	public User getFriendAccepter() {
		return friendAccepter;
	}

	public void setFriendAccepter(User friendAccepter) {
		this.friendAccepter = friendAccepter;
	}

	public User getActionUser() {
		return actionUser;
	}

	public void setActionUser(User actionUser) {
		this.actionUser = actionUser;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	//TODO Should be entities.
	public static final int INVITED=1, 
							ACCEPTED=2, 
							DECLINED=3, 
							BLOCKED = 4;

	public static class Builder {
		private User friendRequester;
		private User friendAccepter;
		//todo always friendRequester ?
		private Integer state;


		public Builder Builder(User requester, User targetUser, Integer stateId) {
			this.friendRequester = requester;
			this.friendAccepter = targetUser;
			this.state = stateId;
			return this;
		}

		public Builder setState(Integer stateId){
			this.state = stateId;
			return this;
		}

		public Friendship build(){

		}

	}
}