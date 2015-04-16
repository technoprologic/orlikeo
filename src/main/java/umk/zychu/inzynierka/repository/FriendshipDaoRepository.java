package umk.zychu.inzynierka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.FriendshipPK;
import umk.zychu.inzynierka.model.User;

public interface FriendshipDaoRepository extends BaseRepository<Friendship, FriendshipPK> {
	
	
	
	public final static String COUNT_USERS_DEPENDENCIES = "SELECT COUNT(f.id.userId) FROM Friendship f WHERE "
			+ "( f.friendRequester.id = :id1 AND f.friendAccepter.id = :id2 ) OR ( f.friendRequester.id = :id2 AND f.friendAccepter.id = :id1 )";
	@Query(COUNT_USERS_DEPENDENCIES)
	long countUsersEmailDependencies(@Param("id1") long requesterId, @Param("id2") long accepterId);
	
		
	
	
	
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
	
	

	
	public static final String ACCEPT_FRIEND_INVITATION = "UPDATE Friendship f SET f.state = 2, f.actionUser = :actionUser WHERE "
			+ "f.friendRequester= :user1 AND f.friendAccepter = :user2";
	@Modifying
	@Query(ACCEPT_FRIEND_INVITATION)
	void acceptFriendship(@Param("user1") User user1, @Param("user2") User user2, @Param("actionUser") User actionUser);
	
	
	
	
	public static final String USERS_FRIENDSHIP= "SELECT f FROM Friendship f WHERE "
			+ "( f.friendRequester = :user1 AND f.friendAccepter = :user2 ) OR ( f.friendRequester = :user2 AND f.friendAccepter = :user1 )";
	@Query(USERS_FRIENDSHIP)
	Friendship getUsersFriendship(@Param("user1") User user, @Param("user2") User userRequest);
	
	
	public static final String BLOCK_USER = "UPDATE Friendship f SET f.state = 4, f.actionUser = :user1 WHERE "
			+ "( f.friendRequester = :user1 AND f.friendAccepter = :user2 ) OR ( f.friendRequester = :user2 AND f.friendAccepter = :user1 )";
	@Modifying
	@Query(BLOCK_USER)
	void blockFriendship(@Param("user1") User user, @Param("user2") User userRequest);
	
	
	
	public static final String REJECT_FRIEND_INVITATION = "UPDATE Friendship f SET f.state = 3, f.actionUser = :user1 WHERE "
			+ "( f.friendRequester = :user1 AND f.friendAccepter = :user2 ) OR ( f.friendRequester = :user2 AND f.friendAccepter = :user1 )";
	@Modifying
	@Query(REJECT_FRIEND_INVITATION)
	void rejectFriendship(@Param("user1") User user, @Param("user2") User userRequest);
	
	
	
	public static final String RETRY_INVITATION = "UPDATE Friendship f SET f.state = 1, f.actionUser = :user1 WHERE "
			+ "( f.friendRequester = :user1 AND f.friendAccepter = :user2 ) OR ( f.friendRequester = :user2 AND f.friendAccepter = :user1 )";
	@Modifying
	@Query(RETRY_INVITATION)
	void retryInvitation(@Param("user1") User user, @Param("user2") User userRequest);
	

	
	
	
	
	
	
}
