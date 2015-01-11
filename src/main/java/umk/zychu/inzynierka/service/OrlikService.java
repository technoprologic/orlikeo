package umk.zychu.inzynierka.service;

import java.util.List;
import java.util.Map;
import java.util.Set;



import umk.zychu.inzynierka.model.GraphicEntity;
import umk.zychu.inzynierka.model.Orlik;


public interface OrlikService {
	
	Orlik getOrlik(int id);
	

	void saveOrlik(Orlik orlik);
	
	
	Map<String, String> getOrliksIdsAndNames();
	
	
	List<GraphicEntity> getOrlikGraphicByOrlikId(int id);
	
	
	GraphicEntity getGraphicEntityById(int graphicId);


}
