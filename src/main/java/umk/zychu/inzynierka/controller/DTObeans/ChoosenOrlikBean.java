package umk.zychu.inzynierka.controller.DTObeans;




public class ChoosenOrlikBean {

	long Id;
	long eventId;

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public ChoosenOrlikBean() {
		Id = -1;
	}

	public void setId(long id) {
		Id = id;
	}

	public long getId() {
		return Id;
	}
	
	
}
