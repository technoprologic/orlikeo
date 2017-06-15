package umk.zychu.inzynierka.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = { "/", "", "/home" })
public class HomeController extends ServicesAwareController {

	private static final Logger logger = LoggerFactory.getLogger(EventsController.class);

	@Value("${my.var}")
	public String myVar;

	@RequestMapping(method = RequestMethod.GET)
	public String home(ModelMap model) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

		if(authorities.stream().filter(a -> a.getAuthority().contains("ROLE_ANIMATOR")).count() > 0) {
			return "redirect:/pane";
		}
		String dsd = myVar;
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