package umk.zychu.inzynierka.controller;


import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public ModelAndView home(ModelMap model, Principal principal) {	
		User user = userService.getUser(principal.getName());
		List<EventWindowBlock> eventWindowBlockList = eventService.getEventWindowBlocks(null);
		model.addAttribute("eventWindowsList", eventWindowBlockList );
		model.addAttribute("page", "fast");
		model.put("username", user.getName());
		debug(eventWindowBlockList);
		return new ModelAndView("home");
	}
		
	private void debug(List<EventWindowBlock> eventWindowBlockList ){
		for(int i=0; i<eventWindowBlockList.size(); i++){
			if(eventWindowBlockList.get(i) != null){
				String city = eventWindowBlockList.get(i).getCity();
				String address = eventWindowBlockList.get(i).getAddress();
				Long willCome = eventWindowBlockList.get(i).getGoingToCome();
				Integer limit = eventWindowBlockList.get(i).getLimit();
				Integer state = eventWindowBlockList.get(i).getStateId();
				Long haveTheSameState = eventWindowBlockList.get(i).getCountedInSameState();
				Integer displayOrder = eventWindowBlockList.get(i).getDisplayOrder();
				String label = eventWindowBlockList.get(i).getLabel();			
				logger.debug("i: " + i 
							+ " City:" + city
							+ " Address:" + address 
							+ " WillCome:" + willCome
							+ " Limit:" + limit
							+ " State:" + state
							+ " InTheSameStateCounter:" + haveTheSameState
							+ " displayOrder:" + displayOrder
							+ " label:" +  label
							);
			}else{
				logger.debug("Event id: brak");
			}
		}	
	}
}