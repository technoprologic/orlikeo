package umk.zychu.inzynierka.service;

import java.util.List;
import java.util.Optional;

import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;

public interface GraphicService {

	List<Graphic> getOrlikGraphicByOrlik(Orlik orlik);
	Optional<Graphic> getGraphicById(long id);
}
