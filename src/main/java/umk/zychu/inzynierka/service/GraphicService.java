package umk.zychu.inzynierka.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;

public interface GraphicService {

	List<Graphic> getOrlikGraphicByOrlik(Orlik orlik);
	Optional<Graphic> getGraphicById(Integer id);
	Collection<Graphic> getOrlikGraphicByOrlikId(Long id);
	void update(Graphic entity);
	void save(Graphic graphic);
	void delete(Graphic entity);
}
