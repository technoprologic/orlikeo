package umk.zychu.inzynierka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import umk.zychu.inzynierka.controller.DTObeans.*;
import umk.zychu.inzynierka.controller.util.EventType;
import umk.zychu.inzynierka.controller.validator.ChoosenOrlikBeanValidator;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.service.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static umk.zychu.inzynierka.model.EnumeratedEventState.IN_PROGRESS;
import static umk.zychu.inzynierka.model.FriendshipType.ACCEPT;

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
	private UserEventDecisionService decisionService;
	@Autowired
	private FriendshipService friendshipService;
	@Autowired
	private UserEventRoleService roleService;
	@Autowired
	private EventToApproveService eventToApproveService;
	@Autowired
	private UserNotificationsService userNotificationsService;
	
	@Autowired
	private ChoosenOrlikBeanValidator choosenOrlikBeanValidator;

	@InitBinder("chooseOrlikBean")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(choosenOrlikBeanValidator);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String editForm(final @RequestParam(value="event", required=false) Integer id, final Map<String, Object> model) {
		ChoosenOrlikBean choosenOrlikBean = new ChoosenOrlikBean();
		model.put("choosenOrlikBean", choosenOrlikBean);
		model.put("orliks", orlikService.getOrliksIdsAndNames());
		if(id != null){
			model.put("eventId", id);
		}
		return "create";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register( final @ModelAttribute("registerEventForm") RegisterEventForm form,
			ModelMap model, final BindingResult result) {
		if(result.hasErrors()) 
			return "error";
		Event event = eventService.registerEventForm(form);
		UserGameDetails gameDetails  = eventService.getGameDetails(event);
		model.addAttribute("eventDetails", gameDetails);
		List<UserEvent> usersEvents = event.getUsersEvent();
		model.addAttribute("usersEvents", usersEvents);
		return "eventCreated";
	}
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(
			@RequestParam(value = "page", required = false) String page,
			final @RequestParam(value = "state", required = false) Integer stateId,
			final Model model,
			final Principal principal) {
		EventType eventType = null;
		UserEventRole userEventRole = null;
		if (page != null) {
			switch (page) {
				case "organized":
					eventType = EventType.ORGANIZED;
					userEventRole = roleService.findOne(1);
					break;
				case "invitations":
					eventType = EventType.INVITATIONS;
					userEventRole = roleService.findOne(2);
					break;
			}
		}else{
			page = "all";
		}
		model.addAttribute("page", page);
		List<UserGameDetails> userGamesDetails = eventService.getGamesDetails(
				principal.getName(), eventType, stateId);
		model.addAttribute("userGamesDetailsList", userGamesDetails);
		model.addAttribute("stateId", stateId);
		List<EventWindowBlock> eventsWindowsBlocks = eventService
				.getEventWindowBlocks(userEventRole);
		model.addAttribute("eventWindowsList", eventsWindowsBlocks);
        return "eventsInState";
	}
	
	@RequestMapping(value = "/decision/{eventId}", method = RequestMethod.GET)
	public String userEventDecision(
			final @PathVariable("eventId") Integer eventId,
			final @RequestParam(value = "decision") Boolean decision,
			final @RequestParam(value = "page", required = false) String page,
			final @RequestParam(value = "state", required = false) Integer stateId) {
		Optional<Event> eventOpt = eventService.getEventById(eventId);
		if (!eventOpt.isPresent()) {
			return "redirect:/";
		}
		Event event = eventOpt.get();
		if (eventService.isEventMember(event)) {
			Integer decisionId;
			if (Boolean.valueOf(decision)) {
				decisionId = UserDecision.ACCEPTED;
			} else {
				decisionId = UserDecision.REJECTED;
			}
			UserDecision userDecision = decisionService.findOne(decisionId);
			userEventService.setUserEventDecision(event, userDecision);
		}
		String url = "redirect:/";
		if(page != null && page.equals("details")){
			url += "events/details/" + event.getId();
		}else{
			url+="events/show";
		}
		if (stateId != null || page != null) {
			url += "?";
			if (stateId != null && page != null) {
				url += "page=" + page + "&state=" + stateId;
			} else if (stateId != null) {
				url += "state=" + stateId;
			} else if (page != null) {
				url += "page=" + page;
			}
		}
		return url;
	}
	
	@RequestMapping(value = "/details/{event}", method = RequestMethod.GET)
	public String details(final @PathVariable("event") Integer id, final ModelMap model, final Principal principal) {
		User user = userService.getUser(principal.getName());	
		try{
			Optional<Event> ev = eventService.getEventById(id);
			if(ev.isPresent()){
				Event event = ev.get();
				UserGameDetails gameDetails  = eventService.getGameDetails(event);
				model.addAttribute("details", gameDetails);
				if(event.getGraphic() != null){
					Orlik orlik = event.getGraphic().getOrlik();
					User animator = orlik.getAnimator();
					model.addAttribute("animator", animator);
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
				userNotificationsService.setCheckedForEvent(event);
			}
		}catch(Exception e){
			logger.debug("EXCEPTION " + e);
		}
		model.addAttribute("page", "details");
		return "details";
	}
	
	@RequestMapping(value= "/remove", method = RequestMethod.POST)
	public String removeEvent(final @RequestParam("eventToRemoveId") Integer id,
			final @RequestParam(value = "state", required = false) Integer stateId,
			final @RequestParam(value="page", required=false) String page) {
		try{
			eventService.delete(id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		String redirectUrl = "redirect:/events/show";
		if(stateId != null){
			redirectUrl += "?state="+stateId;
			if(page != null){
				redirectUrl += "&page="+page;
			}
		}else{
			if(page != null){
				redirectUrl += "?page=" + page;
			}
		}
		return redirectUrl;
		
	}

	@RequestMapping(value = "/edit/{eventId}", method = RequestMethod.GET)
	public String editGet(final @PathVariable("eventId") Integer id,
						  final ModelMap model,
						  final Principal principal) {
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
			User animator = orlik == null ? null : orlik.getAnimator();
			model.addAttribute("animator", animator);
			boolean isOrganizer = event.getUserOrganizer().equals(loggedUser);
			model.addAttribute("isOrganizer", isOrganizer);
			model.addAttribute("editFormUser", loggedUser.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!model.containsKey("saved"))
			model.addAttribute("saved", "false");
		return "eventEdit";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editPost(final @ModelAttribute("editEventForm") RegisterEventForm form,
						   final BindingResult result,
						   final Principal principal,
						   final RedirectAttributes redirectAttr) {
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
				return "redirect:/events/show";
			}
		}
		redirectAttr.addFlashAttribute("saved","true");
		return "redirect:/events/edit/" + form.getEventId();
	}
	
	@RequestMapping(value = "/reserve/{eventId}/{graphicId}", method = RequestMethod.GET)
	public String reserve(final @PathVariable("eventId") Integer eventId,
						  final @PathVariable("graphicId") Integer graphicId,
						  final RedirectAttributes redirectAttrs) {
		try {
			Optional<Event> ev = eventService.getEventById(eventId);
			if(ev.isPresent()){
				Event event = ev.get();
				event.setGraphic(graphicService.findOne(graphicId));
				event.setEnumeratedEventState(IN_PROGRESS);
				UserDecision rejected = decisionService.findOne(UserDecision.REJECTED);
				UserDecision accepted = decisionService.findOne(UserDecision.ACCEPTED);
				UserDecision invited = decisionService.findOne(UserDecision.INVITED);
				event.getUsersEvent().stream()
					.filter(ue -> ue.getInviter() != null
							&& (ue.getDecision().equals(rejected) || ue.getDecision().equals(accepted)))
					.forEach(ue -> {
						ue.setDecision(invited);
					});

				event = eventService.save(event);
				userNotificationsService.eventGraphicChangedByOrganizer(event);
				if(eventToApproveService.findByEvent(event).isPresent()){
					eventToApproveService.removeEventFromWaitingForCheckByManager(event);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		redirectAttrs.addFlashAttribute("saved","true");
		return "redirect:/events/edit/" + eventId;
	}

	@RequestMapping(value = "/reserve/{graphicId}", method = RequestMethod.GET)
	public String reserve(final @PathVariable("graphicId") Integer graphicId,
						  final Model model) {
		try {
			Graphic graphicEntity = graphicService.findOne(graphicId);
			Orlik orlik = graphicEntity.getOrlik();	
			List<User> userFriends = friendshipService.getFriends(null, ACCEPT);
			List<RegisterEventUser> users = new ArrayList<>();
	
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
			User animator = orlik.getAnimator();
			model.addAttribute("animator", animator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("saved", "true");
		return "reserve";
	}
}