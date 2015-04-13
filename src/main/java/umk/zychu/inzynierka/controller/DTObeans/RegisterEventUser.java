package umk.zychu.inzynierka.controller.DTObeans;

public class RegisterEventUser {
	
	public RegisterEventUser() {
		super();
		// TODO Auto-generated constructor stub
	}


	long userId;
	boolean allowed;
	boolean invited;
	String email;
	int age;
	String position;
	
	public void setUserId(long userId){
		this.userId = userId;
	}
	
	public long getUserId(){
		return userId;
	}
	
	public void setAllowed(boolean allowed){
		this.allowed = allowed;
	}
	
	public boolean getAllowed(){
		return this.allowed;
	}
	
	public void setInvited(boolean decision){
		this.invited = decision;
	}
	
	public boolean getInvited(){
		return this.invited;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	public int getAge(){
		return this.age;
	}
	
	
	public void setPosition(String position){
		this.position = position;
	}
	
	
	public String getPosition(){
		return this.position;
	}
	

	public RegisterEventUser(long userId, boolean hasRight, boolean decision, String email, int age, String position){
		this.userId = userId;
		this.allowed = hasRight;
		this.invited = decision;
		this.email = email;
		this.age = age;
		this.position = position;
	}
	
}
