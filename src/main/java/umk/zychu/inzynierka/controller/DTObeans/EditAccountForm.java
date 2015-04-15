package umk.zychu.inzynierka.controller.DTObeans;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EditAccountForm {

	public EditAccountForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EditAccountForm(String name, String surname, Date dateOfBirth,
			String position, int weight, int height, String foot) {
		super();
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	Date dateOfBirth;
	String position;
	int weight;
	int height;
	String foot;
	
}
