package umk.zychu.inzynierka.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.EventToApproveService;
import umk.zychu.inzynierka.service.UserEventService;
import umk.zychu.inzynierka.service.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = { "/", "", "/home" })
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
		
	@Autowired
	EventService eventService;

	@Autowired
	UserEventService userEventService;

	@Autowired
	UserService userService;

	@Autowired
	EventToApproveService eventToApproveService;

	@RequestMapping(method = RequestMethod.GET)
	public String home(ModelMap model) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

		if(authorities.stream().filter(a -> a.getAuthority().contains("ROLE_ANIMATOR")).count() > 0) {
			return "redirect:/pane";
		}

		List<EventWindowBlock> eventWindowBlockList = eventService.getEventWindowBlocks(null);
		model.addAttribute("eventWindowsList", eventWindowBlockList );
		model.addAttribute("page", "all");
		//debug(eventWindowBlockList);
		return "home";
	}
		
	private void debug(List<EventWindowBlock> eventWindowBlockList ){
		for(int i=0; i<eventWindowBlockList.size(); i++){
			if(eventWindowBlockList.get(i) != null){
				eventWindowBlockList.get(i).toString();
			}else{
				logger.debug("Event id: brak");
			}
		}	
	}

	@RequestMapping(value = "denied")
	public String denied(){
		return "error";
	}

	@RequestMapping(value = "error")
	public String error404(){
		return "error";
	}
}