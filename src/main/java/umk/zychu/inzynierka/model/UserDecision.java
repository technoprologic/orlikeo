package umk.zychu.inzynierka.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_decision")
public class UserDecision extends BaseEntity {

	@Column(name = "decision")
	String userDecision;

	
	UserDecision(){
		super();
	}
	
	public UserDecision(String userDecision) {
		super();
		this.userDecision = userDecision;
	}

	public void setUserDecision(String decision) {
		this.userDecision = decision;
	}

	public String getUserDecision() {
		return this.userDecision;
	}

	@OneToMany( mappedBy = "decision", cascade = CascadeType.ALL)
	List<UserEvent> usersEvent;

	public void setUsersEvent(List<UserEvent> events) {
		this.usersEvent = events;
	}

	public List<UserEvent> getUsersEvent() {
		return this.usersEvent;
	}
}
