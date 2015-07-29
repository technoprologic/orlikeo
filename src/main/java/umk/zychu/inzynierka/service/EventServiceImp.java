package umk.zychu.inzynierka.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventUser;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.EventState;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.model.UserEventRole;
import umk.zychu.inzynierka.repository.EventDaoRepository;

@Service
@Transactional
public class EventServiceImp implements EventService {

	@Autowired
	EventDaoRepository eventDAO;
	@Autowired
	UserEventService userEventService;
	@Autowired
	GraphicService graphicService;
	@Autowired
	UserService userService;
	@Autowired
	UserEventDecisionService decisionService;
	@Autowired
	UserEventRoleService roleService;
	@Autowired
	EventStateService stateService;

	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(EventServiceImp.class);

	@Override
	public Optional<Event> getEventById(Integer id) {
		eventDAO.findOne(id);
		return Optional.ofNullable(eventDAO.findOne(id));
	}

	@Override
	public Event registerEventForm(RegisterEventForm form) {
		try {
			User userOrganizer = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
			Graphic graphic = graphicService.findOne(form.getGraphicId());
			EventState state = stateService.findOne(EventState.IN_PROGRESS);
			Integer playersLimit = form.getUsersLimit();
			Event event = new Event(userOrganizer, graphic, state, playersLimit);
			UserEventRole organizerRole = roleService.findOne(UserEventRole.ORGANIZER);
			UserDecision organizerDecision = decisionService.findOne(UserDecision.ACCEPTED);
			UserEvent organizerUserEvent = new UserEvent(userOrganizer, organizerRole, organizerDecision, true, event, null);
			List<UserEvent> usersEvents = new LinkedList<UserEvent>();
			usersEvents.add(organizerUserEvent);
			ArrayList<RegisterEventUser> regUsersList = (ArrayList<RegisterEventUser>)form.getEventFormMembers();
			for (RegisterEventUser regEventUser : regUsersList) {
				if (regEventUser.getAllowed() || regEventUser.getInvited()) {
					User userTarget = userService.getUser(regEventUser.getEmail());
					UserDecision decision = (regEventUser.getInvited()) ? decisionService
							.findOne(UserDecision.INVITED)
							: decisionService.findOne(UserDecision.NOT_INVITED);
					UserEventRole role = roleService.findOne(UserEventRole.GUEST);
					Boolean permission = regEventUser.getAllowed();
					UserEvent ue = new UserEvent(userTarget, role, decision, permission, event, userOrganizer);
					usersEvents.add(ue);
				}
			}
			event.setUsersEvent(usersEvents);
			return eventDAO.save(event);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<UserGameDetails> getGamesDetailsByStateId(User user,
			Integer stateId) {
		EventState state = stateService.findOne(stateId);
		List<UserEvent> userEvents = user.getUserEvents().stream()
			.filter(ue -> ue.getEvent().getState().equals(state))
			.collect(Collectors.toList());
		return generateUserGameDetailsList(userEvents);
	}
	
	@Override
	public List<UserGameDetails> getGamesDetails(User user) {
		List<UserEvent> userEvents = user.getUserEvents();	
		return generateUserGameDetailsList(userEvents);
	}

	@Override
	public UserGameDetails getGameDetails(Event event) {	
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		UserEvent userEvent = user.getUserEvents().stream()
			.filter(ue ->ue.getEvent().equals(event))
			.findFirst()
			.get();
		return	generateUserGameDetails(userEvent);
	}
	
	@Override
	public List<UserGameDetails> getGamesDetailsByRoleId(User user,
			Integer roleId) {
		UserEventRole role = roleService.findOne(roleId);
		List<UserEvent> userEvents = user.getUserEvents().stream()
			.filter(ue -> ue.getRole().equals(role))
			.collect(Collectors.toList());
		return generateUserGameDetailsList(userEvents);
	}

	@Override
	public List<UserGameDetails> getGamesDetailsByRoleIdAndStateId(User user,
			Integer roleId, Integer stateId) {
		UserEventRole role = roleService.findOne(roleId);
		EventState state = stateService.findOne(stateId);
		List<UserEvent> userEvents = user.getUserEvents().stream()
			.filter(ue -> ue.getRole().equals(role)
						&& ue.getEvent().getState().equals(state))
			.collect(Collectors.toList());
		return generateUserGameDetailsList(userEvents);
	}

	@Override
	public Boolean isEventMember(Event event) {
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUser(userEmail);
		Optional<UserEvent> userEventOpt = user.getUserEvents().stream()
			.filter(ue ->ue.getEvent().equals(event))
			.findFirst();
		
		if(userEventOpt.isPresent()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public User getEventOrganizerUser(Event event) {
		UserEventRole role = roleService.findOne(UserEventRole.ORGANIZER);
		Optional<User> eventOrganizerOpt = event.getUsersEvent().stream()
			.filter(ue -> ue.getRole().equals(role) 
					&& ue.getEvent().equals(event))
			.map(ue ->ue.getUser())
			.findFirst();
		if(eventOrganizerOpt.isPresent()){
			return eventOrganizerOpt.get();
		}
		else{
			return null;
		}
	}

	@Override
	public void deleteEventById(Integer id) throws IllegalArgumentException{
		if(eventDAO.exists(id)){
			Event event = eventDAO.findOne(id);
			event.getUsersEvent().forEach(ue -> userEventService.delete(ue));
			eventDAO.delete(event);
		}
	}

	@Override
	public void updateEvent(RegisterEventForm form) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		Event event = eventDAO.findOne(form.getEventId());
		// remove userOrganizer && editing user
		form.getEventFormMembers()
			.removeIf(reu -> reu.getEmail().equals(event.getUserOrganizer().getEmail()) || reu.getEmail().equals(user.getEmail())); 
		// remove all who's !invited && has !rights && their && !has userEvent
		form.getEventFormMembers()
			.removeIf(reu -> !reu.getAllowed() 
						&& !reu.getInvited() 
						&& !userEventService.findOne(event, userService.getUser(reu.getEmail()))
			.isPresent());
		if(!event.getUserOrganizer().equals(user)){
			form.getEventFormMembers()
				.removeIf(reu -> userEventService.findOne(event, userService.getUser(reu.getEmail())).isPresent() 
							&& !userEventService.findOne(event, userService.getUser(reu.getEmail())).get().getInviter().equals(user));
		}else{
			event.setPlayersLimit(form.getUsersLimit());
			eventDAO.save(event);
		}
		updateUsersEvents(form.getEventFormMembers(), event);
	}

	private void updateUsersEvents(List<RegisterEventUser> eventFormMembers, Event event) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());

		// are still event members
		eventFormMembers
				.stream()
				.filter(reu -> reu.getAllowed() || reu.getInvited())
				.forEach(
						reu -> {
							User u = userService.getUser(reu.getEmail());
							Optional<UserEvent> ueOpt = userEventService
									.findOne(event, u);
							if (ueOpt.isPresent()) {
								UserEvent ue = ueOpt.get();
								ue.setInviter(user);
								if (reu.getAllowed() && reu.getInvited()) {
									ue.setUserPermission(true);
									ue.setDecision(decisionService
											.findOne(UserDecision.INVITED));
								} else if (!reu.getAllowed()
										&& reu.getInvited()) {
									ue.setUserPermission(false);
									ue.setDecision(decisionService
											.findOne(UserDecision.INVITED));
								} else if (reu.getAllowed()
										&& !reu.getInvited()) {
									ue.setUserPermission(true);
									ue.setDecision(decisionService
											.findOne(UserDecision.NOT_INVITED));
								}
								userEventService.save(ue);
							}
						});
		//are new to the event
		eventFormMembers.stream()
								.filter(reu -> (reu.getAllowed() || reu.getInvited())
										&& !userEventService.findOne(event, userService.getUser(reu.getEmail()))
										.isPresent())
								.forEach(reu -> {
									User u = userService.getUser(reu.getEmail());
									UserEventRole role = roleService.findOne(UserEventRole.GUEST);
									UserDecision decision = decisionService.findOne(UserDecision.NOT_INVITED);
									Boolean permission = false;
									if(reu.getAllowed() && reu.getInvited()){
										decision = decisionService.findOne(UserDecision.INVITED);
										permission = true;
									}else if(!reu.getAllowed() && reu.getInvited()){
										decision = decisionService.findOne(UserDecision.INVITED);
										permission = false;
									}else if(reu.getAllowed() && !reu.getInvited()){
										decision = decisionService.findOne(UserDecision.NOT_INVITED);
										permission = true;
									}
									UserEvent ue = new UserEvent (u, role, decision, permission, event, user);
									userEventService.save(ue);
								});
		// are no longer event members
		eventFormMembers
				.stream()
				.filter(reu -> !reu.getAllowed() && !reu.getInvited())
				.forEach(
						reu -> {
							User u = userService.getUser(reu.getEmail());
							Optional<UserEvent> ueOpt = userEventService
									.findOne(event, u);
							if (ueOpt.isPresent()) {
								UserEvent ue = ueOpt.get();
								event.getUsersEvent().remove(ue);
								eventDAO.save(event);
							}
						});
		
	}

	@Override
	public Event save(Event event) {
		return eventDAO.saveAndFlush(event);
	}
		
	private List<UserGameDetails> generateUserGameDetailsList(List<UserEvent> userEvents) {
		List<UserGameDetails> userGamesDetailsList = new LinkedList<UserGameDetails>();
		Iterator<UserEvent> it = userEvents.iterator();
		while(it.hasNext()){
			UserEvent userEvent = it.next();
			UserGameDetails userGameDetails = generateUserGameDetails(userEvent);
			userGamesDetailsList.add(userGameDetails);
		}
		return userGamesDetailsList;
	}

	private UserGameDetails generateUserGameDetails(UserEvent userEvent) {
		UserDecision accept = decisionService.findOne(UserDecision.ACCEPTED);
		UserDecision not_invited = decisionService.findOne(UserDecision.NOT_INVITED);
		Event event = userEvent.getEvent();
		Integer orlikId = 0;
		String address = "";
		String city = "";
		Date startDate = null;
		Date endDate = null;
		Boolean lights = false, water = false, shower = false;
		String shoes = "-";
		if(event.getGraphic() != null){
			Graphic graphic = event.getGraphic();
			startDate = graphic.getStartTime();
			endDate = graphic.getEndTime();
			Orlik orlik = graphic.getOrlik();
			orlikId = orlik.getId();
			address = orlik.getAddress();
			city = orlik.getCity();
			lights = orlik.getLights();
			water = orlik.getWater();
			shower = orlik.getShower();
			shoes = orlik.getShoes();
		}
		Integer eventId = event.getId();
		Integer stateId = event.getState().getId();
		String organizerEmail = event.getUserOrganizer().getEmail();
		Integer decisionId = userEvent.getDecision().getId();
		Integer roleId = userEvent.getRole().getId();
		Boolean permission = userEvent.getUserPermission();
		long willCome = event.getUsersEvent().stream()
								.filter(ue-> ue.getDecision().equals(accept))
								.count();
		int playersLimit = event.getPlayersLimit();
		long invited = event.getUsersEvent().stream()
								.filter(ue -> !ue.getDecision().equals(not_invited))
								.count();
		return  new UserGameDetails(
				eventId, 
				stateId, 
				orlikId,
				address, 
				city,
				organizerEmail,
				startDate,
				endDate,
				decisionId,
				roleId,
				permission,
				playersLimit, 
				willCome,
				invited,
				lights,
				water,
				shower,
				shoes);
	}

	@Override
	public List<EventWindowBlock> getEventWindowBlocks(UserEventRole role) {
		List<EventWindowBlock> windowBlocks = new ArrayList<EventWindowBlock>();
		List<EventState> states = stateService.findAll();
		for (EventState state : states) {
			windowBlocks.add(getBlock(state, role, false));
		}
		windowBlocks.add(getBlock(stateService.findOne(EventState.APPROVED), role, true));
		Collections.sort(windowBlocks, new Comparator<EventWindowBlock>(){
			@Override
			public int compare(EventWindowBlock lhs, EventWindowBlock rhs){
				return rhs.getDisplayOrder() - lhs.getDisplayOrder();
			}
		});
		return windowBlocks;
	}

	private EventWindowBlock getBlock(EventState state, UserEventRole role,
			Boolean incoming) {
		User user = userService.getUser(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		List<UserEvent> userEvents = new ArrayList<UserEvent>();
		userEvents.addAll(user.getUserEvents());
		if (state != null)
			userEvents.removeIf(ue -> !ue.getEvent().getState().equals(state));
		if (role != null)
			userEvents.removeIf(ue -> !ue.getRole().equals(role));
		if (incoming) {
			Date now = new Date();
			System.out.println(now.getDate());
			long end = now.getTime() + 172400000;
			Date endDate = new Date(end);
			Date todayAndTomorrow = new Date(endDate.getYear(),
					endDate.getMonth(), endDate.getDate());
			userEvents.removeIf(ue -> !(ue.getEvent().getGraphic()
					.getStartTime().after(now) && ue.getEvent().getGraphic()
					.getStartTime().before(todayAndTomorrow)));
		}
		Collections.sort(userEvents, new Comparator<UserEvent>() {
			@Override
			public int compare(UserEvent lhs, UserEvent rhs) {
				// return 1 if rhs should be before lhs
				// return -1 if lhs should be before rhs
				// return 0 otherwise
				Graphic lgraphic = lhs.getEvent().getGraphic();
				Graphic rgraphic = rhs.getEvent().getGraphic();
				if(lgraphic == null || rgraphic == null){
					if(lgraphic == null && rgraphic == null)
						return 0;
					else if(lgraphic == null)
						return 1;
					else
						return -1;
				}
				if (rgraphic.getStartTime()
						.before(lgraphic.getStartTime()))
					return 1;
				else if (lgraphic.getStartTime()
						.before(rgraphic.getStartTime()))
					return -1;
				else
					return 0;
			}
		});
		Event event = !userEvents.isEmpty() ? userEvents.get(0).getEvent() : null; 
		Graphic graphic = event != null ? event.getGraphic() : null;
		Orlik orlik = graphic != null ? graphic.getOrlik() : null;
		UserDecision decision = decisionService.findOne(UserDecision.ACCEPTED);
		long goingToCome = event != null ? event.getUsersEvent().stream()
												.filter(ue -> ue.getDecision().equals(decision)).count() : 0;
		String address = orlik != null ? orlik.getAddress() : "brak";
		String city = orlik != null ? orlik.getCity() : "brak";
		Date startTime = graphic != null ? graphic.getStartTime() : null;
		Date endTime = graphic != null ? graphic.getEndTime() : null;
		Integer playersLimit = event != null ? event.getPlayersLimit() : 0;
		Integer size = userEvents.size();
		String label;
		Integer displayOrder;
		switch (state.getId()) {
		case 1:
			label = "Kosz";
			displayOrder = 0;
			address = "brak orlika";
			break;
		case 2:
			label = "W budowie";
			displayOrder = 1;
			break;
		case 3:
			label = "Do akceptacji";
			displayOrder = 2;
			break;
		case 4:
			label = "Zagrożony";
			displayOrder = 3;
			break;
		case 5:
			if (incoming) {
				label = "Przyjęty";
				displayOrder = 4;
			} else {
				label = "Nadchodzący";
				displayOrder = 5;
			}
			break;
		default:
			label = "";
			displayOrder = -1;
			break;
		}

		EventWindowBlock block = new EventWindowBlock(label, displayOrder,
				state.getId(), address, city, startTime, endTime, playersLimit,
				goingToCome, size, incoming);
		return block;
	}	
}
