package umk.zychu.inzynierka.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping(value = "/signing", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error ) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Niepoprawny login lub hasło!");
		}
		model.setViewName("login");
		return model;
	}

	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		model.addAttribute("error", "true");
		return "login";
	}

	@RequestMapping(value = "/j_spring_security_logout", method = RequestMethod.GET)
	public ModelAndView logout() {
		SecurityContextHolder.clearContext();
		ModelAndView model = new ModelAndView();
		model.addObject("msg", "Poprawnie wylogowano z serwisu.");
		model.setViewName("login");
		return model;
	}	
}
