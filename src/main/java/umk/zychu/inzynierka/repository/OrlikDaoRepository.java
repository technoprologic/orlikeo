package umk.zychu.inzynierka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;


public interface OrlikDaoRepository extends BaseRepository<Orlik, Long>{
	
	
	public final static String ORLIK_MANAGERS = "SELECT o.orlikManagers FROM Orlik o  WHERE o.id = :orlikId";
	@Query(ORLIK_MANAGERS)
	List<User> getOrlikManagersByOrlikId(@Param("orlikId") long orlikId);
}
