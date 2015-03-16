package umk.zychu.inzynierka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import umk.zychu.inzynierka.model.User;

public interface UserDaoRepository extends BaseRepository<User, Long>{
	
	public static final String GET_USER_BY_EMAIL = "SELECT u FROM User u WHERE u.email = :email";
	@Query(GET_USER_BY_EMAIL)
	User getUserByEmail(@Param("email") String email);
	
	
	public static final String GET_USER_FRIENDS = "SELECT u.hasFriendCollection FROM User u WHERE u.email = :email";
	@Query(GET_USER_FRIENDS)
	List<User> getUserFriends(@Param("email") String email);
}
