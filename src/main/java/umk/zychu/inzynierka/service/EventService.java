package umk.zychu.inzynierka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import umk.zychu.inzynierka.controller.DTObeans.CreatedEventDetails;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.controller.DTObeans.UserGameInfo;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserEvent;

public interface EventService {

	List<Graphic> getOrlikGraphicByOrlik(Orlik orlik);
	void saveUserEvent(UserEvent event);
	Event registerEventForm(RegisterEventForm form);
	Optional<Event> getEventById(long id);
	List<UserEvent> getUserEvent(long id);
	
	List<CreatedEventDetails> getEventAndGraphicAndOrlikByEvent(Event event);
	
	List<UserGameInfo> getAllUserEventsByState(User user, long stateId);
	List<UserGameDetails> getGamesDetails(User user);
	List<EventWindowBlock> getEventsBlockWindowList(User user);
	List<UserGameDetails> getGamesDetailsByStateId(User user, long state);
	List<EventWindowBlock> getEventsBlockWindowByRoleList(User user, long roleId);
	List<UserGameDetails> getGamesDetailsByRoleId(User user, long roleId);
	List<UserGameDetails> getGamesDetailsByRoleIdAndStateId(User user, long roleId, long stateId);
	int isInvitedOnTheEvent(User user, long eventId);
	void setJoinDecision(long userId, long eventId);
	void setQuitDecision(long userId, long eventId);
}
