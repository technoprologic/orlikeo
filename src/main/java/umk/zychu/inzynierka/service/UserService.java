package umk.zychu.inzynierka.service;

import java.util.List;

import org.springframework.stereotype.Service;

import umk.zychu.inzynierka.controller.DTObeans.RegisterUserBean;
import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.User;
@Service
public interface UserService {
	User getUser(String email);
	void saveUser(User user);
	boolean checkIfUserExists(String email);
	void createNewUser(RegisterUserBean registerUserBean);
/*	List<User> getUserFriends(String email);*/	
	
	List<User> getUserFriendships(User user);
	
	
	
	boolean checkIfTheyHadContacted(User userRequest1, User userRequest2);
	List<User> getPendedUserFriendshipRequests(User user);
	List<User> getReceivedUserFriendshipRequests(User user);
	void inviteUserToFriends(User user, User invitedUser);
	void accceptUserInvitation(String email);
}
