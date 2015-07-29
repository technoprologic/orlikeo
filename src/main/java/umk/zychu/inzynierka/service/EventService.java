package umk.zychu.inzynierka.service;

import java.util.List;
import java.util.Optional;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserEventRole;

public interface EventService {
	Event registerEventForm(RegisterEventForm form);
	Optional<Event> getEventById(Integer id);
	UserGameDetails getGameDetails(Event event);
	List<UserGameDetails> getGamesDetails(User user);
	List<UserGameDetails> getGamesDetailsByStateId(User user, Integer state);
	List<UserGameDetails> getGamesDetailsByRoleId(User user, Integer roleId);
	List<UserGameDetails> getGamesDetailsByRoleIdAndStateId(User user, Integer role, Integer stateId);
	Boolean isEventMember(Event event);
	User getEventOrganizerUser(Event event);
	void deleteEventById(Integer id);
	void updateEvent(RegisterEventForm form);
	Event save(Event event);
	List<EventWindowBlock> getEventWindowBlocks(UserEventRole role);
}
