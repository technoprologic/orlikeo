package umk.zychu.inzynierka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.FriendshipPK;
import umk.zychu.inzynierka.model.User;

public interface FriendshipDaoRepository extends BaseRepository<Friendship, FriendshipPK> {
	
	
	
	public final static String COUNT_USERS_DEPENDENCIES = "SELECT COUNT(f.id.userId) FROM Friendship f WHERE f.id.userId = :requesterId AND f.id.friendId = :accepterId ";
	@Query(COUNT_USERS_DEPENDENCIES)
	long countUsersEmailDependencies(@Param("requesterId") long requesterId, @Param("accepterId") long accepterId);
	
		
	
	
	
	public static final String GET_USER_FRIENDHIPS = "SELECT f FROM Friendship f WHERE "
			+ "( f.friendRequester = :user OR f.friendAccepter = :user ) AND f.state = 2";
	@Query(GET_USER_FRIENDHIPS)
	List<Friendship> getUserFriendships(@Param("user") User user);
	
	

	
	
	public final static String GET_USER_FRIENDSHIP_REQUESTS = "SELECT f FROM Friendship f WHERE "
			+ "( f.friendRequester = :user OR f.friendAccepter = :user ) AND f.actionUser = :user AND f.state = 1";
	@Query(GET_USER_FRIENDSHIP_REQUESTS)
	List<Friendship> getPendedUserFriendshipRequests(@Param("user") User user);
	
	
	
	
	
	public final static String GET_USER_FRIENDSHIP_RECEIVED_REQUESTS = "SELECT f FROM Friendship f WHERE "
			+ "( f.friendRequester = :user OR f.friendAccepter = :user ) AND f.actionUser <> :user AND f.state = 1";
	@Query(GET_USER_FRIENDSHIP_RECEIVED_REQUESTS)
	List<Friendship> getReceivedUserFriendshipRequests(@Param("user") User user);
	
	

	
	public static final String ACCEPT_FRIEND_INVITATION = "UPDATE Friendship f SET f.state = 2, f.actionUser = :actionUser WHERE f.friendRequester= :user1 AND f.friendAccepter = :user2";
	@Modifying
	@Query(ACCEPT_FRIEND_INVITATION)
	void acceptFriendship(@Param("user1") User user1, @Param("user2") User user2, @Param("actionUser") User actionUser);
	

	
}
