package umk.zychu.inzynierka.service;

import java.util.List;

import umk.zychu.inzynierka.controller.DTObeans.UsersEventDetail;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.UserEvent;

public interface UserEventService {
	List<Object[]> getJoinTest(long id);
	List<UsersEventDetail> getUsersEventDetail(Event event);
	void update(UserEvent ue);

	
}
