package umk.zychu.inzynierka.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dhtmlx.planner.DHXEv;


@SuppressWarnings("serial")
@Entity
@Table(name="orlik_graphic")
public class Graphic implements Serializable{

	@Override
	public String toString() {
		return "Graphic [title=" + title + ", orlikId=" + orlikId
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", available=" + available + ", orlik=" + orlik + ", events="
				+ events + ", id=" + id + "]";
	}


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
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="graphic")
	List<Event> events;
	
	public Graphic(){
	}
	
	public Graphic(DHXEv event) {
		// TODO Auto-generated constructor stub
		this.title = event.getText();
		this.orlikId  = -1;
		this.startTime = event.getStart_date();
		this.endTime = event.getEnd_date();
		this.available = false;
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
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	public Integer getId() {
		return id;
	}

	public boolean isNew() {
		return id == null;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
}
