package umk.zychu.inzynierka.repository;

import umk.zychu.inzynierka.model.User;

public interface UserDAO {
	User getUserByEmail(String email);
	User getUserById(int id);
	void saveUser(User user);
}
