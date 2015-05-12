package umk.zychu.inzynierka.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;

@Entity
@Table(name = "calendar")
public class EventX implements Serializable{

    public EventX() {
		super();
	}


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="event_id")
    private Integer id;
	
	public EventX(DHXEv event) {
		// TODO Auto-generated constructor stub
		this.contacts = event.getText();
		this.notes = event.getText();
		
		this.start_date = event.getStart_date();
		this.end_date = event.getEnd_date();
	}

	public Integer getId() {
		return id;
	}

	public boolean isNew() {
		return id == null;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	

	@Column(name = "contacts")
	private String contacts;

	@Column(name = "notes")
	private String notes;

	@Column(name = "tablename")
	private String table;

	@Column(name = "start_date")
	Date start_date;

	@Column(name = "end_date")
	Date end_date;

	




	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}


	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	

	@Override
	public String toString() {
		return "EventX [id=" + id + ", contacts=" + contacts + ", notes="
				+ notes + ", table=" + table + ", start_date=" + start_date
				+ ", end_date=" + end_date + "]";
	}
}