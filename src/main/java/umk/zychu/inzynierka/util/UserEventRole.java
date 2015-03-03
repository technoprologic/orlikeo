package umk.zychu.inzynierka.util;

public enum UserEventRole {
	ORGANIZER(1),
	GUEST(2);
	
	private final int id;
	UserEventRole(int id){this.id = id;}
	public int getValue(){return id;}
}

