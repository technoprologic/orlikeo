package umk.zychu.inzynierka.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name="event_state")
public class EventState extends BaseEntity{
	
	
	@Column(name="state")
	String state;
	
	@OneToMany( mappedBy = "state", cascade = CascadeType.ALL)
	List<Event> events;
	
	public void setState(String state){
		this.state = state;
	}
	
	
	public String getState(){
		return state;
	}
	
	public void setEvents(List<Event> events){
		this.events = events;
	}
	
	public List<Event> getEvents(){
		return events;
	}
}
