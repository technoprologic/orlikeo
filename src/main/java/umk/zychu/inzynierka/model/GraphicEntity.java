package umk.zychu.inzynierka.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Null;


@SuppressWarnings("serial")
@Entity
@Table(name="orlik_graphic")
public class GraphicEntity extends BaseEntity{


	@ManyToOne(optional=false)
	@JoinColumn(name="orlik_id", referencedColumnName="id")
	Orlik orlik;
	
	@Column(name="title")
	String title;
	
	@Column(name="event_id", nullable = true)
	Integer eventId;
	
	@Column(name="start_time")
	Date startTime;
	
	@Column(name="end_time")
	Date endTime;
	
	@Column(name="available")
	Boolean available;
	
	
	public GraphicEntity(){
	}
	
	public void setOrlikId(Orlik orlikObj){
		orlik = orlikObj;
	}
	
	public Orlik getOrlikId(){
		return orlik;
	}
		
	public void setTitle(String name){
		title=name;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setEventId(Integer id){
		eventId = id;
	}
	
	public Integer getEventId(){
		return eventId;
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
}
