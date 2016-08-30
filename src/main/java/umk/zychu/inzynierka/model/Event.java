package umk.zychu.inzynierka.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name="event")
public class Event extends BaseEntity {

	@Column(name = "players_limit")
	private Integer playersLimit;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserEvent> usersEvent;
	
	@Column(name="creation_date")
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "graphic_id", referencedColumnName = "id", nullable = true)
	private Graphic graphic;
	
	@ManyToOne
	@JoinColumn(name = "state_id", referencedColumnName = "id")
	private EventState state;
	
	@ManyToOne
	@JoinColumn(name="user_organizer", referencedColumnName="id")
	private User userOrganizer;

	public Integer getPlayersLimit() {
		return playersLimit;
	}
	
	public void setPlayersLimit(Integer playersLimit) {
		this.playersLimit = playersLimit;
	}

	public void setUsersEvent(List<UserEvent> usersEvent){
		this.usersEvent = usersEvent;
	}
	
	public List<UserEvent> getUsersEvent(){
		return this.usersEvent;
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
	
	public void setState(EventState state){
		this.state = state;
	}
	
	public EventState getState(){
		return state;
	}
	
	public User getUserOrganizer() {
		return userOrganizer;
	}
	
	public void setUserOrganizer(User userOrganizer) {
		this.userOrganizer = userOrganizer;
	}
	
	private Event(){
		super();
	}

	private Event(Builder builder){
		super();
		this.userOrganizer = builder.userOrganizer;
		this.graphic = builder.graphic;
		this.state = builder.state;
		this.playersLimit = builder.playersLimit;
		this.creationDate = new Date();
	}

	public static class Builder{
		private User userOrganizer;
		private Graphic graphic;
		private Integer playersLimit;
		private EventState state;

		public Builder(final User organizer, final Graphic graphic, final Integer playersLimit, EventState state){
			this.userOrganizer = organizer;
			this.graphic = graphic;
			this.playersLimit = playersLimit;
			this.state = state;
		}

		public Event build(){
			Event event = new Event(this);
			return event;
		}
	}
}
