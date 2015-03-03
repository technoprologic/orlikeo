package umk.zychu.inzynierka.service;

import java.util.List;
import umk.zychu.inzynierka.controller.DTObeans.RegisterUserBean;
import umk.zychu.inzynierka.model.User;

public interface UserService {
	User getUser(String email);
	void saveUser(User user);
	boolean checkIfUserExists(String email);
	void createNewUser(RegisterUserBean registerUserBean);
	List<User> getUserFriends(String email);	
}
