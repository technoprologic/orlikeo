package umk.zychu.inzynierka.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.controller.DTObeans.ChangePasswordForm;
import umk.zychu.inzynierka.controller.DTObeans.EditAccountForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterUserBean;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.repository.FriendshipDaoRepository;
import umk.zychu.inzynierka.repository.UserDaoRepository;
import umk.zychu.inzynierka.repository.UserEventDaoRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDaoRepository userDAO;
	@Autowired 
	FriendshipDaoRepository friendshipDAO;
	@Autowired
	UserEventDaoRepository userEventDAO;

	@Override
	public User getUser(String email) {
		Optional<User> userOpt = userDAO.findAll().stream().filter(u -> u.getEmail().equals(email)).findFirst();
		if(userOpt.isPresent())
			return userOpt.get();
		else 
			return null;
	}
		
	@Override
	public User getUser(Integer id) {
		Optional<User> userOpt = userDAO.findAll().stream().filter(u -> u.getId() == id).findFirst();
		if(userOpt.isPresent())
			return userOpt.get();
		else 
			return null;
	}
	
	@Override
	public void saveUser(User user) {
		userDAO.save(user);
	}

	@Override
	public Boolean checkIfUserExists(String email) {
		return getUser(email) != null;
	}
	
	@Override
	public void createNewUser(RegisterUserBean registerUserBean) {
		User newUser = new User();
		newUser.setEmail(registerUserBean.getEmail());
		newUser.setPassword(registerUserBean.getPassword());
		saveUser(newUser);
	}
	
	@Override
	public Boolean checkOldPasswordCorrectness(String oldPassword) {
		User user = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user.getPassword().equals(oldPassword)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void changePassword(ChangePasswordForm form) {	
		User user = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user.getPassword().equals(form.getOldPassword())){
			user.setPassword(form.getNewPassword());
			userDAO.saveAndFlush(user);
		}
	}

	@Override
	public void updateUserDetails(EditAccountForm form) {
		User user = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		user.setName(form.getName());
		user.setSurname(form.getSurname());
		user.setDateOfBirth(form.getDateOfBirth());
		user.setPosition(form.getPosition());
		user.setWeight(form.getWeight());
		user.setHeight(form.getHeight());
		user.setFoot(form.getFoot());
		userDAO.saveAndFlush(user);
	}
}
