package umk.zychu.inzynierka.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name="user")
public class User extends BaseEntity
{
	@Email
	@NotBlank
	@Size(min = 6)
	@Column(name = "email")
	private String email;
	
    @NotBlank
    @Size(min = 6)
    @Column(name = "password")
    private String password;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "surname")
	private String surname;
	
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "weight")
	private Integer weight;
	
	@Column(name = "height")
	private Integer  height;
	
	@Column(name = "foot")
	private String foot;

	@OneToMany(mappedBy="requester", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Friendship> friendRequesterList;

	@OneToMany(mappedBy="target", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Friendship> friendAccepterList;

	@OneToMany(mappedBy = "inviter", fetch = FetchType.EAGER)
	private List<UserEvent> usersEventsFriendsInvited;

	@OneToMany(mappedBy="userOrganizer", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Event> organizedEvents;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user", orphanRemoval = true)
	private List<UserEvent> userEvents;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<UserNotification> userNotifications;

	private User(){}

	private User(final Builder builder) {
		super();
		this.email = builder.email;
		this.password = builder.password;
		this.name = builder.name;
		this.surname = builder.surname;
		this.dateOfBirth = builder.dateOfBirth;
		this.position = builder.position;
		this.weight = builder.weight;
		this.height = builder.weight;
		this.foot = builder.foot;
		this.friendRequesterList = new ArrayList<>();
		this.friendAccepterList = new ArrayList<>();
		this.usersEventsFriendsInvited = new ArrayList<>();
		this.organizedEvents = new ArrayList<>();
		this.userEvents = new ArrayList<>();
		this.userNotifications = new ArrayList<>();
	}

	public List<UserNotification> getUserNotifications() {
		return userNotifications;
	}

	public List<Event> getOrganizedEvents() {
		return organizedEvents;
	}

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
	
	public Integer getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Integer getHeight() {
		return height;
	}
	
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public String getFoot() {
		return foot;
	}
	
	public void setFoot(String foot) {
		this.foot = foot;
	}
	
	public List<UserEvent> getUserEvents(){
		return this.userEvents;
	}

	public List<UserEvent> getUsersEventsFriendsInvited() {
		return usersEventsFriendsInvited;
	}

	public void setUsersEventsFriendsInvited(
			List<UserEvent> usersEventsFriendsInvited) {
		this.usersEventsFriendsInvited = usersEventsFriendsInvited;
	}
	public List<Friendship> getFriendRequesterList() {
		return friendRequesterList;
	}

	public List<Friendship> getFriendAccepterList() {
		return friendAccepterList;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public static class Builder{

		private String email;
		private String password;
		private String name;
		private String surname;
		private Date dateOfBirth;
		private String position;
		private Integer weight;
		private Integer height;
		private String foot;
		public Builder(final String email, final String password){
			this.email = email;
			this.password = password;
			this.name = "Imie";
			this.surname = "Nazwisko";
			this.dateOfBirth = new Date();
			this.position = "Nie ustawiono";
			this.weight = 0;
			this.height = 0;
			this.foot = "nie ustawiono";
		}

		public void setName(final String name){
			this.name = name;
		}

		public void setSurname(final String surname){
			this.surname = surname;
		}

		public void setDateOfBirth(final Date dateOfBirth){
			this.dateOfBirth = dateOfBirth;
		}

		public void setPosition(final String position){
			this.position = position;
		}

		public void setWeight(final Integer weight){
			this.weight = weight;
		}

		public void setHeight(final Integer height){
			this.height = height;
		}

		public void setFoot(final String foot){
			this.foot = foot;
		}

		public User build(){
			User user = new User(this);
			return user;
		}
	}

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
	@Override
	public String toString() {
		return "User [email=" + email + ", userEvents=" + userEvents
				+ "]";
	}
}
