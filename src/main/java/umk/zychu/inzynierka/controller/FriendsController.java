package umk.zychu.inzynierka.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.servlet.ModelAndView;

import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.UserService;


@Controller
@RequestMapping("/friends")
public class FriendsController {

	@Autowired
	UserService userService;
	


	
	
	@RequestMapping(method = RequestMethod.GET)
	public String getFriends(ModelMap model) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		
		List<User> friends = userService.getUserFriendships(user); 
		model.addAttribute("friends", friends);
		
		List<User> friendsPendedRequests = userService.getPendedUserFriendshipRequests(user);
		model.addAttribute("friendsPendedRequests", friendsPendedRequests);
		
		List<User> friendsReceivedRequests = userService.getReceivedUserFriendshipRequests(user);
		model.addAttribute("friendsReceivedRequests", friendsReceivedRequests);
			
		return "friends";
	}
	
	
	
	
	
	
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public String displaySearchFriendsForm(ModelMap model) {
		return "searchFriends";
	}
	
	
	
	
	
	
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public String searchFriendsPost(@RequestParam("email") String email, ModelMap model) {
		if(userService.checkIfUserExists(email)){
			User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
			User friendTarget = userService.getUser(email);
			if(user.getEmail().equals(email))
			{
				model.addAttribute("selfRequest", true);
				model.addAttribute("display", true);
			
			}else if(userService.checkIfTheyHadContacted(user, friendTarget)){
				model.addAttribute("isAlreadyAfriend", true);
				System.out.println("kontaktowali się już");
			}else{
				model.addAttribute("userEmail", friendTarget.getEmail());
				System.out.println("user dodany do widoku");
			}	
			
		}else{
			model.addAttribute("notFound", true);
		}
		return "searchFriends";
	}
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/friendRequest", method = RequestMethod.POST)
	public String addFriend(@RequestParam("email") String email, ModelMap model) {
		
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		User invitedUser = userService.getUser(email);
		
		
		
		
		System.out.println("userId: " + user.getId() + " and friendId: " + invitedUser.getId());
		
		
		userService.inviteUserToFriends(user, invitedUser);
			
		
		return "searchFriends";
	}




	@RequestMapping(value="/acceptUser", method = RequestMethod.GET)
	public String acceptFriendRequest(@RequestParam("email") String email){
		try{	
			userService.accceptUserInvitation(email);
			return "redirect:/friends";
		}catch(Exception e){
			System.out.println("Exception: " + e);
		}
		return "redirect:/friends";
	}


	
	@RequestMapping(value="/userDetail/{email:.+}", method = RequestMethod.GET)
	public String otherUserProfile(@PathVariable("email") String email, ModelMap model){
		
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		User userRequest = userService.getUser(email);
		boolean allowToSeeProfile = true;
		
		if(!userService.checkIfTheyHadContacted(user, userRequest)){
			model.addAttribute("contact", "without");
		}else{
			Friendship friendship = userService.getUsersFriendship(user, userRequest);
			if(friendship != null){
				System.out.println("user1: " + friendship.getFriendRequester().getEmail() 
								+ " user1: " + friendship.getFriendAccepter().getEmail()
								+ " actionUser: " + friendship.getActionUser().getEmail()
								+ " state: " + friendship.getState());

				int friendshipState = friendship.getState();
				long actionUserId = friendship.getActionUserId();
				
				switch(friendshipState){
					case 1:if(user.getId() == actionUserId){
								model.addAttribute("contact", "pendingRequester");
							}else{
								model.addAttribute("contact", "pendingReceiver");
							}
							break;
					case 2: model.addAttribute("contact", "friends");
							break;
					case 3:if(user.getId() == actionUserId){
						model.addAttribute("contact", "decliner");
					}else{
						model.addAttribute("contact", "declined");
					}
					allowToSeeProfile = false;
					break;
					case 4:if(user.getId() == actionUserId){
								model.addAttribute("contact", "blocker");
							}else{
								model.addAttribute("contact", "blocked");
							}
							allowToSeeProfile = false;
							break;
				}
			}
		}
		if(allowToSeeProfile){
			model.addAttribute("user", userRequest);
		}
		return "profile";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}