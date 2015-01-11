package umk.zychu.inzynierka.repository;

import java.util.List;

import umk.zychu.inzynierka.model.Event;

public interface EventDAO {
	Event getEventById(int id);
	List<Event> getEventsByOrlikId(int id);
}
