package umk.zychu.inzynierka.repository;

import java.util.List;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.GraphicEntity;
import umk.zychu.inzynierka.model.Orlik;

public interface EventDAO {
	Event getEventById(int id);
	List<GraphicEntity> getOrlikGraphicByOrlik(Orlik orlik);
	GraphicEntity getGraphicEntityById(int graphicId);
}
