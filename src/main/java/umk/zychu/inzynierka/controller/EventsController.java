package umk.zychu.inzynierka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/events")
public class EventsController {

	

	@RequestMapping(value="", method = RequestMethod.GET)
	public String dupa2(ModelMap model) {

	    return "home";
	}
	
	
	@RequestMapping(value="/dupa", method = RequestMethod.GET)
	public String dupa(ModelMap model) {

	    return "home";
	}
	
	
	@RequestMapping( value="/login", method = RequestMethod.GET)
	public String create2(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return "profile";
	}

}