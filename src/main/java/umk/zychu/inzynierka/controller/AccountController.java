package umk.zychu.inzynierka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController {


	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String userProfile(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return "profile";
	}
	
	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public String userPassword(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return "password";
	}
	
}