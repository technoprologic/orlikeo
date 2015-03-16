package umk.zychu.inzynierka.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;

public interface GraphicDaoRepository extends BaseRepository<Graphic, Long>{

	public final static String ORLIK_GRAPHICS = "SELECT o FROM Graphic o WHERE o.orlik = :orlik";
	@Query(ORLIK_GRAPHICS)
	List<Graphic> getOrlikGraphicByOrlik(@Param("orlik") Orlik orlik);
}
