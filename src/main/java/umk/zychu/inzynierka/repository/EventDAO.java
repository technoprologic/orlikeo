package umk.zychu.inzynierka.repository;

import java.util.List;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.GraphicEntity;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.UserEvent;

public interface EventDAO {
	Event getEventById(long id);
	List<GraphicEntity> getOrlikGraphicByOrlik(Orlik orlik);
	GraphicEntity getGraphicEntityById(long id);
	void saveUserEvent(UserEvent event);
	void saveEvent(Event event);
	List<UserEvent> getUserEvent(long id);
	List<Event> getEventsWithStateByUserName(String username, long state);
	
	
	
}
