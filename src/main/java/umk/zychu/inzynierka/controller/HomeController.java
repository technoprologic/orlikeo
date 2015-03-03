package umk.zychu.inzynierka.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.service.EventService;

@Controller
@RequestMapping(value = { "/", "", "/home" })
public class HomeController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
	
	
	
	@Autowired
	EventService eventService;
	
	

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home(ModelMap model) {

		List<Event> events = eventService.getUserEvents(SecurityContextHolder.getContext().getAuthentication().getName(), 2);
		
		
		

		
		
		logger.debug(eventService.findText("wypas").getTxt());
		model.addAttribute("eventsInBuildState", events);
		return new ModelAndView("home");
	}
	
}