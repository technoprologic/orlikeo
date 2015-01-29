package umk.zychu.inzynierka.service;

import java.util.List;
import java.util.Map;
import java.util.Set;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.GraphicEntity;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.repository.EventDAO;

@Service
@Transactional
public class EventServiceImp implements EventService{
	
	@Autowired
	EventDAO eventDAO;
	
	@Override
	public List<GraphicEntity> getOrlikGraphicByOrlik(Orlik orlik){
		return eventDAO.getOrlikGraphicByOrlik(orlik);
	}
	
	@Override
	public GraphicEntity getGraphicEntityById(int graphicId){
		return eventDAO.getGraphicEntityById(graphicId);
	}
	
	


}
