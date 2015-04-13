package umk.zychu.inzynierka.controller.DTObeans;

public class EditAccountForm {

	public EditAccountForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EditAccountForm(String name, String surname, int age,
			String position, int weight, int height, String foot) {
		super();
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.position = position;
		this.weight = weight;
		this.height = height;
		this.foot = foot;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getFoot() {
		return foot;
	}
	public void setFoot(String foot) {
		this.foot = foot;
	}
	String name;
	String surname;
	int age;
	String position;
	int weight;
	int height;
	String foot;
	
}
