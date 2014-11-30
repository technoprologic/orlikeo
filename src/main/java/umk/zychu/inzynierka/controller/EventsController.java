package umk.zychu.inzynierka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value="/events")
public class EventsController {

	
	@RequestMapping(method = RequestMethod.GET)
	public String dupa(ModelMap model) {

	    return "home";
	}

	
	@RequestMapping( value="/create", method = RequestMethod.GET)
	public String create2(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return "create";
	}

}