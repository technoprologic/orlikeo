package umk.zychu.inzynierka.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.controller.DTObeans.CreatedEventDetails;
import umk.zychu.inzynierka.controller.DTObeans.EditEventForm;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventUser;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.controller.DTObeans.UserGameInfo;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.repository.EventDaoRepository;
import umk.zychu.inzynierka.repository.GraphicDaoRepository;
import umk.zychu.inzynierka.repository.UserDaoRepository;
import umk.zychu.inzynierka.repository.UserEventDaoRepository;
import umk.zychu.inzynierka.util.UserEventDecision;
import umk.zychu.inzynierka.util.UserEventRole;

@Service
public class EventServiceImp implements EventService{
	
	@Autowired
	EventDaoRepository eventDAO;
	
	@Autowired
	UserEventDaoRepository userEventDAO;
	
	@Autowired
	GraphicDaoRepository graphicDAO;
	
	@Autowired
	UserDaoRepository userDAO;
	
	
	@Override
	public Optional<Event> getEventById(long id){
		return eventDAO.findById(id);
	}
	
	
	
	@Override
	public List<Graphic> getOrlikGraphicByOrlik(Orlik orlik){
		return graphicDAO.getOrlikGraphicByOrlik(orlik);
	}
	


	@Override
	public void saveUserEvent(UserEvent event) {
		userEventDAO.save(event);
	}

	
	
