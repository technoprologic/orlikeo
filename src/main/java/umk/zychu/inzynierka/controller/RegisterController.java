package umk.zychu.inzynierka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView registerNewUser(ModelMap model) {

		model.addAttribute("message", "Spring 3 MVC Hello World");
		return new ModelAndView("register");

	}

	
}
