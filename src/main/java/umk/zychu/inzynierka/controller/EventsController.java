package umk.zychu.inzynierka.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.GraphicService;
import umk.zychu.inzynierka.service.OrlikService;
import umk.zychu.inzynierka.service.UserEventService;
import umk.zychu.inzynierka.service.UserService;
import umk.zychu.inzynierka.controller.DTObeans.ChoosenOrlikBean;
import umk.zychu.inzynierka.controller.DTObeans.CreatedEventDetails;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventUser;
import umk.zychu.inzynierka.controller.DTObeans.JsonEventObject;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.controller.DTObeans.UsersEventDetail;
import umk.zychu.inzynierka.controller.validator.ChoosenOrlikBeanValidator;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;

@Controller
@RequestMapping("/events")
public class EventsController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
	
	@Autowired
	private OrlikService orlikService;
	@Autowired
	private EventService eventService;
	@Autowired
	private UserService userService;
	@Autowired
	private GraphicService graphicService;
	@Autowired
	private UserEventService userEventService;
	
	
	@Autowired
	private ChoosenOrlikBeanValidator choosenOrlikBeanValidator;

	@InitBinder("chooseOrlikBean")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(choosenOrlikBeanValidator);
	}

	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String initForm(Map<String, Object> model) {
		ChoosenOrlikBean choosenOrlikBean = new ChoosenOrlikBean();
		model.put("choosenOrlikBean", choosenOrlikBean);
		model.put("orliks", orlikService.getOrliksIdsAndNames());
		return "create";
	}

	
	
	@RequestMapping(value = "/graphic", method = RequestMethod.POST)
	public String graphic(
			@ModelAttribute @Valid ChoosenOrlikBean chooseOrlikBean,
			ModelMap model, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors() || chooseOrlikBean.getId() == 0) {
			return "redirect:create";
		}
		return "redirect:graphic/" + chooseOrlikBean.getId();
	}

	
	
	@RequestMapping(value = "/graphic/{orlik}", method = RequestMethod.GET)
	public ModelAndView graphic(@PathVariable("orlik") long id) {

		ObjectMapper mapper = new ObjectMapper();
		List<Graphic> graphic = graphicService.getOrlikGraphicByOrlik(orlikService.getOrlikById(id).get());
		List<JsonEventObject> graphicEntityList = new ArrayList<JsonEventObject>();

		for (Graphic i : graphic) {
			Graphic graphicEntity = i;
			JsonEventObject jObj = new JsonEventObject();
			jObj.id = graphicEntity.getId();
			jObj.title = graphicEntity.getTitle();
			jObj.start = (graphicEntity.getStartTime()).getTime();
			jObj.end = (graphicEntity.getEndTime()).getTime();
			jObj.url = null;
			jObj.allDay = false;
			graphicEntityList.add(jObj);
		}

		ModelAndView model = new ModelAndView("graphic");
		String json = "";
		try {
			json = mapper.writeValueAsString(graphicEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("list", json);
		model.addObject("orlikInfo", orlikService.getOrlikById(id).get());
		
		List<User> managers = orlikService.getOrlikManagersByOrlikId(id);
		model.addObject("managers", managers);
		return model;
	}

	
	
	@RequestMapping(value = "/reserve/{id}", method = RequestMethod.GET)
	public String reserve(@PathVariable("id") long graphicId, Model model) {
		try {
			Graphic graphicEntity = graphicService.getGraphicById(graphicId).get();
			Orlik orlik = graphicEntity.getOrlik();	
			User loggedUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
			List<User> userFriends = userService.getUserFriendships(loggedUser);
			List<RegisterEventUser> users = new ArrayList<RegisterEventUser>();
			for(User user : userFriends){
				RegisterEventUser e = new RegisterEventUser(user.getId(), false, false, user.getEmail(), user.getAge(), user.getPosition());
				users.add(e);
			}
			
			RegisterEventForm form = new RegisterEventForm();
			form.setGraphicId(graphicId);
			form.setUserFriends(users);
			model.addAttribute("orlik", orlik);
			model.addAttribute("event", graphicEntity);
			model.addAttribute("registerEventForm", form);
			
			List<User> managers = orlikService.getOrlikManagersByOrlikId(orlik.getId());
			model.addAttribute("managers", managers);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "reserve";
	}
	
	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register( @ModelAttribute("registerEventForm") RegisterEventForm form, ModelMap model, BindingResult result, HttpServletRequest request) {
		
		if(result.hasErrors()) return "error";
		
		Event event = eventService.registerEventForm(form);
		
		List<CreatedEventDetails> createdEventDetails  = eventService.getEventAndGraphicAndOrlikByEvent(event);
		model.addAttribute("eventDetails", createdEventDetails.get(0));
		
		List<UsersEventDetail> usersEventDetailList = userEventService.getUsersEventdetail(event);
		model.addAttribute("usersEventDetailList", usersEventDetailList);
		
		return "eventCreated";
	}

	

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String organized(ModelMap model) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		List<EventWindowBlock> eventWindowBlockList = eventService.getEventsBlockWindowList(user);
		model.addAttribute("eventWindowsList", eventWindowBlockList );
		List<UserGameDetails>  userGamesDetailsList = eventService.getGamesDetails(user);
		model.addAttribute("userGamesDetailsList", userGamesDetailsList);
		model.addAttribute("page", "all");
		return "eventsInState";
	}
	
	
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(ModelMap model) {

		return new ModelAndView("details");
	}
	
	

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(ModelMap model) {

		return new ModelAndView("edit");
	}
	
	
	
	@RequestMapping(value="/list/{roleId}", method = RequestMethod.GET)
	public String mainPageEventsByRole(@PathVariable("roleId") long roleId, Model model){
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		
		List<EventWindowBlock> eventWindowBlockList = eventService.getEventsBlockWindowByRoleList(user, roleId);
		model.addAttribute("eventWindowsList", eventWindowBlockList );
		
		List<UserGameDetails>  userGamesDetailsList = eventService.getGamesDetailsByRoleId(user, roleId);
		model.addAttribute("userGamesDetailsList", userGamesDetailsList);
		
		if(roleId == 1){
			model.addAttribute("page", "organized");
			
		}else if(roleId == 2){
			model.addAttribute("page", "invitations");
		}
		
		return "eventsInState";
	}
	
	
	@RequestMapping(value="/allInState/{stateId}", method = RequestMethod.GET)
	public String allInState(@PathVariable("stateId") long stateId, Model model){
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		
		List<EventWindowBlock> eventWindowBlockList = eventService.getEventsBlockWindowList(user);
		model.addAttribute("eventWindowsList", eventWindowBlockList );
		
		List<UserGameDetails>  userGamesDetailsList = eventService.getGamesDetailsByStateId(user, stateId);
		model.addAttribute("userGamesDetailsList", userGamesDetailsList);
		
		model.addAttribute("stateId", stateId);
		model.addAttribute("page", "all");
		return "eventsInState";
	}
	
	
	
	
	@RequestMapping(value="/listDetails/{stateId}/{roleId}", method = RequestMethod.GET)
	public String detailedPageEventsByRole(@PathVariable("stateId") long stateId, @PathVariable("roleId") long roleId, Model model){
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		List<UserGameDetails>  userGamesDetailsList = eventService.getGamesDetailsByRoleIdAndStateId(user, roleId, stateId);
		
		List<EventWindowBlock> eventWindowBlockList = eventService.getEventsBlockWindowByRoleList(user, roleId);
		model.addAttribute("eventWindowsList", eventWindowBlockList );
		
		if(roleId == 1){
			model.addAttribute("page", "organized");
			
		}else if(roleId == 2){
			model.addAttribute("page", "invitations");
		}
		model.addAttribute("stateId", stateId);
		model.addAttribute("userGamesDetailsList", userGamesDetailsList);
		return "eventsInState";
	}
	
	

	
	@RequestMapping(value="/join/{eventId}", method = RequestMethod.GET)
	public String joinEvent(@PathVariable("eventId") long eventId){
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		if(eventService.isInvitedOnTheEvent(user, eventId) > 0){
			eventService.setJoinDecision(user.getId(), eventId);
		}
		return "home";
	}
	
	
	
	
	@RequestMapping(value="/reject/{eventId}", method = RequestMethod.GET)
	public String rejectEvent(@PathVariable("eventId") long eventId){
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		
		System.out.println("OKO: " + eventService.isInvitedOnTheEvent(user, eventId));
		
		if(eventService.isInvitedOnTheEvent(user, eventId) > 0){
			eventService.setQuitDecision(user.getId(), eventId);
		}
		return "home";
	}
	
	
	
	
	
	
	
	
	
	
}