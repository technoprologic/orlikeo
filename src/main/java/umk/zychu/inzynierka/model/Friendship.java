package umk.zychu.inzynierka.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;




@SuppressWarnings("serial")
@Entity 
@Table(name = "user_friends")
public class Friendship implements Serializable{

	@EmbeddedId
	FriendshipPK id;
	
    public FriendshipPK getId() {
        return id;
    }

    public void setId(FriendshipPK id) {
        this.id = id;
    }
    
    public void setId(long id1, long id2) {
        this.id.userId = id1;
        this.id.friendId = id2;
    }

	
    @ManyToOne 
    @JoinColumn(name="user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User friendRequester;
    
    
	@ManyToOne 
    @JoinColumn(name="friend_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User friendAccepter;
	
	
	@ManyToOne 
    @JoinColumn(name="action_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User actionUser;
    
    

    public Friendship() {
		super();
	}



	public User getActionUser() {
		return actionUser;
	}


	public void setActionUser(User actionUser) {
		this.actionUser = actionUser;
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

	
	
	
	@Column(name="action_user_id")
	long actionUserId;



	public long getActionUserId() {
		return actionUserId;
	}

	public void setActionUserId(long actionUserId) {
		this.actionUserId = actionUserId;
	}




	@Column(name = "state")
    int state;


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}
	
	
	
	
}




