package umk.zychu.inzynierka.controller.DTObeans;

import java.util.List;

public class EditEventForm extends RegisterEventForm{

	public EditEventForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EditEventForm(long id, List<RegisterEventUser> users,
			String organizerEmail) {
		// TODO Auto-generated constructor stub
		this.eventId = id;
		this.userFriends = users;
		this.organizerEmail = organizerEmail;
	}

	long eventId;

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
	
}
