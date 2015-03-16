package umk.zychu.inzynierka.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "user_event")
public class UserEvent extends BaseEntity implements Serializable {
	
	
	@Column(name = "user_id")
	long userId;
	
	
	@Column(name = "user_role")
	long roleId;
	
	
	@Column(name = "user_decision")
	long userDecision;
	
	
	@Column(name = "user_permission")
	boolean userPermission;
	
	@Column(name = "event_id")
	long eventId;
	
	
	@ManyToOne
	@JoinColumn(name = "user_role", referencedColumnName = "id", insertable = false, updatable = false)
	EventRole role;
	
	@ManyToOne
	@JoinColumn(name = "user_decision", referencedColumnName = "id", insertable = false, updatable = false)
	UserDecision decision;
	
	@ManyToOne
	@JoinColumn(name = "event_id", referencedColumnName = "id", insertable = false, updatable = false)
	Event event;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	User user;
	
	
	public void setEventId(long eventId){
		this.eventId = eventId;
	}
	
	public long getEventId(){
		return this.eventId;
	}
	
	public void setUserId(long userId){
		this.userId = userId;
	}
	
	public long getUserId(){
		return this.userId;
	}

	
	public void setEvent(Event event){
		this.event = event;
	}
	
	public Event getEvent(){
		return this.event;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public void setRoleId(long roleId){
		this.roleId = roleId;
	}
	
	public long getRoleId(){
		return roleId;
	}

	public void setUserDecision(long decision){
		this.userDecision = decision;
	}
	
	
	public long getUserDecision(){
		return this.userDecision;
	}
	
	public void setUserPermission(boolean permission){
		this.userPermission = permission;
	}
	
	public boolean getUserPermission(){
		return this.userPermission;
	}
	
	public void setRole(EventRole role){
		this.role = role;
	}
	
	public EventRole getRole(){
		return this.role;
	}
	
	public void setDecision(UserDecision decision){
		this.decision = decision;
	}
	
	public UserDecision getDecision(){
		return this.decision;
	}
}
