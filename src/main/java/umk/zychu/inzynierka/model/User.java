package umk.zychu.inzynierka.model;
 
 
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

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
	
	@Column(name = "age")
	int age;
	
	@Column(name = "position")
	String position;
	
	@Column(name = "weight")
	int weight;
	
	@Column(name = "height")
	int  height;
	
	@Column(name = "foot")
	String foot;
	
	@JoinTable(name = "user_friend", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "friend_id", referencedColumnName = "id", nullable = false) })
	@ManyToMany
	private Collection<User> hasFriendCollection;
	
	
	@ManyToMany(mappedBy = "hasFriendCollection")
    private Collection<User> isFriendCollection;
	
	
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

	public int getAge() {
		return age;
	}
	
	public void setAge(int age){
		this.age = age;
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
	
	public void setHasFriendCollection(Collection<User> friendsOwnersCollection){
		hasFriendCollection = friendsOwnersCollection;
	}
	
	public Collection<User> getHasFriendCollection(){
		return hasFriendCollection;
	}
	
	public void setIsFriendCollection(Collection<User> friendsIsCollection){
		isFriendCollection = friendsIsCollection;
	}
	
	public Collection<User> getIsFriendCollection(){
		return isFriendCollection;
	}	
	
	public void setUserEvents(List<UserEvent> userEvents){
		this.userEvents = userEvents;
	}
	
	public List<UserEvent> getUserEvents(){
		return this.userEvents;
	}
}
