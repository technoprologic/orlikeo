package umk.zychu.inzynierka.controller.DTObeans;

import java.util.Date;

public class EventWindowBlock {


	long eventId;
	long stateId;
	String address;
	String city;
	Date startTime;
	Date endTime;
	int goingToCome;
	int playersLimit;
	int countedInSameState;
	
	
	
	public long getStateId() {
		return stateId;
	}



	public void setStateId(long stateId) {
		this.stateId = stateId;
	}



	public int getPlayersLimit() {
		return playersLimit;
	}



	public void setPlayersLimit(int playersLimit) {
		this.playersLimit = playersLimit;
	}

	
	
	public EventWindowBlock(long eventId, long stateId, String address, String city,
			Date startTime, Date endTime, int limit) {
		super();
		this.eventId = eventId;
		this.stateId = stateId;
		this.address = address;
		this.city = city;
		this.startTime = startTime;
		this.endTime = endTime;
		this.playersLimit = limit;
		this.goingToCome = 0;
		this.countedInSameState = 0;
	}
	
	
	
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getGoingToCome() {
		return goingToCome;
	}
	public void setGoingToCome(int goingToCome) {
		this.goingToCome = goingToCome;
	}
	public int getLimit() {
		return playersLimit;
	}
	public void setLimit(int limit) {
		this.playersLimit = limit;
	}
	public int getCountedInSameState() {
		return countedInSameState;
	}
	public void setCountedInSameState(int countedInSameState) {
		this.countedInSameState = countedInSameState;
	}
	
	
}
