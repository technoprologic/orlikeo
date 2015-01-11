package umk.zychu.inzynierka.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="event")
public class Event extends BaseEntity {

	
	@Column(name="candidate_graphic_id")
	int graphicEventId;
	

	@Column(name="state_id")
	int stateId;
	
	@Column(name="creation_date")
	Date creationDate;
	
	
	public void setGraphicEventId(int id){
		graphicEventId = id;
	}
	
	
	public int getGraphicEventId(){
		return graphicEventId;
	}
	
	public void setStateId(int id){
		stateId = id;
	}
	
	public int getStateId(){
		return stateId;
	}
	
	public void setCreationDate(Date date){
		creationDate = date;
	}
	
	public Date getCreationDate(){
		return creationDate;
	}

}
