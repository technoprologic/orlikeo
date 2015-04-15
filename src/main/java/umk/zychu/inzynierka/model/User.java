package umk.zychu.inzynierka.model;
 
 
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Entity
@Table(name="user")
public class User extends BaseEntity
{
	@Email
	@NotBlank
	@Size(min = 6)
	@Column(name = "email")
	String email;
	
    @NotBlank
    @Size(min = 6)
    @Column(name = "password")
    String password;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "surname")
	String surname;
	
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	Date dateOfBirth;
	
	@Column(name = "position")
	String position;
	
	@Column(name = "weight")
	int weight;
	
	@Column(name = "height")
	int  height;
	
	@Column(name = "foot")
	String foot;
	
	
	@OneToMany(mappedBy="friendRequester")
	private List<Friendship> friendRequesterList;
	
	@OneToMany(mappedBy="friendAccepter")
	private List<Friendship> friendAccepterList;
	
	
	@OneToMany(mappedBy = "actionUserId")
	private List<Friendship> actionUsersList;
	
	
	@ManyToMany(mappedBy = "orlikManagers")
    private List<Orlik> isOrliksManager;
	
	
	
	
	public List<Orlik> getIsOrliksManager() {
		return isOrliksManager;
	}

	public void setIsOrliksManager(List<Orlik> isOrliksManager) {
		this.isOrliksManager = isOrliksManager;
	}

	@OneToMany(mappedBy = "user")
	private List<UserEvent> userEvents;

	//getter and setter methods
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public void setDateOfBirth(Date dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}
	
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position){
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
	
	public void setUserEvents(List<UserEvent> userEvents){
		this.userEvents = userEvents;
	}
	
	public List<UserEvent> getUserEvents(){
		return this.userEvents;
	}
	
}
