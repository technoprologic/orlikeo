package umk.zychu.inzynierka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {

	@RequestMapping(value = "/register/new_user", method = RequestMethod.POST)
	public String registerNewUser(ModelMap model) {

		model.addAttribute("message", "Spring 3 MVC Hello World");
		return "createAccount";

	}

	
}
