package umk.zychu.inzynierka.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.controller.DTObeans.ChangePasswordForm;
import umk.zychu.inzynierka.controller.DTObeans.EditAccountForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterUserBean;
import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.FriendshipPK;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.repository.FriendshipDaoRepository;
import umk.zychu.inzynierka.repository.UserDaoRepository;



@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDaoRepository userDAO;
	
	@Autowired 
	FriendshipDaoRepository friendshipDAO;
	
	

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
	public List<User> getUserFriendships(User user) {		
		  List<User> friends  = new ArrayList<User>();
		  List<Friendship> friendships = friendshipDAO.getUserFriendships(user);
		  
		  Iterator<Friendship> iterator = friendships.iterator();
		  while (iterator.hasNext()) {
		     Friendship friendship = (Friendship) iterator.next();
		     
		     System.out.println("requester: " + friendship.getFriendRequester().getEmail() 
		    		 + " accepter: " + friendship.getFriendAccepter().getEmail() 
		    		 + " frId: " + friendship.getState());
 
		     if (friendship.getFriendRequester().getId() != user.getId()) {
		         friends.add(friendship.getFriendRequester());
		     } else {
		         friends.add(friendship.getFriendAccepter());
		     }
		  }

		  return friends;
	}
	
		

	@Override
	public boolean checkIfTheyHadContacted(User userRequest1, User userRequest2) {

		long id1 = userRequest1.getId();
		long id2 = userRequest2.getId();
		
		//DATABASE CONSTRAINTS(id1>id2)
		boolean needToBeFixed = ( id1 < id2) ? true : false;
		long counted = 0;
		
		if(!needToBeFixed){
			counted = friendshipDAO.countUsersEmailDependencies(id1, id2);
			System.out.println("requester Id: "  +id1 + " accepetrId: " + id2);
		}else{
			counted = friendshipDAO.countUsersEmailDependencies(id2, id1);
			System.out.println("requester Id: "  +id2 + " accepetrId: " + id1);
		}
		
		
		System.out.println("Counter " + counted);
		if(counted > 0){
			return true;
		}
		else{
			return false;
		}
		
	}

	
	
	@Override
	public List<User> getPendedUserFriendshipRequests(User user) {
		  List<User> friendsPendedRequests  = new ArrayList<User>();
		  List<Friendship> friendships = friendshipDAO.getPendedUserFriendshipRequests(user);

		  Iterator<Friendship> iterator = friendships.iterator();
		  while (iterator.hasNext()) {
		     Friendship friendship = (Friendship) iterator.next();

		     if (friendship.getFriendRequester().getId() != user.getId()) {
		    	 friendsPendedRequests.add(friendship.getFriendRequester());
		     } else {
		    	 friendsPendedRequests.add(friendship.getFriendAccepter());
		     }
		  }
		  return friendsPendedRequests;
	}

	
	
	@Override
	public List<User> getReceivedUserFriendshipRequests(User user) {
		  List<User> friendsPendedRequests  = new ArrayList<User>();
		  List<Friendship> friendships = friendshipDAO.getReceivedUserFriendshipRequests(user);

		  Iterator<Friendship> iterator = friendships.iterator();
		  while (iterator.hasNext()) {
		     Friendship friendship = (Friendship) iterator.next();

		     if (friendship.getFriendRequester().getId() != user.getId()) {
		    	 friendsPendedRequests.add(friendship.getFriendRequester());
		     } else {
		    	 friendsPendedRequests.add(friendship.getFriendAccepter());
		     }
		  }
		  return friendsPendedRequests;
	}

	
	
	@Override
	public void inviteUserToFriends(User user, User invitedUser) {
		try{
			Friendship friendship = new Friendship();
			friendship.setActionUserId(user.getId());
			
			if(friendshipDAO.countUsersEmailDependencies(user.getId(), invitedUser.getId()) > 0){
				friendshipDAO.retryInvitation(user, invitedUser);
			}else{
				if(user.getId() > invitedUser.getId()){
					FriendshipPK pk = new FriendshipPK(user.getId(), invitedUser.getId());
					friendship.setId(pk);
				}
				
				if(invitedUser.getId() > user.getId() ){
					FriendshipPK pk = new FriendshipPK(invitedUser.getId(), user.getId());
					friendship.setId(pk);
				}
				friendship.setState(1);
				friendshipDAO.save(friendship);
			}
		}catch(Exception e){
			System.out.println("BABOL " + e);
		}

	}

	
	
	@Override
	public void accceptUserInvitation(String email) {
		User user = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		User userToAccept = getUser(email);
	
		if(user.getId() > userToAccept.getId())
			friendshipDAO.acceptFriendship(user, userToAccept, user);
		else if(user.getId() < userToAccept.getId())
			friendshipDAO.acceptFriendship(userToAccept, user, user);
	}

	
		
	@Override
	public Friendship getUsersFriendship(User user, User userRequest) {
		Friendship friendship = friendshipDAO.getUsersFriendship(user, userRequest);
		return friendship;
	}

	
	
	@Override
	public boolean checkOldPasswordCorrectness(String oldPassword) {
		User user = userDAO.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		if(userDAO.checkOldPasswordCorrectness(user, oldPassword) > 0){
			return true;
		}else{
			return false;
		}
	}



	@Override
	public void changePassword(ChangePasswordForm form) {	
		User user = userDAO.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		userDAO.changeUserPassword(user, form.getOldPassword(), form.getNewPassword());
	}



	@Override
	public void updateUserDetails(EditAccountForm form) {
		// TODO Auto-generated method stub
		User user = userDAO.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		userDAO.updateUserDetails(user, form.getName(), form.getSurname(), form.getDateOfBirth(), form.getPosition(), form.getWeight(), form.getHeight(), form.getFoot());
		
	}



	@Override
	public void cancelFriendInvitation(String email) {
		
		User user = userDAO.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		User userRequest = userDAO.getUserByEmail(email);
		
		if(friendshipDAO.countUsersEmailDependencies(user.getId(), userRequest.getId()) > 0){
			Friendship f = friendshipDAO.getUsersFriendship(user, userRequest);
			System.out.println("STATE" + f.getState());
			friendshipDAO.delete(f);
		}
		
	}



	@Override
	public void blockUser(String email) {
		User user = userDAO.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		User userRequest = userDAO.getUserByEmail(email);
	
		if(friendshipDAO.countUsersEmailDependencies(user.getId(), userRequest.getId()) > 0){
			friendshipDAO.blockFriendship(user, userRequest);
		}else{
			this.inviteUserToFriends(user, userRequest);
			this.blockUser(email);
		}
		
	}



	@Override
	public void rejectUserFriendRequest(String email) {
		User user = userDAO.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		User userRequest = userDAO.getUserByEmail(email);
		friendshipDAO.rejectFriendship(user, userRequest);
	}

	
	
	
}
