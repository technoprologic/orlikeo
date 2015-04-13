package umk.zychu.inzynierka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import umk.zychu.inzynierka.controller.DTObeans.EditAccountForm;
import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.User;

public interface UserDaoRepository extends BaseRepository<User, Long>{
	
	public static final String GET_USER_BY_EMAIL = "SELECT u FROM User u WHERE u.email = :email";
	@Query(GET_USER_BY_EMAIL)
	User getUserByEmail(@Param("email") String email);
	
	
	public static final String PASSWORD_CORRECTNESS = "SELECT COUNT(u.id) FROM User u WHERE u = :user AND u.password = :password";
	@Query(PASSWORD_CORRECTNESS)
	int checkOldPasswordCorrectness(@Param("user") User user, @Param("password") String oldPassword);
	
	
	public static final String PASSWORD_CHANGING = "UPDATE User u SET u.password = :newPassword WHERE u = :user AND u.password = :oldPassword";
	@Modifying
	@Query(PASSWORD_CHANGING)
	void changeUserPassword(@Param("user") User user, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);
	
	
	
	
	public static final String UPDATE_USER_DETAILS = "UPDATE User u SET u.name = :name, u.surname = :surname, u.age = :age, "
			 + "u.position = :position, u.weight = :weight, u.height = :height, u.foot = :foot WHERE u = :user";
	@Modifying
	@Query(UPDATE_USER_DETAILS)
	void updateUserDetails(@Param("user") User user, @Param("name") String name, @Param("surname") String surname, @Param("age") int age,
			@Param("position") String position, @Param("weight") int weight, @Param("height") int height, @Param("foot") String foot);
	
		
}
