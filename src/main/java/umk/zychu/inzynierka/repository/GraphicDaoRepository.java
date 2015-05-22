package umk.zychu.inzynierka.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;

@Transactional
public interface GraphicDaoRepository extends BaseRepository<Graphic, Integer >{

	public final static String ORLIK_GRAPHICS = "SELECT o FROM Graphic o WHERE o.orlik = :orlik";
	@Query(ORLIK_GRAPHICS)
	List<Graphic> getOrlikGraphicByOrlik(@Param("orlik") Orlik orlik);
	
	
	public static final String GET_GRAPHIC_BY_ORLIK_ID = "SELECT g FROM Graphic g WHERE g.orlikId = :orlikId";
	@Query(GET_GRAPHIC_BY_ORLIK_ID)
	Collection<Graphic> getOrlikGraphicByOrlikId(@Param("orlikId") Long id);
	
	
	public static final String UPDATE_GRAPHIC = "UPDATE Graphic g SET "
			+ " g.title = :title, g.startTime = :startTime, g.endTime = :endTime, g.available = :available "
			+ " WHERE g.id = :id";
	@Modifying
	@Query(UPDATE_GRAPHIC)
	void update(@Param("id") Integer id, 
			@Param("title") String title, 
			@Param("startTime") Date startTime, 
			@Param("endTime") Date endTime,
			@Param("available") Boolean available);
}
