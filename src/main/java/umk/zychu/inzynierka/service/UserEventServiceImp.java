package umk.zychu.inzynierka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umk.zychu.inzynierka.controller.DTObeans.UsersEventDetail;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.repository.UserEventDaoRepository;


@Service
public class UserEventServiceImp implements UserEventService {

	@Autowired
	UserEventDaoRepository userEventDAO;

	@Override
	public List<Object[]> getJoinTest(long id) {
		return userEventDAO.getJoinTest(id);
	}

	
	
	
	
	
	
	
	
	@Override
	public List<UsersEventDetail> getUsersEventDetail(Event event) {
		
		return userEventDAO.getUsersEventDetail(event);
	}









	@Override
	public void update(UserEvent ue) {
		// TODO Auto-generated method stub
		userEventDAO.update(ue.getId(), ue.getRoleId(), ue.getUserDecision(), ue.getUserPermission());
	}

	
	
	
	
	
	
	
	
	
	
	

	

	
	
	
}
