package umk.zychu.inzynierka.service;

import java.util.List;

import umk.zychu.inzynierka.model.Event;

public interface EventService {
	List<Event> getOrlikEvents(int id);
}
