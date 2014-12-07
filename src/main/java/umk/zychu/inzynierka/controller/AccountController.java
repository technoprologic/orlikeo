package umk.zychu.inzynierka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class AccountController {


	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView userProfile(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return new ModelAndView("profile");
	}
	
	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public ModelAndView userPassword(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return new ModelAndView("password");
	}
	
	
	@RequestMapping(value = "/changePswd", method = RequestMethod.GET)
	public ModelAndView changePswd(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return new ModelAndView("profile");
	}
	
}