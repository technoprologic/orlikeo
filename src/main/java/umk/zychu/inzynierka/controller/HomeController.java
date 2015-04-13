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

import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.UserEventService;
import umk.zychu.inzynierka.service.UserService;

@Controller
@RequestMapping(value = { "/", "", "/home" })
public class HomeController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
	
	
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UserEventService objectService;
	
	@Autowired
	UserService userService;

	
	

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home(ModelMap model) {	
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		List<EventWindowBlock> eventWindowBlockList = eventService.getEventsBlockWindowList(user);
		debug(eventWindowBlockList);
		model.addAttribute("eventWindowsList", eventWindowBlockList );
		model.addAttribute("page", "fast");
		model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
		return new ModelAndView("home");
	}
	
	
	private void debug(List<EventWindowBlock> eventWindowBlockList ){
		for(int i=0; i<eventWindowBlockList.size(); i++){
			if(eventWindowBlockList.get(i) != null){
				long eventId = eventWindowBlockList.get(i).getEventId();
				String city = eventWindowBlockList.get(i).getCity();
				String address = eventWindowBlockList.get(i).getAddress();
				int willCome = eventWindowBlockList.get(i).getGoingToCome();
				int limit = eventWindowBlockList.get(i).getLimit();
				long state = eventWindowBlockList.get(i).getStateId();
				int haveTheSameState = eventWindowBlockList.get(i).getCountedInSameState();
				
				logger.debug("i: " + i + " Event id:" + eventId 
							+ " City:" + city
							+ " Address:" + address 
							+ " WillCome:" + willCome
							+ " Limit:" + limit
							+ " State:" + state
							+ " InTheSameStateCounter:" + haveTheSameState 
							);
			}else{
				logger.debug("Event id: brak");
			}
		}
		
	}
	
}