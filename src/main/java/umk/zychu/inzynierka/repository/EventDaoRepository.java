package umk.zychu.inzynierka.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.controller.DTObeans.CreatedEventDetails;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.controller.DTObeans.UserGameInfo;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.User;

public interface EventDaoRepository extends BaseRepository<Event, Long>{
	

	public final static String GET_ALL_USER_EVENTS = "SELECT "
			+ "new umk.zychu.inzynierka.controller.DTObeans.UserGameDetails(e.id, e.stateId, ue, o.address, g, e.playersLimit) "
			+ "FROM Event e JOIN e.graphic g JOIN g.orlik o JOIN e.usersEvent ue WHERE ue.user= :user ORDER BY g.startTime ASC";
	@Query(GET_ALL_USER_EVENTS)
	List<UserGameDetails> getGamesDetails(@Param("user") User user);
	
	
	public final static String GET_ALL_USER_EVENTS_BY_STATE = "SELECT "
			+ "new umk.zychu.inzynierka.controller.DTObeans.UserGameDetails(e.id, e.stateId, ue, o.address, g, e.playersLimit) "
			+ "FROM Event e JOIN e.graphic g JOIN g.orlik o JOIN e.usersEvent ue WHERE ue.user= :user AND e.stateId = :state ORDER BY g.startTime ASC";
	@Query(GET_ALL_USER_EVENTS_BY_STATE)
	List<UserGameDetails> getGamesDetailsByStateId(@Param("user") User user, @Param("state") long state);
	
	
	
	public final static String GET_ALL_USER_EVENTS_BY_ROLE = "SELECT "
			+ "new umk.zychu.inzynierka.controller.DTObeans.UserGameDetails(e.id, e.stateId, ue, o.address, g, e.playersLimit) "
			+ "FROM Event e JOIN e.graphic g JOIN g.orlik o "
			+ "JOIN e.usersEvent ue WHERE ue.user= :user AND ue.roleId = :roleId ORDER BY g.startTime ASC";
	@Query(GET_ALL_USER_EVENTS_BY_ROLE)
	List<UserGameDetails> getGamesDetailsByRoleId(@Param("user") User user, @Param("roleId") long roleId);
	
	
	
	public final static String GET_ALL_USER_EVENTS_BY_ROLE_AND_STATE = "SELECT "
			+ "new umk.zychu.inzynierka.controller.DTObeans.UserGameDetails(e.id, e.stateId, ue, o.address, g, e.playersLimit) "
			+ "FROM Event e JOIN e.graphic g JOIN g.orlik o JOIN e.usersEvent ue "
			+ "WHERE ue.user= :user AND ue.roleId = :roleId AND e.stateId = :stateId "
			+ "ORDER BY g.startTime ASC";
	@Query(GET_ALL_USER_EVENTS_BY_ROLE_AND_STATE)
	List<UserGameDetails> getGamesDetailsByRoleIdAndStateId(@Param("user") User user, @Param("roleId") long roleId, @Param("stateId") long stateId);
	
	
	
	public final static String JOIN_EVENT_GRAPHIC_ORLIK = "SELECT new umk.zychu.inzynierka.controller.DTObeans.CreatedEventDetails(e, g, o) " 
			+ "FROM Event e JOIN e.graphic g JOIN g.orlik o WHERE e = :event";
	@Query(JOIN_EVENT_GRAPHIC_ORLIK)
	List<CreatedEventDetails> getEventAndGraphicAndOrlikByEvent(@Param("event") Event event);
	
	
	
	public final static String ALL_USER_GAMES_INFO_IN_STATE = "SELECT new umk.zychu.inzynierka.controller.DTObeans.UserGameInfo(e, g, o ) " 
			+ "FROM Event e JOIN e.graphic g JOIN g.orlik o JOIN e.usersEvent ue WHERE ue.user = :user AND e.stateId = :stateId ORDER BY g.startTime)";
	@Query(ALL_USER_GAMES_INFO_IN_STATE)
	List<UserGameInfo> getAllUserGamesInfo(@Param("user") User user, @Param("stateId") long stateId);
	

	
	public final static String USER_GAMES_INFO_IN_STATE = "SELECT new umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock(e.id, e.stateId,  o.address, o.city, g.startTime, g.endTime, e.playersLimit )  " 
			+ "FROM Event e JOIN e.graphic g JOIN g.orlik o JOIN e.usersEvent ue WHERE ue.user = :user AND e.stateId = :stateId ORDER BY g.startTime ASC)";
	@Query(USER_GAMES_INFO_IN_STATE)
	List<EventWindowBlock> getWindowBlockInState(@Param("user") User user, @Param("stateId") Long stateId);
	
	
	
	public final static String USER_GAMES_INFO_IN_STATE_BY_ROLE = "SELECT new umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock(e.id, e.stateId,  o.address, o.city, g.startTime, g.endTime, e.playersLimit )  " 
			+ "FROM Event e JOIN e.graphic g JOIN g.orlik o JOIN e.usersEvent ue WHERE ue.user = :user AND e.stateId = :stateId AND ue.roleId = :roleId ORDER BY g.startTime ASC)";
	@Query(USER_GAMES_INFO_IN_STATE_BY_ROLE)
	List<EventWindowBlock> getWindowBlockInStateByRole(@Param("user") User user, @Param("stateId") Long stateId, @Param("roleId") long roleId);
	
	
	
