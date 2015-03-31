package umk.zychu.inzynierka.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Embeddable
public
class FriendshipPK implements Serializable {

	public FriendshipPK() {
		super();
	}

	public FriendshipPK(long userId, long friendId) {
		super();
		this.userId = userId;
		this.friendId = friendId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (friendId ^ (friendId >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FriendshipPK other = (FriendshipPK) obj;
		if (friendId != other.friendId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;

	@Column(name = "user_id", nullable = false)
	long userId;
	
	@Column(name = "friend_id", nullable = false)
	long friendId;

}
