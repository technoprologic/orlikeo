package umk.zychu.inzynierka.controller.DTObeans;

import java.util.Date;

public class EventWindowBlock {
	//TODO Builder pattern
	public EventWindowBlock(String label, Integer displayOrder, Integer stateId, String address, String city,
			Date startTime, Date endTime, Integer limit, long goingToCome, long countedInSameState, Boolean incoming) {
		super();
		this.stateId = stateId;
		this.address = address;
		this.city = city;
		this.startTime = startTime;
		this.endTime = endTime;
		this.playersLimit = limit;
		this.goingToCome = goingToCome;
		this.countedInSameState = countedInSameState;
		this.label = label;
		this.displayOrder = displayOrder;
		this.incoming = incoming;
	}

	public Boolean getIncoming() {
		return incoming;
	}

	public void setIncoming(Boolean incoming) {
		this.incoming = incoming;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getPlayersLimit() {
		return playersLimit;
	}

	public void setPlayersLimit(Integer playersLimit) {
		this.playersLimit = playersLimit;
	}

	public EventWindowBlock() {
		super();
		// TODO Auto-generated constructor stub
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

	public long getGoingToCome() {
		return goingToCome;
	}

	public void setGoingToCome(long l) {
		this.goingToCome = l;
	}

	public Integer getLimit() {
		return playersLimit;
	}

	public void setLimit(Integer limit) {
		this.playersLimit = limit;
	}

	public long getCountedInSameState() {
		return countedInSameState;
	}

	public void setCountedInSameState(long counter) {
		this.countedInSameState = counter;
	}

	//TODO  Builder pattern
	private Integer stateId;
	private String address;
	private String city;
	private Date startTime;
	private Date endTime;
	private long goingToCome;
	private Integer playersLimit;
	private long countedInSameState;
	private String label;
	private Integer displayOrder;
	private Boolean incoming;


	@Override
	public String  toString(){
		return new StringBuffer("i: " + "id"
				+ " City:" + city
				+ " Address:" + address
				+ " WillCome:" + incoming
				+ " Limit:" + playersLimit
				+ " State:" + stateId
				+ " InTheSameStateCounter:" + "haveTheSameState"
				+ " displayOrder:" + displayOrder
				+ " label:" +  label
		).toString();

	}
}
