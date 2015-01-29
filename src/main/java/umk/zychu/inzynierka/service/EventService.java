package umk.zychu.inzynierka.service;

import java.util.List;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.GraphicEntity;
import umk.zychu.inzynierka.model.Orlik;

public interface EventService {

	List<GraphicEntity> getOrlikGraphicByOrlik(Orlik orlik);
	GraphicEntity getGraphicEntityById(int graphicId);
}
