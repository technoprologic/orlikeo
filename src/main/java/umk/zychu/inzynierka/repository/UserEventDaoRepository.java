package umk.zychu.inzynierka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.controller.DTObeans.UsersEventDetail;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.model.UserEvent;

@Transactional
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
	
	
	
	
	
	public static final String DELETE_USER_EVENT_BY_EVENT_ID = "DELETE FROM UserEvent ue WHERE ue.eventId = :id";
	@Modifying
	@Query(DELETE_USER_EVENT_BY_EVENT_ID)
	void removeUsersEventsByEventId(@Param("id") long id);
	
	
	
	
	
	public static final String UPDATE_USER_EVENT = "UPDATE UserEvent ue SET ue.userPermission = :allowed, ue.userDecision = :invited WHERE ue.userId = :userId AND ue.eventId = :eventId";
	@Modifying
	@Query(UPDATE_USER_EVENT)
	void updateUserEvent(@Param("eventId") long eventId, @Param("userId") long id, @Param("allowed") boolean b, @Param("invited") long invitation);
	
	
	
	
	
	
	public static final String DELETE_USER_EVENT = "DELETE FROM UserEvent ue WHERE ue.eventId = :eventId AND ue.userId = :id";
	@Modifying
	@Query(DELETE_USER_EVENT)
	void removeUserEventByEventId(@Param("id") long id, @Param("eventId") long eventId);

	
	
	public static final String CHECK_IF_USER_IS_INVITED = "SELECT COUNT(ue.id) FROM UserEvent ue WHERE ue.userId = :userId AND ue.eventId = :eventId";
	@Query(CHECK_IF_USER_IS_INVITED)
	int ifUserEventExists(@Param("eventId") long eventId, @Param("userId") long id);
	
	
	
	
	
	
	
}
