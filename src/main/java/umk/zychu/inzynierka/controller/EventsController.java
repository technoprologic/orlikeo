package umk.zychu.inzynierka.controller;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.GraphicService;
import umk.zychu.inzynierka.service.OrlikService;
import umk.zychu.inzynierka.service.UserEventService;
import umk.zychu.inzynierka.service.UserService;
import umk.zychu.inzynierka.controller.DTObeans.ChoosenOrlikBean;
import umk.zychu.inzynierka.controller.DTObeans.CreatedEventDetails;
import umk.zychu.inzynierka.controller.DTObeans.EditEventForm;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventUser;
import umk.zychu.inzynierka.controller.DTObeans.JsonEventObject;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.controller.DTObeans.UsersEventDetail;
import umk.zychu.inzynierka.controller.validator.ChoosenOrlikBeanValidator;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserEvent;

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
			return "redirect:/events/create";
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
	
	
	
	@RequestMapping(value = "/graphic/{orlik}/{event}", method = RequestMethod.GET)
	public ModelAndView graphic(@PathVariable("orlik") long orlikId, @PathVariable("event") long eventId) {

		ObjectMapper mapper = new ObjectMapper();
		List<Graphic> graphic = graphicService.getOrlikGraphicByOrlik(orlikService.getOrlikById(orlikId).get());
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
		model.addObject("orlikInfo", orlikService.getOrlikById(orlikId).get());
		
		List<User> managers = orlikService.getOrlikManagersByOrlikId(orlikId);
		model.addObject("managers", managers);
		model.addObject("evId", eventId);
		return model;
	}

	
	@RequestMapping(value = "/reserve/{graphicId}", method = RequestMethod.GET)
	public String reserve(@PathVariable("graphicId") long graphicId, Model model) {
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
	
	
	
	@RequestMapping(value = "/reserve/{eventId}/{graphicId}", method = RequestMethod.GET)
	public String reserve(@PathVariable("eventId") long eventId, @PathVariable("graphicId") long graphicId,  Model model) {
		try {
			eventService.updateEventGraphic(eventId, graphicId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/events/edit/" + eventId;
	}
	
	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register( @ModelAttribute("registerEventForm") RegisterEventForm form, ModelMap model, BindingResult result, HttpServletRequest request) {
		
		if(result.hasErrors()) return "error";
		
		Event event = eventService.registerEventForm(form);
		
		CreatedEventDetails createdEventDetails  = eventService.getEventAndGraphicAndOrlikByEvent(event);
		model.addAttribute("eventDetails", createdEventDetails);
		
		List<UsersEventDetail> usersEventDetailList = userEventService.getUsersEventDetail(event);
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
		return "redirect:/events/all";
	}
	
	
	
	
	@RequestMapping(value="/reject/{eventId}", method = RequestMethod.GET)
	public String rejectEvent(@PathVariable("eventId") long eventId){
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());

		if(eventService.isInvitedOnTheEvent(user, eventId) > 0){
			eventService.setQuitDecision(user.getId(), eventId);
		}
		return "redirect:/events/all";
	}
	
	
	
	
	@RequestMapping(value = "/details/{event}", method = RequestMethod.GET)
	public String details(@PathVariable("event") long id, ModelMap model) {

		Event event = eventService.getEventById(id).get();

		CreatedEventDetails createdEventDetails  = eventService.getEventAndGraphicAndOrlikByEvent(event);
		model.addAttribute("eventDetails", createdEventDetails);
	
		List<User> managers = orlikService.getOrlikManagersByOrlikId(event.getGraphic().getOrlikId());
		model.addAttribute("managers", managers);
			
		List<UsersEventDetail> usersEventDetailList = userEventService.getUsersEventDetail(event);
		List<User> usersJoinedDecision = new ArrayList<User>();
		List<User> usersWithoutDecision = new ArrayList<User>();
		List<User> usersRejectedDecision = new ArrayList<User>();
		List<User> usersPermittedOnly = new ArrayList<User>();
		
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		int decision = -1;
		
		Iterator<UsersEventDetail> iterator = usersEventDetailList.iterator();
		while (iterator.hasNext()) {
			UsersEventDetail userEventDetail = (UsersEventDetail) iterator.next();
			if(user.getId() == userEventDetail.getUser().getId()){
				decision = (int)userEventDetail.getUserEvent().getUserDecision();
				System.out.println(decision);
				
			}
	
			switch((int)userEventDetail.getUserEvent().getUserDecision()){
			  	case 1: usersWithoutDecision.add(userEventDetail.getUser());
			  			break;
			  	case 2: usersJoinedDecision.add(userEventDetail.getUser());
			  			break;
			  	case 3: usersRejectedDecision.add(userEventDetail.getUser());
			  			break;
			  	case 4: usersPermittedOnly.add(userEventDetail.getUser());
			  			break;
			  }
		}
		model.addAttribute("usersWithoutDecision", usersWithoutDecision);
		model.addAttribute("usersJoinedDecision", usersJoinedDecision);
		model.addAttribute("usersPermittedOnly", usersPermittedOnly);
		model.addAttribute("usersRejectedDecision", usersRejectedDecision);
		model.addAttribute("decision", decision);
		
		String organizerEmail = eventService.getEventUserOrganizerEmail(event);
		
		if(organizerEmail.equals(SecurityContextHolder.getContext().getAuthentication().getName().toString())){
			model.addAttribute("organizerEmail", true);
		}
		else{
			model.addAttribute("organizerEmail", false);
		}
			
		System.out.println(organizerEmail  + " vs " + SecurityContextHolder.getContext().getAuthentication().getName().toString());		
		
		return "details";
	}
	
	
	
	
	@RequestMapping(value= "/remove/{eventId}", method = RequestMethod.GET)
	public String  removeEvent(@PathVariable("eventId") long id){
		try{
			eventService.deleteEventById(id);
		}
		catch(Exception e){
			System.out.println("Exception : " + e);
		}
		
		return "redirect:/events/all";
		
	}
	
	
	
	
	

	

	@RequestMapping(value = "/edit/{eventId}", method = RequestMethod.GET)
	public String editGet(@PathVariable("eventId") long id, ModelMap model) {

		try {
			Event event = eventService.getEventById(id).get();		
			Graphic graphic = event.getGraphic();
			Orlik orlik = graphic.getOrlik();
			User loggedUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
			List<UserEvent> usersEvents = eventService.getUserEvent(event.getId());
			List<User> friends = userService.getUserFriendships(loggedUser);
			List<RegisterEventUser> users = new ArrayList<RegisterEventUser>();
			
			for(UserEvent userEvent : usersEvents){
				if(userEvent.getUserId() != loggedUser.getId()){  
					
					boolean decision = false;
					if(userEvent.getUserDecision() != 4) decision = true;
					RegisterEventUser e = new RegisterEventUser(userEvent.getUserId(), 
															userEvent.getUserPermission(), 
															decision, 
															userEvent.getUser().getEmail(), 
															userEvent.getUser().getAge(), 
															userEvent.getUser().getPosition());
					users.add(e);
				}
			}
			
			for(User friend : friends){
				boolean isInvited = false;
				for(UserEvent userEvent : usersEvents){
					if(friend.getId() == userEvent.getUserId()){
						isInvited = true;
						System.out.println("friendid: " + friend.getId() + " == " + userEvent.getUser().getId());
						
					}
				}
					
				if(isInvited == false){
					RegisterEventUser e1 = new RegisterEventUser(friend.getId(), 
							false, 
							false, 
							friend.getEmail(), 
							friend.getAge(), 
							friend.getPosition());
					users.add(e1);
				}
			}
			
			EditEventForm form = new EditEventForm();
			form.setGraphicId(id);
			form.setUserFriends(users);
			model.addAttribute("orlik", orlik);
			model.addAttribute("graphic", graphic);
			model.addAttribute("event", event);
			model.addAttribute("editEventForm", form);
			
			List<User> managers = orlikService.getOrlikManagersByOrlikId(orlik.getId());
			model.addAttribute("managers", managers);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "eventEdit";
	}
	
	
	
	
	
	

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editPost( @ModelAttribute("editEventForm") EditEventForm form, ModelMap model, BindingResult result, HttpServletRequest request) {
		if(result.hasErrors()){
			return "redirect:/events";
		}
		eventService.updateEvent(form);
		return "redirect:/events/edit/" + form.getEventId();
	}
	
	
	
	
	
	@RequestMapping(value = "/create/{eventId}", method = RequestMethod.GET)
	public String editForm(@PathVariable("eventId") long id, Map<String, Object> model) {
		ChoosenOrlikBean choosenOrlikBean = new ChoosenOrlikBean();
		model.put("choosenOrlikBean", choosenOrlikBean);
		model.put("orliks", orlikService.getOrliksIdsAndNames());
		model.put("eventId", id);
		return "create";
	}
	
	
	
	
	
	@RequestMapping(value = "/editGraphic", method = RequestMethod.POST)
	public String editGraphic(
			@ModelAttribute @Valid ChoosenOrlikBean chooseOrlikBean,
			ModelMap model, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors() || chooseOrlikBean.getId() == 0) {
			return "redirect:/events/create/" + chooseOrlikBean.getEventId();
		}
		return "redirect:graphic/" + chooseOrlikBean.getId() + "/" + chooseOrlikBean.getEventId();
	}

	
	
	
	
	
	
}