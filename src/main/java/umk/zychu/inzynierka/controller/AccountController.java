package umk.zychu.inzynierka.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import umk.zychu.inzynierka.service.*;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;


	@RequestMapping(value = "/profile/{userId}", method = RequestMethod.GET)
	public ModelAndView userProfile(@PathVariable String userId, ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		//userService.getUser("rzeznik@o2.pl").getId();
		
		model.addAttribute("owner", userService.getUser("saa@jw.pl"));
		return new ModelAndView("profile");
	}
	
	
	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public ModelAndView userPassword(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return new ModelAndView("password");
	}
	
	
	@RequestMapping(value = "/changePswd", method = RequestMethod.GET)
	public ModelAndView changePassword(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return new ModelAndView("profile");
	}
	
}