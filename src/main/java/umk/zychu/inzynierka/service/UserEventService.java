package umk.zychu.inzynierka.service;

import java.util.List;

import umk.zychu.inzynierka.controller.DTObeans.UsersEventDetail;
import umk.zychu.inzynierka.model.Event;

public interface UserEventService {
	List<Object[]> getJoinTest(long id);
	List<UsersEventDetail> getUsersEventdetail(Event event);

	
}
