package umk.zychu.inzynierka.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;




@SuppressWarnings("serial")
@Entity
@Table(name="user_event_role")
public class EventRole extends BaseEntity {
	
	@Column(name="event_role")
	String eventRole;
	
	public void setEventRole(String role){
		this.eventRole = role;
	}
	
	public String getEventRole(){
		return this.eventRole;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL)
	List<UserEvent> usersEvent;
	
	public void setUserEvent(List<UserEvent> events){
		this.usersEvent = events;
	}
	
	public List<UserEvent> getUsersEvent(){
		return this.usersEvent;
	}
}
