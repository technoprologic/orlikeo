package umk.zychu.inzynierka.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import umk.zychu.inzynierka.service.EventStateService;
import umk.zychu.inzynierka.service.FriendshipService;
import umk.zychu.inzynierka.service.GraphicService;
import umk.zychu.inzynierka.service.OrlikService;
import umk.zychu.inzynierka.service.UserEventDecisionService;
import umk.zychu.inzynierka.service.UserEventRoleService;
import umk.zychu.inzynierka.service.UserEventService;
import umk.zychu.inzynierka.service.UserService;
import umk.zychu.inzynierka.controller.DTObeans.ChoosenOrlikBean;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventUser;
import umk.zychu.inzynierka.controller.DTObeans.JsonEventObject;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
/*import umk.zychu.inzynierka.controller.DTObeans.UsersEventDetail;*/
import umk.zychu.inzynierka.controller.validator.ChoosenOrlikBeanValidator;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.EventState;
import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.model.UserEventRole;


@Controller
@RequestMapping("/events")
public class EventsController {

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
	private EventStateService eventStateService;
	@Autowired
	private UserEventDecisionService decisionService;
	@Autowired
	private FriendshipService friendshipService;
	@Autowired
	private UserEventRoleService roleService;
	
	@Autowired
	private ChoosenOrlikBeanValidator choosenOrlikBeanValidator;

