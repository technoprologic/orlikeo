package umk.zychu.inzynierka.repository;

import java.util.List;
import umk.zychu.inzynierka.model.User;

public interface UserDAO {
	User getUserByEmail(String email);
	User getUserById(int id);
	List<User> getUserFriends(String email);
	void saveUser(User user);
}
