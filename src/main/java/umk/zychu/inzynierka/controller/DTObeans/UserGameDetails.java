package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.UserEvent;

public class UserGameDetails {

	public UserGameDetails(long eventId, long stateId, UserEvent userEvent,
			String address, Graphic graphic, int playersLimit) {
		super();
		this.eventId = eventId;
		this.stateId = stateId;
		this.userEvent = userEvent;
		this.address = address;
		this.graphic = graphic;
		this.playersLimit = playersLimit;
	}

	
	long eventId;
	long stateId;
	UserEvent userEvent;
	String address;
	Graphic graphic;
	int willCome;
	int playersLimit;
	
	
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public long getStateId() {
		return stateId;
	}
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}
	public UserEvent getUserEvent() {
		return userEvent;
	}
	public void setUserEvent(UserEvent userEvent) {
		this.userEvent = userEvent;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Graphic getGraphic() {
		return graphic;
	}
	public void setGraphic(Graphic graphic) {
		this.graphic = graphic;
	}
	public int getWillCome() {
		return willCome;
	}
	public void setWillCome(int willCome) {
		this.willCome = willCome;
	}
	public int getPlayersLimit() {
		return playersLimit;
	}
	public void setPlayersLimit(int playersLimit) {
		this.playersLimit = playersLimit;
	}
	
}
