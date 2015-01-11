package umk.zychu.inzynierka.repository;

import java.util.List;
import java.util.Map;

import umk.zychu.inzynierka.model.GraphicEntity;
import umk.zychu.inzynierka.model.Orlik;



public interface OrlikDAO {
	Orlik getOrlikById(int id);
	List<Orlik> getOrliks();
	void saveOrlik(Orlik orlik);
	Map<String, String> getOrliksIdsAndNames(); 
	List<GraphicEntity> getOrlikGraphicByOrlikId(int id);
	GraphicEntity getGraphicEntityById(int graphicId);
}
