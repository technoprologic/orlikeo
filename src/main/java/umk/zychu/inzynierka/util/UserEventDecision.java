package umk.zychu.inzynierka.util;

public enum UserEventDecision {
	INVITED(1),
	ACCEPTED(2),
	REJECTED(3);
	
	private int id;
	UserEventDecision(int id){ this.id = id; }
	public int getValue(){ return this.id; }
}
