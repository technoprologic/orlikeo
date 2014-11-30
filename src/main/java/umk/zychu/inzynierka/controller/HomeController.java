package umk.zychu.inzynierka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {


	@RequestMapping(value = "", method = RequestMethod.GET)
	public String login(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return "login";
	}
	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return "home";

	}

}