package umk.zychu.inzynierka.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import umk.zychu.inzynierka.model.EventX;

public interface EventXRepository extends BaseRepository<EventX, Integer>{

	
	public final static String GET_X_EVENTS = "SELECT ex FROM EventX ex";
	@Query(GET_X_EVENTS)
	Collection<EventX> getXevents();
	
	
	
	
	
	public static final String UPDATE_EVENT_X = "UPDATE EventX ex "
			+ " SET ex.notes = :notes, ex.table = :table, ex.start_date = :start_date, ex.end_date = :end_date "
			+ " WHERE ex.id = :id";
	@Modifying
	@Query(UPDATE_EVENT_X)
	void update(@Param("id") Integer id, @Param("notes") String notes , @Param("table") String table, @Param("start_date") Date start_date, @Param("end_date") Date end_date);
}
