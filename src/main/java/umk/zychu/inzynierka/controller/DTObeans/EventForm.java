package umk.zychu.inzynierka.controller.DTObeans;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EventForm {
	
	Integer eventId;
	Integer graphicId;
	Integer usersLimit;
	List<EventMember> eventFormMembers;
	
	EventForm(){
		super();
		eventFormMembers = new ArrayList<>();
	}
	
	public EventForm(Integer id, List<EventMember> users) {
		super();
		eventId = id;
		eventFormMembers = users;
		eventId = null;
		graphicId = null;
	}

	public Integer getEventId() {
		return eventId;
	}
	
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
	public List<EventMember> getEventFormMembers() {
		return eventFormMembers;
	}
	
	public void setEventFormMembers(List<EventMember> eventFormMembers) {
		this.eventFormMembers = eventFormMembers;
	}
	
	public void setGraphicId(Integer id){
		this.graphicId = id;
	}
	
	public Integer getGraphicId(){
		return graphicId;
	}
	
	public void setUsersLimit(Integer limit){
		this.usersLimit = limit;
	}
	
	public Integer getUsersLimit(){
		return usersLimit;
	}

	public void removeUnnecessaryEventMembers() {
		//Who's all who no acts anymore;
		Predicate<EventMember> notInRosters = em -> !em.getInvited() && !em.getAllowed();
		eventFormMembers.removeIf(notInRosters);
	}
}
