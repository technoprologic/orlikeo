package umk.zychu.inzynierka.service;

import java.util.List;

import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.GraphicEntity;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.Test;
import umk.zychu.inzynierka.model.UserEvent;

public interface EventService {

	List<GraphicEntity> getOrlikGraphicByOrlik(Orlik orlik);
	GraphicEntity getGraphicEntityById(long id);
	void saveUserEvent(UserEvent event);
	Event registerEventForm(RegisterEventForm form);
	Event getEventById(long id);
	List<UserEvent> getUserEvent(long id);
	List<Event> getUserEvents(String username, long state);
	
	
	void saveTest(Test test);
	Test findText(String text);
}
