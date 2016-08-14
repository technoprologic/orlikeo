package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.model.EventState;

import java.util.List;

public interface EventStateService {
	EventState findOne(Integer id);
	List<EventState> findAll();
}
