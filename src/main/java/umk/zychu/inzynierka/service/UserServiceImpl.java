package umk.zychu.inzynierka.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.controller.DTObeans.RegisterUserBean;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.repository.UserDaoRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDaoRepository userDAO;
	
	@Override
	public User getUser(String email) {
		return userDAO.getUserByEmail(email);
	}
	
	@Override
	public void saveUser(User user) {
		userDAO.save(user);
	}

	@Override
	public boolean checkIfUserExists(String email) {
		
		return userDAO.getUserByEmail(email)!=null;
	}

	@Override
	public void createNewUser(RegisterUserBean registerUserBean) {
		User newUser = new User();
		newUser.setEmail(registerUserBean.getEmail());
		newUser.setPassword(registerUserBean.getPassword());
		saveUser(newUser);
	}

	@Override
	public List<User> getUserFriends(String email) {
		return userDAO.getUserFriends(email);
	}
}
