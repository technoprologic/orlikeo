package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.repository.GraphicDaoRepository;

import java.util.List;
import java.util.Set;


@Service
public class GraphicServiceImp implements GraphicService{

	@Autowired
	private GraphicDaoRepository graphicDAO;
	@Autowired
	private EventService eventService;
	@Autowired
	private UserEventService userEventService;
	@Autowired 
	private UserEventDecisionService userEventDecisionService;

	@Autowired
	private EventToApproveService eventToApproveService;
	
	@Override
	public Graphic findOne(Integer graphicId){
		return graphicDAO.findOne(graphicId);
	}

	@Override
	public List<Graphic> findAll() {
		return graphicDAO.findAll();
	}

	@Override
	public Graphic save(Graphic graphic){
		return graphicDAO.save(graphic);
	}

	@Override
	@Transactional
	public void delete(Graphic entity) {
		reduceConnectedEvents(entity);
		graphicDAO.delete(entity);
	}
	
	private void reduceConnectedEvents(final Graphic graphic) {
		Set<Event> events = graphic.getEvents();
		eventService.downgradeEventToBasket(events);
	}
}