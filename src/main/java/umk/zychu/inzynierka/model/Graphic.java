package umk.zychu.inzynierka.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name="orlik_graphic")
public class Graphic extends BaseEntity{

	@Column(name="title")
	String title;
	
	@Column(name = "orlik_id")
	long orlikId;
	
	@Column(name="start_time")
	Date startTime;
	
	@Column(name="end_time")
	Date endTime;
	
	@Column(name="available")
	Boolean available;
	
	@ManyToOne
	@JoinColumn(name="orlik_id", referencedColumnName="id", insertable = false, updatable = false)
	Orlik orlik;
	
	@OneToMany(mappedBy="graphic")
	List<Event> events;
	
	public Graphic(){
	}
	
	public void setOrlikId(long id){
		orlikId = id;
	}
	
	public long getOrlikId(){
		return orlikId;
	}
		
	public void setTitle(String name){
		title = name;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setStartTime(Date date){
		startTime = date;
	}
	
	public Date getStartTime(){
		return startTime;
	}
	
	public void setEndTime(Date date){
		endTime = date;
	}
	
	public Date getEndTime(){
		return endTime;
	} 
	
	public void setAvailable(Boolean bool){
		available = bool;
	}
	
	public Boolean getAvailable(){
		return available;
	}
	
	public void setOrlik(Orlik orlik){
		this.orlik = orlik;
	}
	
	public Orlik getOrlik(){
		return orlik;
	}
	
	public void setEvent(List<Event> events){
		this.events = events;
	}
	
	public List<Event> getEvents(){
		return this.events;
	}
	
}
