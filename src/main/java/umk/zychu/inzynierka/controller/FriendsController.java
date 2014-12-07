package umk.zychu.inzynierka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/friends")
public class FriendsController {

	

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getFriends(ModelMap model) {

		return new ModelAndView("friends");
	}
	
	@RequestMapping(value="/user", method = RequestMethod.GET)
	public ModelAndView getFriend(ModelMap model) {

		return new ModelAndView("profile");
	}
	
	@RequestMapping(value="/addFriend", method = RequestMethod.GET)
	public ModelAndView addFriend(ModelMap model) {

		return new ModelAndView("addFriend");
	}
}