	@InitBinder("chooseOrlikBean")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(choosenOrlikBeanValidator);
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createInitForm(Map<String, Object> model) {
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
	public ModelAndView graphic(@PathVariable("orlik") Integer orlikId) {
		ObjectMapper mapper = new ObjectMapper();
		Orlik orlik = orlikService.getOrlikById(orlikId);
		List<Graphic> graphics = orlik.getGraphicCollection();
		List<JsonEventObject> graphicEntityList = new ArrayList<JsonEventObject>();

		for (Graphic i : graphics) {
			Graphic graphic = i;
			JsonEventObject jObj = new JsonEventObject(graphic);
			graphicEntityList.add(jObj);
		}

		ModelAndView model = new ModelAndView("graphic");
		String jsonGraphicsList = "";
		try {
			jsonGraphicsList = mapper.writeValueAsString(graphicEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("list", jsonGraphicsList);
		model.addObject("orlikInfo", orlik);
		List<User> managers = orlik.getOrlikManagers();
		model.addObject("managers", managers);
		return model;
	}
	
	@RequestMapping(value = "/graphic/{orlik}/{event}", method = RequestMethod.GET)
	public ModelAndView graphic(@PathVariable("orlik") Integer orlikId, @PathVariable("event") Integer eventId) {
		ObjectMapper mapper = new ObjectMapper();
		Orlik orlik = orlikService.getOrlikById(orlikId);
		List<Graphic> graphics = (List<Graphic>) orlik.getGraphicCollection(); 
		List<JsonEventObject> graphicEntityList = new ArrayList<JsonEventObject>();

		for (Graphic i : graphics) {
			Graphic graphic = i;
			JsonEventObject jObj = new JsonEventObject(graphic);
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
		model.addObject("orlikInfo", orlik);
		List<User> managers = orlikService.getOrlikManagersByOrlik(orlik);
		model.addObject("managers", managers);
		model.addObject("evId", eventId);
		return model;
	}

	@RequestMapping(value = "/reserve/{graphicId}", method = RequestMethod.GET)
	public String reserve(@PathVariable("graphicId") Integer graphicId, Model model, Principal principal) {
		try {
			Graphic graphicEntity = graphicService.findOne(graphicId);
			Orlik orlik = graphicEntity.getOrlik();	
			List<User> userFriends = friendshipService.getFriendsByState(Friendship.ACCEPTED);
			List<RegisterEventUser> users = new ArrayList<RegisterEventUser>();

			for(User u : userFriends){
				RegisterEventUser e = new RegisterEventUser(u.getId(), false, false, u.getEmail(), u.getDateOfBirth(), u.getPosition(), null);
				users.add(e);
			}			
			RegisterEventForm form = new RegisterEventForm();
			form.setGraphicId(graphicId);
			form.setEventFormMembers(users);
			model.addAttribute("orlik", orlik);
			model.addAttribute("event", graphicEntity);
			model.addAttribute("registerEventForm", form);
			model.addAttribute("reserve", true);
			List<User> managers = orlikService.getOrlikManagersByOrlik(orlik);
			model.addAttribute("managers", managers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "reserve";
	}
	
	@RequestMapping(value = "/reserve/{eventId}/{graphicId}", method = RequestMethod.GET)
	public String reserve(@PathVariable("eventId") Integer eventId, @PathVariable("graphicId") Integer graphicId,  Model model) {
		try {
			Optional<Event> ev = eventService.getEventById(eventId);
			if(ev.isPresent()){
				Event event = ev.get();
				event.setGraphic(graphicService.findOne(graphicId));
				EventState state = eventStateService.findOne(EventState.IN_A_BASKET);
				if(event.getState().equals(state)){
					EventState eventState = eventStateService.findOne(EventState.IN_PROGRESS);
					event.setState(eventState);
				}
				event = eventService.save(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/events/edit/" + eventId;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register( @ModelAttribute("registerEventForm") RegisterEventForm form, 
			ModelMap model, BindingResult result, HttpServletRequest request) {		
		if(result.hasErrors()) 
			return "error";
		Event event = eventService.registerEventForm(form);
		UserGameDetails gameDetails  = eventService.getGameDetails(event);
		model.addAttribute("eventDetails", gameDetails);
		List<UserEvent> usersEvents = event.getUsersEvent();
		model.addAttribute("usersEvents", usersEvents);
		return "eventCreated";
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String organized(ModelMap model, Principal principal) {
		User user = userService.getUser(principal.getName());
		List<EventWindowBlock> eventWindowBlockList = eventService.getEventWindowBlocks(null);
		model.addAttribute("eventWindowsList", eventWindowBlockList );
		List<UserGameDetails> userGamesDetailsList = eventService.getGamesDetails(user);
		model.addAttribute("userGamesDetailsList", userGamesDetailsList);
		model.addAttribute("page", "all");
		return "eventsInState";
	}
	
	@RequestMapping(value="/list/{roleId}", method = RequestMethod.GET)
	public String mainPageEventsByRole(@PathVariable("roleId") Integer roleId, Model model, Principal principal){
		User user = userService.getUser(principal.getName());		
		UserEventRole role = roleService.findOne(roleId);
		List<EventWindowBlock> eventWindowBlockList = eventService.getEventWindowBlocks(role);
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
	public String allInState(@PathVariable("stateId") Integer stateId, Model model, Principal principal){
		User user = userService.getUser(principal.getName());	
		List<EventWindowBlock> eventWindowBlockList = eventService.getEventWindowBlocks(null);
		model.addAttribute("eventWindowsList", eventWindowBlockList );
		List<UserGameDetails>  userGamesDetailsList = eventService.getGamesDetailsByStateId(user, stateId);
		model.addAttribute("userGamesDetailsList", userGamesDetailsList);
		model.addAttribute("stateId", stateId);
		model.addAttribute("page", "all");
		return "eventsInState";
	}

	@RequestMapping(value="/listDetails/{stateId}/{roleId}", method = RequestMethod.GET)
	public String detailedPageEventsByRole(@PathVariable("stateId") Integer stateId, @PathVariable("roleId") Integer roleId, Model model, Principal principal){
		User user = userService.getUser(principal.getName());
		List<UserGameDetails>  userGamesDetailsList = eventService.getGamesDetailsByRoleIdAndStateId(user, roleId, stateId);		
		UserEventRole role = roleService.findOne(roleId);
		List<EventWindowBlock> eventWindowBlockList = eventService.getEventWindowBlocks(role);
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
	
	@RequestMapping(value = "/decision/{eventId}/{decision}", method = RequestMethod.GET)
	public String userEventDecision(
			@PathVariable("decision") String decision,
			@PathVariable("eventId") Integer eventId) {
		Optional<Event> eventOpt = eventService.getEventById(eventId);
		if (!eventOpt.isPresent()) {
			return "redirect:/";
		}
		Event event = eventOpt.get();
		if (eventService.isEventMember(event)) {
			Integer decisionId = -1;
			switch (decision) {
			case "accept":
				decisionId = UserDecision.ACCEPTED;
				break;
			case "reject":
				decisionId = UserDecision.REJECTED;
				break;
			default:
				return "redirect:/";
			}
			UserDecision userDecision = decisionService.findOne(decisionId);
			userEventService.setUserEventDecision(event, userDecision);
		}
		return "redirect:/events/all";
	}
	
	@RequestMapping(value = "/details/{event}", method = RequestMethod.GET)
	public String details(@PathVariable("event") Integer id, ModelMap model, Principal principal) {
		User user = userService.getUser(principal.getName());	
		try{
			Optional<Event> ev = eventService.getEventById(id);
			if(ev.isPresent()){
				Event event = ev.get();
				UserGameDetails gameDetails  = eventService.getGameDetails(event);
				model.addAttribute("details", gameDetails);
				if(event.getGraphic() != null){
					Orlik orlik = event.getGraphic().getOrlik();
					List<User> managers = orlikService.getOrlikManagersByOrlik(orlik);
					model.addAttribute("managers", managers);
				}
				UserDecision accepted = decisionService.findOne(UserDecision.ACCEPTED);
				List<User> usersJoinedDecision = userEventService.findUsersByEventAndDecision(event, accepted);
				model.addAttribute("usersJoinedDecision", usersJoinedDecision);
				UserDecision without = decisionService.findOne(UserDecision.INVITED);
				List<User> usersWithoutDecision = userEventService.findUsersByEventAndDecision(event, without);
				model.addAttribute("usersWithoutDecision", usersWithoutDecision);
				UserDecision rejected = decisionService.findOne(UserDecision.REJECTED);
				List<User> usersRejectedDecision = userEventService.findUsersByEventAndDecision(event, rejected);
				model.addAttribute("usersRejectedDecision", usersRejectedDecision);
				Boolean canInvite = true;
				List<User> usersPermitted = userEventService.findUsersByEventAndPermission(event, canInvite);
				model.addAttribute("usersPermittedOnly", usersPermitted);
				Optional<UserEvent> loggedUserEvent = userEventService.findOne(event, user);
				if(loggedUserEvent.isPresent()){
					UserEvent ue = loggedUserEvent.get();
					model.addAttribute("decision", ue.getDecision().getId());
					model.addAttribute("allowed", ue.getUserPermission());
				}else{
					return "redirect:/home";
				}
				String organizerEmail = event.getUserOrganizer().getEmail();
				if(organizerEmail.equals(principal.getName())){
					model.addAttribute("isOrganizer", true);
				}
				else{
					model.addAttribute("isOrganizer", false);
				}
			}
		}catch(Exception e){
			logger.debug("EXCEPTION " + e);
		}
		return "details";
	}
	
	@RequestMapping(value= "/remove/{eventId}", method = RequestMethod.GET)
	public String  removeEvent(@PathVariable("eventId") Integer id){
		try{
			eventService.deleteEventById(id);
		}
		catch(Exception e){
			System.out.println("Exception : " + e);
		}
		return "redirect:/events/all";
		
	}

	@RequestMapping(value = "/edit/{eventId}", method = RequestMethod.GET)
	public String editGet(@PathVariable("eventId") Integer id, ModelMap model, Principal principal) {
		User loggedUser = userService.getUser(principal.getName());
		Optional<Event> eventOpt = eventService.getEventById(id);
		if(eventOpt.isPresent()){
			Optional<UserEvent> userEvent = eventOpt.get().getUsersEvent().stream()
					.filter(ev -> ev.getUser().equals(loggedUser))
					.findFirst();
			if(!userEvent.isPresent() || !userEvent.get().getUserPermission()){
				return "redirect:/events/all";
			}
		}
		try {	
			Event event = eventOpt.get();
			UserGameDetails gameDetails = eventService.getGameDetails(event);
			model.addAttribute("eventDetails", gameDetails);
			RegisterEventForm form = eventService.generateRegisterEventForm(event);
			model.addAttribute("editEventForm", form);
			Graphic graphic = event.getGraphic();
			Orlik orlik = graphic == null ? null : graphic.getOrlik();	
			List<User> managers = orlik == null ? new ArrayList<User>() : orlik.getOrlikManagers();
			model.addAttribute("managers", managers);
			boolean isOrganizer = event.getUserOrganizer().equals(loggedUser);
			model.addAttribute("isOrganizer", isOrganizer);
			model.addAttribute("editFormUser", loggedUser.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "eventEdit";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editPost( @ModelAttribute("editEventForm") RegisterEventForm form, ModelMap model, BindingResult result, HttpServletRequest request, Principal principal) {
		if(result.hasErrors()){
			return "redirect:/events";
		}		
		String username = principal.getName();
		Optional<Event> event = eventService.getEventById(form.getEventId());
		if(event.isPresent()){
			Optional<UserEvent> userEvent = event.get().getUsersEvent().stream().filter(ev -> ev.getUser().getEmail().equals(username)).findFirst();
			if(userEvent.isPresent() && userEvent.get().getUserPermission()){
				eventService.updateEvent(form);
			}else{
				return "redirect:/events/all";
			}
		}
		return "redirect:/events/edit/" + form.getEventId();
	}
	
	@RequestMapping(value = "/create/{eventId}", method = RequestMethod.GET)
	public String editForm(@PathVariable("eventId") Integer id, Map<String, Object> model) {
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