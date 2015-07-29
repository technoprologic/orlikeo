package umk.zychu.inzynierka.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.repository.EventDaoRepository;
import umk.zychu.inzynierka.repository.EventStateDaoRepository;
import umk.zychu.inzynierka.repository.UserEventDaoRepository;
import umk.zychu.inzynierka.repository.UserEventDecisionDAOrepository;

@Service
public class UserEventServiceImp implements UserEventService {

	@Autowired
	UserEventDaoRepository userEventDAO;
	@Autowired
	EventService eventService;
	@Autowired
	EventStateService eventStateService;
	@Autowired
	UserService userService;
	@Autowired
	UserEventDecisionService userEventDecisionService;

	@Override
	public UserEvent save(UserEvent event) {
		return userEventDAO.save(event);
	}

	@Override 
	public void setUserEventDecision(Event event, UserDecision decision) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		user.getUserEvents().stream()
							.filter(ue -> ue.getEvent().equals(event))
							.findFirst()
							.ifPresent(ue -> 
							{ 
								ue.setDecision(decision);
								userEventDAO.save(ue);
							});
	}
	
	@Override
	public List<User> findUsersByEventAndDecision(Event event, UserDecision decision) {
		List<User> members = event.getUsersEvent().stream()
				.filter((x) -> x.getDecision().equals(decision))
				.map(ue -> ue.getUser())
				.collect(Collectors.toList());
		return members;
	}

	@Override
	public Optional<UserEvent> findOne(Event event, User user) {
		return event.getUsersEvent().stream()
				.filter(ue -> ue.getUser().equals(user))
				.findFirst();
	}

	@Override
	public List<User> findUsersByEventAndPermission(Event event,
			Boolean canInvite) {
		List<User> users = event.getUsersEvent().stream()
				.filter(ue -> ue.getUserPermission() == canInvite)
				.map(ue ->ue.getUser())
				.collect(Collectors.toList());
		return users;
	}

	@Override
	@Modifying
	public void delete(UserEvent userEvent) {
		System.out.println("xxx");
		UserEvent tmp = userEventDAO.findOne(userEvent.getId());
		System.out.println("equals? : " + tmp.equals(userEvent) + tmp.hashCode() + " : " + userEvent.hashCode());
		userEventDAO.delete(userEvent);
		System.out.println("xxx");
	}	
}
