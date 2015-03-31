package umk.zychu.inzynierka.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

/*	@Override
	public List<User> getUserFriends(String email) {
		return userDAO.getUserFriends(email);
	}*/

	@Override
	public List<User> getUserFriendships(User user) {
		// TODO Auto-generated method stub
		
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
/*		
		Friendship friendship = new Friendship();
		if(user.getId() > invitedUser.getId()){
			friendship.setFriendRequester(user);
			friendship.setFriendAccepter(invitedUser);

		}
		
		if(invitedUser.getId() > user.getId() ){
			friendship.setFriendRequester(invitedUser);
			friendship.setFriendAccepter(user);
		}
		
		friendship.setActionUser(user);
		friendship.setState(1);
		friendshipDAO.save(friendship);*/
		
		
		Friendship friendship = new Friendship();
		
		
		if(user.getId() > invitedUser.getId()){
			FriendshipPK pk = new FriendshipPK(user.getId(), invitedUser.getId());
			friendship.setId(pk);
		}
		
		if(invitedUser.getId() > user.getId() ){
			FriendshipPK pk = new FriendshipPK(invitedUser.getId(), user.getId());
			friendship.setId(pk);
		}
		

		friendship.setActionUserId(user.getId());
		friendship.setState(1);
		friendshipDAO.save(friendship);
	}

	
	
	
	
	
	
	@Override
	public void accceptUserInvitation(String email) {
		// TODO Auto-generated method stub
		User user = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		User userToAccept = getUser(email);
		
/*		if(user.getId() > userToAccept.getId())
			friendshipDAO.acceptFriendship(user.getId(), userToAccept.getId(), user.getId());
		else if(user.getId() < userToAccept.getId())
			friendshipDAO.acceptFriendship(userToAccept.getId(), user.getId(), user.getId());*/
		
		if(user.getId() > userToAccept.getId())
			friendshipDAO.acceptFriendship(user, userToAccept, user);
		else if(user.getId() < userToAccept.getId())
			friendshipDAO.acceptFriendship(userToAccept, user, user);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
