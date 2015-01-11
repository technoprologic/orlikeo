package umk.zychu.inzynierka.controller;

/*import java.security.Principal;
*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login(ModelMap model/*, Principal principal*/) {

/*		String name = principal.getName(); //get logged in username
		model.addAttribute("username", name);*/
		return new ModelAndView("login");

	}

	
}
