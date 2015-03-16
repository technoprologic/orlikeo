package umk.zychu.inzynierka.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.UserService;


@Controller
@RequestMapping("/friends")
public class FriendsController {

	@Autowired
	UserService userService;
	

	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String getFriends(ModelMap model) {

		List<User> friends = userService.getUserFriends(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("friends", friends);
		return "friends";
	}
	
	
	
	
	
	
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public String displaySearchFriendsForm(ModelMap model) {
		return "searchFriends";
	}
	
	
	
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public String searchFriendsPost(@RequestParam("email") String email, ModelMap model) {
		if(userService.checkIfUserExists(email)){
			//TODO sprawdzić czy zaproszenie już nie zostało wysłane
			User user = userService.getUser(email);
			model.addAttribute("user", user);
		}else{
			model.addAttribute("error", true);
		}
		return "searchFriends";
	}
	
	
	
	@RequestMapping(value="/addFriend", method = RequestMethod.GET)
	public ModelAndView addFriend(ModelMap model) {
		return new ModelAndView("addFriend");
	}










}