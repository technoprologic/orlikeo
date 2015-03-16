package umk.zychu.inzynierka.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="event")
public class Event extends BaseEntity {

	
	@Column(name="graphic_id")
	long graphicId;
	

	@Column(name="state_id")
	long stateId;
	
	@Column(name="creation_date")
	Date creationDate;
	
	
	@Column(name = "players_limit")
	int playersLimit;
	
	public int getPlayersLimit() {
		return playersLimit;
	}


	public void setPlayersLimit(int playersLimit) {
		this.playersLimit = playersLimit;
	}

	@ManyToOne
	@JoinColumn(name = "graphic_id", referencedColumnName = "id", insertable = false, updatable = false)
	Graphic graphic;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	List<UserEvent> usersEvent;


	@ManyToOne
	@JoinColumn(name = "state_id", referencedColumnName = "id", insertable = false, updatable = false)
	EventState state;
	
	
	
	public void setGraphicId(long id){
		graphicId = id;
	}
	
	
	public long getGraphicId(){
		return graphicId;
	}
	
	public void setStateId(long id){
		stateId = id;
	}
	
	public long getStateId(){
		return stateId;
	}
	
	public void setCreationDate(Date date){
		creationDate = date;
	}
	
	public Date getCreationDate(){
		return creationDate;
	}
	
	
	public void setGraphic(Graphic graphic){
		this.graphic = graphic;
	}
	
	public Graphic getGraphic(){
		return this.graphic;
	}
	
	public void setUsersEvent(List<UserEvent> usersEvent){
		this.usersEvent = usersEvent;
	}
	
	public List<UserEvent> getUsersEvent(){
		return this.usersEvent;
	}
	
	public void setState(EventState state){
		this.state = state;
	}
	
	public EventState getState(){
		return state;
	}
	
	
	
	public Event(){
		
	}
	
	public Event(long graphicId, int playersLimit, long stateId ){
		this.graphicId = graphicId;
		this.stateId = stateId;
		this.playersLimit = playersLimit;
		this.creationDate = new Date();
	}
}
