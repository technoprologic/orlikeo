package umk.zychu.inzynierka.model;

import umk.zychu.inzynierka.converter.UserEventDecisionConverter;
import umk.zychu.inzynierka.converter.UserEventRoleConverter;
import umk.zychu.inzynierka.model.enums.EnumeratedEventRole;
import umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_event")
public class UserEvent extends BaseEntity implements Serializable, Comparable<UserEvent> {
	
	@Column(name = "user_permission")
	private boolean userPermission;

	@ManyToOne()
	@JoinColumn(name = "inviter_id", referencedColumnName = "id")
	private User inviter;


	@Column(name = "user_role")
	@Convert(converter = UserEventRoleConverter.class)
	private EnumeratedEventRole role;

	@Column(name = "user_decision")
	@Convert(converter = UserEventDecisionConverter.class)
	private EnumeratedUserEventDecision decision;

	@ManyToOne
	@JoinColumn(name = "event_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Event event;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	private UserEvent() {
		super();
	}
	private UserEvent(Builder builder) {
		super();
		this.user = builder.user;
		this.inviter = builder.inviter;
		this.role = builder.role;
		this.decision = builder.decision;
		this.userPermission = builder.userPermission;
		this.event = builder.event;
	}

	public User getInviter() {
		return inviter;
	}

	public void setInviter(User inviter) {
		this.inviter = inviter;
	}
	
	public void setEvent(Event event){
		this.event = event;
	}
	
	public Event getEvent(){
		return this.event;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public void setUserPermission(boolean permission){
		this.userPermission = permission;
	}
	
	public boolean getUserPermission(){
		return this.userPermission;
	}
	
	public void setRole(EnumeratedEventRole role){
		this.role = role;
	}
	
	public EnumeratedEventRole getRole(){
		return this.role;
	}
	
	public EnumeratedUserEventDecision getDecision(){
		return this.decision;
	}

	public void setDecision(EnumeratedUserEventDecision decision) {
		this.decision = decision;
	}

	@Override
	public int compareTo(UserEvent o) {
		// return 1 if o should be before this
		// return -1 if this should be before o
		// return 0 otherwise
		Graphic thisGraphic = this.getEvent().getGraphic();
		Graphic oGraphic = o.getEvent().getGraphic();
		if (thisGraphic == null || oGraphic == null) {
			if (thisGraphic == null && oGraphic == null)
				return 0;
			else if (thisGraphic == null)
				return -1;
			else
				return 1;
		}
		if (oGraphic.getStartTime()
				.before(thisGraphic.getStartTime()))
			return 1;
		else if (thisGraphic.getStartTime()
				.before(oGraphic.getStartTime()))
			return -1;
		else
			return 0;
	}


	public static class Builder{
		private User user;
		private User inviter;
		private Event event;
		private boolean userPermission;
		private EnumeratedEventRole role;
		private EnumeratedUserEventDecision decision;

		public Builder(
				final User user,
				final User inviter,
				final Event event,
				final EnumeratedEventRole eventRole,
				final EnumeratedUserEventDecision userDecision
				//Boolean permission,
				) {
			this.user = user;
			this.inviter = inviter;
			this.event = event;
			this.decision = userDecision;
			this.role = eventRole;
			this.userPermission = false;
		}

		public Builder setPermission(final boolean permitted){
			this.userPermission = permitted;
			return this;
		}

		public UserEvent build(){
			UserEvent userEvent = new UserEvent(this);
			return userEvent;
		}
	}
}
