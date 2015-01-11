package umk.zychu.inzynierka.service;

import java.util.List;
import java.util.Map;
import java.util.Set;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.repository.EventDAO;

@Service
@Transactional
public class EventServiceImp implements EventService{
	
	@Autowired
	EventDAO eventDAO;
	
	@Override
	public List<Event> getOrlikEvents(int id){
		return eventDAO.getEventsByOrlikId(id);
	}


}