	public final static String COUNT_USER_EVENTS_BY_STATE = "SELECT COUNT(e.id) FROM Event e JOIN e.usersEvent ue JOIN ue.user u WHERE e.stateId = :state AND u = :user";
	@Query(COUNT_USER_EVENTS_BY_STATE)
	int countUserEventsByStateId(@Param("user") User user, @Param("state") long state);
	
	
	
	public final static String COUNT_USER_EVENTS_BY_STATE_BY_ROLE = "SELECT COUNT(e.id) FROM Event e JOIN e.usersEvent ue JOIN ue.user u "
			+ "WHERE e.stateId = :state AND u = :user AND ue.roleId = :roleId";
	@Query(COUNT_USER_EVENTS_BY_STATE_BY_ROLE)
	int countUserEventsByStateIdByRoleId(@Param("user") User user, @Param("state") long state, @Param("roleId") long roleId);
	
	
	
	public final static String INCOMING_USER_GAMES = "SELECT new umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock(e.id, e.stateId,  o.address, o.city, g.startTime, g.endTime, e.playersLimit )  " 
			+ "FROM Event e JOIN e.graphic g JOIN g.orlik o JOIN e.usersEvent ue WHERE ue.user = :user AND e.stateId = :stateId AND g.startTime BETWEEN :start AND :end ORDER BY  g.startTime ASC)";
	@Query(INCOMING_USER_GAMES )
	List<EventWindowBlock> getIncomingWindowBlock(@Param("user") User user, @Param("stateId") long stateId, @Param("start") Date start, @Param("end") Date end);
	
	
	
	public final static String INCOMING_USER_GAMES_BY_ROLE = "SELECT new umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock(e.id, e.stateId,  o.address, o.city, g.startTime, g.endTime, e.playersLimit )  " 
			+ "FROM Event e JOIN e.graphic g JOIN g.orlik o JOIN e.usersEvent ue WHERE ue.user = :user AND e.stateId = :stateId AND g.startTime BETWEEN :start AND :end AND ue.roleId = :roleId "
			+ "ORDER BY  g.startTime ASC)";
	@Query(INCOMING_USER_GAMES_BY_ROLE )
	List<EventWindowBlock> getIncomingWindowBlockByRoleId(@Param("user") User user, @Param("stateId") long stateId, @Param("start") Date start, @Param("end") Date end, @Param("roleId") long roleId);
	
	
	
	public final static String COUNT_INCOMING_USER_GAMES = "SELECT COUNT(e.id) FROM Event e JOIN e.usersEvent ue JOIN ue.user u WHERE e.stateId = :state AND u = :user " 
			+ "AND e.graphic.startTime BETWEEN :start AND :end ";
	@Query(COUNT_INCOMING_USER_GAMES)
	int countIncomingUserGames(@Param("user") User user, @Param("state") long state, @Param("start") Date start, @Param("end") Date end);
	
	

	public final static String COUNT_INCOMING_USER_GAMES_BY_ROLE = "SELECT COUNT(e.id) FROM Event e JOIN e.usersEvent ue JOIN ue.user u WHERE e.stateId = :state AND u = :user " 
			+ "AND e.graphic.startTime BETWEEN :start AND :end AND ue.roleId = :roleId";
	@Query(COUNT_INCOMING_USER_GAMES_BY_ROLE)
	int countIncomingUserGamesByRoleId(@Param("user") User user, @Param("state") long state, @Param("start") Date start, @Param("end") Date end, @Param("roleId") long roleId);
	
	
	
	public final static String COUNT_USERS_EVENT_DECISIONS = "SELECT COUNT(ue.id) FROM UserEvent ue WHERE ue.userDecision = :decisionId "
			+ "AND ue.eventId = :eventid";
	@Query(COUNT_USERS_EVENT_DECISIONS)
	int countUsersEventDecisions(@Param("eventid") long eventId, @Param("decisionId") long decisionId );
	
	
	public final static String IS_INVITED_ON_THE_EVENT = "SELECT COUNT(*) FROM UserEvent ue "
			+ "JOIN ue.user u "
			+ "WHERE u = :user AND ue.eventId = :eventId AND ue.userDecision != 4";
	@Query(IS_INVITED_ON_THE_EVENT)
	int isInvitedOnTheEvent(@Param("user") User user, @Param("eventId") long eventId);

	
	
	
	public final static String JOIN_USER_DECISION = "UPDATE UserEvent ue SET ue.userDecision = 2 "
			+ "WHERE ue.eventId = :eventId AND ue.userId = :userId";
	@Query(JOIN_USER_DECISION)
	@Modifying  
	@Transactional
	int setJoinDecision(@Param("userId") long userId, @Param("eventId") long eventId);
	
	
	public final static String QUIT_USER_DECISION = "UPDATE UserEvent ue SET ue.userDecision = 3 "
			+ "WHERE ue.eventId = :eventId AND ue.userId = :userId";
	@Query(QUIT_USER_DECISION)
	@Modifying  
	@Transactional
	int setQuitDecision(@Param("userId") long userId, @Param("eventId") long eventId);
	
	
}
