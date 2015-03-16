package umk.zychu.inzynierka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import umk.zychu.inzynierka.controller.DTObeans.UsersEventDetail;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.model.UserEvent;

public interface UserEventDaoRepository extends BaseRepository<UserEvent, Long>{

	final String USER_EVENTS = "SELECT e FROM UserEvent e WHERE e.eventId = :id";
	@Query(USER_EVENTS)
	List<UserEvent> getUserEvent(@Param("id") Long id);
	
	
	final String JOIN_TEST = "SELECT u.email, e.id FROM User u JOIN u.userEvents e WHERE e.eventId = :id ";
	@Query(JOIN_TEST)
	List<Object[]> getJoinTest(@Param("id") long id);
	
	
	public final static String USERS_EVENT_DETAILS = "SELECT new umk.zychu.inzynierka.controller.DTObeans.UsersEventDetail(e, u) FROM UserEvent e JOIN e.user u WHERE e.event = :event";
	@Query(USERS_EVENT_DETAILS)
	List<UsersEventDetail> getUsersEventDetail(@Param("event") Event event);
	
	
}
