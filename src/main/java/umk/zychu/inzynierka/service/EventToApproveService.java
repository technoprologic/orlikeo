package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.EventToApprove;

import java.util.List;
import java.util.Optional;

public interface EventToApproveService {
	EventToApprove save(EventToApprove eventToApprove);
	List<EventToApprove> findAll();
	Optional<EventToApprove> findByEvent(Event event);
	void delete(EventToApprove evenToAprrove);
	EventToApprove addEventToCheckByManager(Event event);
	void removeEventFromWaitingForCheckByManager(Event event);
	void setNotificationsChecked();
}
