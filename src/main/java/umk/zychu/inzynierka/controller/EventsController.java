package umk.zychu.inzynierka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/events")
public class EventsController {

	

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(ModelMap model) {

		return new ModelAndView("create");
	}
	
	@RequestMapping(value = "/graphic", method = RequestMethod.GET)
	public ModelAndView graphic(ModelMap model) {

		return new ModelAndView("graphic");
	}
	
	@RequestMapping(value = "/reserve", method = RequestMethod.GET)
	public ModelAndView reserve(ModelMap model) {

		return new ModelAndView("reserve");
	}
	
	@RequestMapping(value = "/registerEvent", method = RequestMethod.GET)
	public ModelAndView register(ModelMap model) {

		return new ModelAndView("/home");
	}
	
	@RequestMapping(value = "/organized", method = RequestMethod.GET)
	public ModelAndView organized(ModelMap model) {

		return new ModelAndView("organized");
	}
	
	@RequestMapping(value = "/invitations", method = RequestMethod.GET)
	public ModelAndView invitations(ModelMap model) {

		return new ModelAndView("invitations");
	}
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(ModelMap model) {

		return new ModelAndView("details");
	}
}