package umk.zychu.inzynierka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EventsController {

	
	@RequestMapping(value="dupa",  method = RequestMethod.GET)
	public String dupa(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return "createEvent";
	}

	
	@RequestMapping( value="help2", method = RequestMethod.GET)
	public String create2(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return "help2";
	}
	
	
}