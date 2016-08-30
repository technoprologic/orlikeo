package umk.zychu.inzynierka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.FriendshipType;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.FriendshipService;
import umk.zychu.inzynierka.service.UserNotificationsService;
import umk.zychu.inzynierka.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static umk.zychu.inzynierka.model.FriendshipType.*;

@Controller
@RequestMapping("/friends")
public class FriendsController {

	@Autowired
	private FriendshipService friendshipService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserNotificationsService userNotificationsService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getFriends(final ModelMap model) {
		List<User> friends = friendshipService.getFriends(null, ACCEPT);
		model.addAttribute("friends", friends);	
		List<User> sentInvitations = friendshipService.getFriends(Boolean.TRUE, INVITE);
		model.addAttribute("friendsPendedRequests", sentInvitations);
		List<User> receiveInvitations = friendshipService.getFriends(Boolean.FALSE, INVITE);
		model.addAttribute("friendsReceivedRequests", receiveInvitations);
		List<User> blockedUsers = friendshipService.getFriends(Boolean.TRUE, BLOCK);
		model.addAttribute("blockedUsers", blockedUsers);
		return "friends";
	}
	
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public String displaySearchFriendsForm() {
		return "searchFriends";
	}
	
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public String searchFriend(final @RequestParam("email") String email, final ModelMap model) {
		if(userService.checkIfUserExists(email)){
			model.addAttribute("userEmail", email);
		}else{
			model.addAttribute("notFound", true);
		}
		return "searchFriends";
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value="/friendRequest", method = RequestMethod.POST)
	public String addFriend(final @RequestParam("email") String email) {
		return consumeRequest(email, INVITE);
	}

	
	@SuppressWarnings("finally")
	@RequestMapping(value="/acceptUser", method = RequestMethod.POST)
	public String acceptFriendRequest(final @RequestParam("email") String email){
		return consumeRequest(email, ACCEPT);
	}
	
	@RequestMapping(value="/userDetail/{email:.+}", method = RequestMethod.GET)
	public String otherUserProfile(final @PathVariable("email") String email, final ModelMap model, final Principal principal){
		User user = userService.getUser(principal.getName());
		User userRequest = userService.getUser(email);
		//if self
		if(user.getEmail().equals(userRequest.getEmail())){
			return "redirect:/account/profile/" + email;
		}

		Optional<Friendship> friendshipOptional = friendshipService.getFriendship(userRequest);
		Friendship friendship = friendshipOptional.get();
		boolean allowToSeeProfile = true;

		if(!friendshipOptional.isPresent()){
			model.addAttribute("contact", "without");
			allowToSeeProfile = true;
		}else{
				boolean isRequester = friendship.getRequester().equals(user);
				switch(friendship.getState()) {
					case INVITE:
						model.addAttribute("contact", isRequester ? "pendingRequester" : "pendingReceiver");
						break;
					case ACCEPT:
						model.addAttribute("contact", "friends");
						break;
					case DECLINE:
						model.addAttribute("contact", isRequester ? "decliner" : "declined");
						break;
					case BLOCK:
						if (isRequester) {
							model.addAttribute("contact", "blocker");
							model.addAttribute("unblockEmail", userRequest.getEmail());
						} else {
							model.addAttribute("contact", "blocked");
						}
						allowToSeeProfile = false;
						break;
					default:
						model.addAttribute("contact", "without");
						break;
				}
		}
		if(allowToSeeProfile){
			model.addAttribute("user", userRequest);
		}
		userNotificationsService.deleteAllWithFriend(userRequest);
		return "profile";
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancelInvitation(final @RequestParam("email") String email){
		return consumeRequest(email, CANCEL);
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/block", method = RequestMethod.POST)
	public String blockUser(final @RequestParam("email") String email){
		return  consumeRequest(email, BLOCK);
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public String rejectFriendRequest(final @RequestParam("email") String email){
		return consumeRequest(email, DECLINE);
		}

	@RequestMapping(value = "/unblock", method = RequestMethod.POST)
	public String unblockUser(final @RequestParam("email") String email){
		return consumeRequest(email, REMOVE);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String removeFriendship(final @RequestParam("email") String email){
		return consumeRequest(email, REMOVE);
	}

	private String consumeRequest(final String email, final FriendshipType type){
		try{
			friendshipService.changeFriendshipStatus(email, type);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return "redirect:/friends/userDetail/" + email;
		}
	}
}