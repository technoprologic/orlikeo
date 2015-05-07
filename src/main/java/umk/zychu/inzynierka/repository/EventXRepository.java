package umk.zychu.inzynierka.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;

import umk.zychu.inzynierka.model.EventX;

public interface EventXRepository extends BaseRepository<EventX, Integer>{

	
	public final static String GET_X_EVENTS = "SELECT ex FROM EventX ex";
	@Query(GET_X_EVENTS)
	Collection<EventX> getXevents();
}