	@Override
	public Event registerEventForm(RegisterEventForm form) {
		
		int STATE=2;
			
		try{
			
			Event event = new Event(form.getGraphicId(),form.getUsersLimit(), STATE);
			eventDAO.save(event);
			
			UserEvent organizerUserEvent = new UserEvent();
			organizerUserEvent.setEventId(event.getId());
			organizerUserEvent.setUserId(userDAO.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
			organizerUserEvent.setRoleId(UserEventRole.ORGANIZER.getValue());
			organizerUserEvent.setUserDecision(UserEventDecision.ACCEPTED.getValue());
			organizerUserEvent.setUserPermission(true);
			userEventDAO.save(organizerUserEvent); 
			
			for(RegisterEventUser regEventUser : (List<RegisterEventUser>)form.getUserFriends()){
				
				if(regEventUser.getAllowed() || regEventUser.getInvited() ){
					UserEvent userEvent = new UserEvent();
					userEvent.setEventId(event.getId());
					userEvent.setUserId(userDAO.getUserByEmail(regEventUser.getEmail()).getId());
					userEvent.setRoleId(UserEventRole.GUEST.getValue());
					userEvent.setInviterId(userDAO.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
					
					if(regEventUser.getInvited()){
						userEvent.setUserDecision(UserEventDecision.INVITED.getValue());
						}
					else { 
						userEvent.setUserDecision(UserEventDecision.NOT_INVITED.getValue());
					}
					
					userEvent.setUserPermission(regEventUser.getAllowed());
					userEventDAO.save(userEvent);
				}	
			}
			return event;
			
		}catch(Exception e){
			System.out.println("Exception : " + e.getMessage());
			return null;
		}
	}

	
	
	@Override
	public List<UserEvent> getUserEvent(long id) {
		return userEventDAO.getUserEvent(id);
	}

	
	
	@Override
	public List<UserGameDetails> getGamesDetailsByStateId(User user, long state){
		
		
		List<UserGameDetails> userGamesDetailsList = eventDAO.getGamesDetailsByStateId(user, state);
		for(UserGameDetails gameDetails :userGamesDetailsList){
			long eventId = gameDetails.getEventId();
			gameDetails.setWillCome(eventDAO.countUsersEventDecisions(eventId, 2));
		}
		return userGamesDetailsList;
	}

	
	
	@Override
	public List<UserGameDetails> getGamesDetails(User user){
		
		List<UserGameDetails> userGamesDetailsList = eventDAO.getGamesDetails(user);
		for(UserGameDetails gameDetails :userGamesDetailsList){
			long eventId = gameDetails.getEventId();
			gameDetails.setWillCome(eventDAO.countUsersEventDecisions(eventId, 2));
		}
		
		return userGamesDetailsList;
	}
	
	
	
	@Override
	public CreatedEventDetails getEventAndGraphicAndOrlikByEvent(Event event) {
		
		CreatedEventDetails createdEventDetails = eventDAO.getEventAndGraphicAndOrlikByEvent(event);
		createdEventDetails.setInvitedPlayers(eventDAO.countInvitedPlayers(createdEventDetails.getEvent()));
		return createdEventDetails;
	}

	
	
	@SuppressWarnings("deprecation")
	@Override
	public List<EventWindowBlock> getEventsBlockWindowList(User user) {

		List<EventWindowBlock> eventWindowBlockList = new ArrayList<EventWindowBlock>();
		/*EventWindowBlock allWithoutGraphic = (List<EventWindowBlock>) eventDAO.getAllWindowBlocksInBasket(user);
		
		
		
		eventWindowBlockList.addAll(allWithoutGraphic);*/
		
		for(int stateId=1; stateId<=5; stateId++){
			try{		
				if(!eventDAO.getWindowBlockInState(user, (long)stateId).isEmpty()){
					EventWindowBlock tmpEventWindowBlock = eventDAO.getWindowBlockInState(user, (long)stateId).get(0);
					long id = tmpEventWindowBlock.getEventId();
					tmpEventWindowBlock.setGoingToCome(eventDAO.countUsersEventDecisions(id, 2));	
					tmpEventWindowBlock.setCountedInSameState(eventDAO.countUserEventsByStateId(user, stateId));
					eventWindowBlockList.add(tmpEventWindowBlock);
				}else{
					eventWindowBlockList.add(null);
				}
			}catch(Exception e){
					System.out.println(e.toString());
				}
		}
		
		try{
			Date now = new Date();
			System.out.println(now.getDate());
			long end = now.getTime() + 172400000;
			Date todayAndTomorrow = new Date(end);
			todayAndTomorrow = new Date(todayAndTomorrow.getYear(), todayAndTomorrow.getMonth(), todayAndTomorrow.getDate());
			
			
			System.out.println(todayAndTomorrow);
			
			if(!eventDAO.getIncomingWindowBlock(user, 5, now, todayAndTomorrow).isEmpty()){
				EventWindowBlock eventWindowBlock = eventDAO.getIncomingWindowBlock(user, 5, now, todayAndTomorrow).get(0);
				long id = eventWindowBlock.getEventId();
				eventWindowBlock.setGoingToCome(eventDAO.countUsersEventDecisions(id, 2));
				eventWindowBlock.setCountedInSameState(eventDAO.countIncomingUserGames(user, 5, now, todayAndTomorrow));
				eventWindowBlockList.add(eventWindowBlock);
			}else{
				eventWindowBlockList.add(null);
			}	
		}catch(Exception e){
			System.out.println(e.toString());
		}	
		
		Collections.reverse(eventWindowBlockList);
		return eventWindowBlockList;
	}


	
	@Override
	public List<UserGameInfo> getAllUserEventsByState(User user, long stateId) {
		return eventDAO.getAllUserGamesInfo(user, stateId);
	}

	
	
	@Override
	public List<EventWindowBlock> getEventsBlockWindowByRoleList(User user, long roleId) {
		List<EventWindowBlock> eventWindowBlockList = new ArrayList<EventWindowBlock>();
		for(int stateId=1; stateId<=5; stateId++){
			try{		
				if(!eventDAO.getWindowBlockInStateByRole(user, (long)stateId, roleId).isEmpty()){
					EventWindowBlock tmpEventWindowBlock = eventDAO.getWindowBlockInStateByRole(user, (long)stateId, roleId).get(0);
					long id = tmpEventWindowBlock.getEventId();
					tmpEventWindowBlock.setGoingToCome(eventDAO.countUsersEventDecisions(id, 2));	
					tmpEventWindowBlock.setCountedInSameState(eventDAO.countUserEventsByStateIdByRoleId(user, stateId, roleId));
					eventWindowBlockList.add(tmpEventWindowBlock);
				}else{
					eventWindowBlockList.add(null);
				}
			}catch(Exception e){
					System.out.println(e.toString());
				}
		}
		
		try{
			Date now = new Date();
			System.out.println(now.getDate());
			long end = now.getTime() + 172400000;
			Date todayAndTomorrow = new Date(end);
			todayAndTomorrow = new Date(todayAndTomorrow.getYear(), todayAndTomorrow.getMonth(), todayAndTomorrow.getDate());
			
			
			System.out.println(todayAndTomorrow);
			
			if(!eventDAO.getIncomingWindowBlockByRoleId(user, 5, now, todayAndTomorrow, roleId).isEmpty()){
				EventWindowBlock eventWindowBlock = eventDAO.getIncomingWindowBlockByRoleId(user, 5, now, todayAndTomorrow, roleId).get(0);
				long id = eventWindowBlock.getEventId();
				eventWindowBlock.setGoingToCome(eventDAO.countUsersEventDecisions(id, 2));
				eventWindowBlock.setCountedInSameState(eventDAO.countIncomingUserGamesByRoleId(user, 5, now, todayAndTomorrow, roleId));
				eventWindowBlockList.add(eventWindowBlock);
			}else{
				eventWindowBlockList.add(null);
			}	
		}catch(Exception e){
			System.out.println(e.toString());
		}	
		
		Collections.reverse(eventWindowBlockList);
		return eventWindowBlockList;
	}



	@Override
	public List<UserGameDetails> getGamesDetailsByRoleId(User user, long roleId) {
		List<UserGameDetails> userGamesDetailsList = eventDAO.getGamesDetailsByRoleId(user, roleId);
		for(UserGameDetails gameDetails :userGamesDetailsList){
			long eventId = gameDetails.getEventId();
			gameDetails.setWillCome(eventDAO.countUsersEventDecisions(eventId, 2));
		}
		
		return userGamesDetailsList;
	}



	@Override
	public List<UserGameDetails> getGamesDetailsByRoleIdAndStateId(User user,
			long roleId, long stateId) {
		List<UserGameDetails> userGamesDetailsList = eventDAO.getGamesDetailsByRoleIdAndStateId(user, roleId, stateId);
		for(UserGameDetails gameDetails :userGamesDetailsList){
			long eventId = gameDetails.getEventId();
			gameDetails.setWillCome(eventDAO.countUsersEventDecisions(eventId, 2));
		}
		return userGamesDetailsList;
	}



	@Override
	public int isInvitedOnTheEvent(User user, long eventId) {
		return eventDAO.isInvitedOnTheEvent(user, eventId);
	}



	@Override
	public void setJoinDecision(long userId, long eventId) {
		eventDAO.setJoinDecision(userId, eventId);
	}



	@Override
	public void setQuitDecision(long userId, long eventId) {
		eventDAO.setQuitDecision(userId, eventId);
		
	}



	@Override
	public String getEventUserOrganizerEmail(Event event) {
		return eventDAO.getUserEventOrganizer(event);
	}



	@Override
	public void deleteEventById(long id) {
		userEventDAO.removeUsersEventsByEventId(id);
		eventDAO.removeEventById(id);	
	}



	@Override
	public void updateEvent(EditEventForm form) {
		
		Event ev = eventDAO.findById(form.getEventId()).get();
		String organizerEmail = eventDAO.getUserEventOrganizer(ev);
		String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		try{
			if(organizerEmail.equals(loggedUserEmail)){
				eventDAO.updateEventPlayersLimit(form.getUsersLimit(), ev.getId());
			}
			for(RegisterEventUser editEventUser : (List<RegisterEventUser>)form.getUserFriends()){
				
				Boolean isOrganizer = editEventUser.getEmail().equals(organizerEmail);
				if (!isOrganizer) {
					
					long formUserId = userDAO.getUserByEmail(
							editEventUser.getEmail()).getId();
					if (editEventUser.getAllowed()
							|| editEventUser.getInvited()) {
						long invited = 0;
						if (editEventUser.getInvited()) {

							invited = 1;

						} else {
							invited = 4;
						}
						if (userEventDAO.ifUserEventExists(form.getEventId(),
								formUserId) > 0) {
							userEventDAO.updateUserEvent(form.getEventId(),
									formUserId, editEventUser.getAllowed(),
									invited);
						} else {
							User logged = userDAO.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
							UserEvent userEvent = new UserEvent(formUserId, 2,
									invited, editEventUser.getAllowed(),
									form.getEventId(), logged.getId());
							userEventDAO.save(userEvent);
						}
					} else {
						userEventDAO.removeUserEventByEventId(formUserId,
								form.getEventId());
					}
				}
			}
		}catch(Exception e){
			System.out.println("Exception  : " + e.getMessage());
		}
	}



	@Override
	public void updateEventGraphic(long eventId, long graphicId) {
		
		Event event = eventDAO.findById(eventId).get();
		event.setGraphicId(graphicId);
		eventDAO.save(event);
		
	}



	@Override
	public Collection<Event> getAllWithGraphic(Graphic entity) {
		// TODO Auto-generated method stub
		return eventDAO.getAllWithGraphic(entity);
	}



	@Override
	public void update(Event event) {
		// TODO Auto-generated method stub
		
		eventDAO.update(event.getId(), event.getStateId(), event.getGraphicId(), event.getPlayersLimit());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
