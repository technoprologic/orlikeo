package umk.zychu.inzynierka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.model.UserEvent;



public interface ObjectDaoRepository extends Repository<UserDecision, Long> {
	
	public final static String SAMPLE_JOIN = "SELECT e FROM UserEvent e WHERE e.eventId = :eventId";
	@Query(SAMPLE_JOIN)
	List<UserEvent> selectInvitedUsers(@Param("eventId") long eventId);

}
