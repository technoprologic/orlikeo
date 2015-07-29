package umk.zychu.inzynierka.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity 
@Table(name = "friendship")
public class Friendship extends BaseEntity implements Serializable{

	@ManyToOne 
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User friendRequester;
	
	@ManyToOne 
    @JoinColumn(name="friend_id", referencedColumnName = "id")
    private User friendAccepter;
	
	
	@ManyToOne 
    @JoinColumn(name="action_user_id", referencedColumnName = "id")
    private User actionUser;
	
	@Column(name = "state")
    Integer state;
    
	public Friendship() {
		super();
	}
		
	public Friendship(User friendRequester, User friendAccepter) {
		super();
		this.actionUser = friendRequester;
		if(friendRequester.getId() < friendAccepter.getId()){
			User tmpUser = friendRequester;
			friendRequester = friendAccepter;
			friendAccepter = tmpUser;
		}
		this.friendRequester = friendRequester;
		this.friendAccepter = friendAccepter;
		this.state = 1;
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
		
	public static final int INVITED=1, 
							ACCEPTED=2, 
							DECLINED=3, 
							BLOCKED = 4;
}