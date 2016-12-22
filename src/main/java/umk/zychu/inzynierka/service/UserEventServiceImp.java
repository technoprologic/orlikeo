package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.repository.UserEventDaoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static umk.zychu.inzynierka.model.EnumeratedEventState.*;

@Service
public class UserEventServiceImp implements UserEventService {

	private final int changeStatusBarrier = 5;

	@Autowired
	UserEventDaoRepository userEventDAO;
	@Autowired
	EventService eventService;

	@Autowired
	UserService userService;
	@Autowired
	UserEventDecisionService userEventDecisionService;

	@Autowired
	UserNotificationsService userNotificationsService;

	@Autowired
	EventToApproveService eventToApproveService;

	@Override
	public UserEvent save(UserEvent event) {
		return userEventDAO.save(event);
	}

	@Override
	@Transactional
	public void setUserEventDecision(Event event, UserDecision decision) {
		User user = userService.getUser(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		event.getUsersEvent().stream().filter(ue -> ue.getUser().equals(user))
				.findFirst().ifPresent(ue -> {
					ue.setDecision(decision);
				});
		changeEventStateIfRequired(event);
	}
	
	@Override
	public void changeEventStateIfRequired(Event event) {
		UserDecision accepted = userEventDecisionService.findOne(UserDecision.ACCEPTED);
		EnumeratedEventState beforeState = event.getEnumeratedEventState();
		long counter = event.getUsersEvent().stream()
				.filter(ue -> ue.getDecision().equals(accepted))
				.count();
		if(event.getEnumeratedEventState().equals(EnumeratedEventState.IN_PROGRESS) && counter >= changeStatusBarrier) {
			event.setEnumeratedEventState(READY_TO_ACCEPT);
			eventToApproveService.addEventToCheckByManager(event);
		}else if(event.getEnumeratedEventState().equals(READY_TO_ACCEPT) && counter < changeStatusBarrier){
			event.setEnumeratedEventState(IN_PROGRESS);
			Optional<EventToApprove> optEventToApprove = eventToApproveService.findByEvent(event);
			if(optEventToApprove.isPresent()){
				EventToApprove evenToAprrove = optEventToApprove.get();
				eventToApproveService.delete(evenToAprrove);
			}
		}else if(event.getEnumeratedEventState().equals(APPROVED) && counter < changeStatusBarrier){
			event.setEnumeratedEventState(THREATENED);
			//TODO set time to find player, if not change state to IN_PROGRESS
		}else if (event.getEnumeratedEventState().equals(THREATENED) && counter >= changeStatusBarrier){
			event.setEnumeratedEventState(APPROVED);
		}
		if(!beforeState.equals(event.getEnumeratedEventState())){
			userNotificationsService.eventStateChanged(event);
		}
		eventService.save(event);
	}

	@Override
	public List<User> findUsersByEventAndDecision(Event event, UserDecision decision) {
		List<User> members = event.getUsersEvent().stream()
				.filter((x) -> x.getDecision().equals(decision))
				.map(UserEvent::getUser)
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
				.map(UserEvent::getUser)
				.collect(Collectors.toList());
		return users;
	}

	@Override
	@Modifying
	public void delete(UserEvent userEvent) {
		UserEvent tmp = userEventDAO.findOne(userEvent.getId());
		userEventDAO.delete(userEvent);
	}	
}
