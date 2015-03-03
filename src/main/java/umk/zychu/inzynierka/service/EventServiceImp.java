package umk.zychu.inzynierka.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventUser;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.GraphicEntity;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.Test;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.repository.EventDAO;
import umk.zychu.inzynierka.repository.UserDAO;
import umk.zychu.inzynierka.repository.TestRepository;
import umk.zychu.inzynierka.util.UserEventDecision;
import umk.zychu.inzynierka.util.UserEventRole;

@Service
@Transactional
public class EventServiceImp implements EventService{
	
	@Autowired
	EventDAO eventDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	TestRepository testRepository;
	
	@Override
	public Event getEventById(long id){
		return eventDAO.getEventById(id);
	}
	
	@Override
	public List<GraphicEntity> getOrlikGraphicByOrlik(Orlik orlik){
		return eventDAO.getOrlikGraphicByOrlik(orlik);
	}
	
	@Override
	public GraphicEntity getGraphicEntityById(long graphicId){
		return eventDAO.getGraphicEntityById(graphicId);
	}

	@Override
	public void saveUserEvent(UserEvent event) {
		eventDAO.saveUserEvent(event);
	}

	@Override
	public Event registerEventForm(RegisterEventForm form) {
		
		//TODO enum class / userlimit
		int STATE=2;
			
		try{
			
			Event event = new Event(form.getGraphicId(), STATE, userDAO.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
			eventDAO.saveEvent(event);
			
			UserEvent organizerUserEvent = new UserEvent();
			organizerUserEvent.setEventId(event.getId());
			organizerUserEvent.setUserId(userDAO.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
			organizerUserEvent.setUserRole(UserEventRole.ORGANIZER.getValue());
			organizerUserEvent.setUserDecision(UserEventDecision.ACCEPTED.getValue());
			organizerUserEvent.setUserPermission(true);
			eventDAO.saveUserEvent(organizerUserEvent); 
			
			for(RegisterEventUser regEventUser : (List<RegisterEventUser>)form.getUserFriends()){
				
				if(regEventUser.getAllowed() || regEventUser.getInvited()){
					UserEvent userEvent = new UserEvent();
					userEvent.setEventId(event.getId());
					userEvent.setUserId(userDAO.getUserByEmail(regEventUser.getEmail()).getId());
					userEvent.setUserRole(UserEventRole.GUEST.getValue());
					userEvent.setUserDecision(UserEventDecision.INVITED.getValue());
					userEvent.setUserPermission(regEventUser.getInvited());
					eventDAO.saveUserEvent(userEvent);
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
		return eventDAO.getUserEvent(id);
	}
	
	@Override
	public List<Event> getUserEvents(String username, long state){
		return eventDAO.getEventsWithStateByUserName(username, state);
	}
	
	@Override
	public void saveTest(Test test){
		testRepository.save(test);
	}
	
	@Override
	public Test findText(String text){
		return testRepository.findBySmth(text);
	}
	

}
