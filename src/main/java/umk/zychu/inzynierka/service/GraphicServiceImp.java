package umk.zychu.inzynierka.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.EventState;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.model.UserEventRole;
import umk.zychu.inzynierka.repository.EventDaoRepository;
import umk.zychu.inzynierka.repository.EventStateDaoRepository;
import umk.zychu.inzynierka.repository.GraphicDaoRepository;
import umk.zychu.inzynierka.repository.UserEventDaoRepository;
import umk.zychu.inzynierka.repository.UserEventDecisionDAOrepository;


@Service
@Transactional
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
	private EventStateService eventStateService;
	@Autowired
	private UserEventRoleService userEventRoleService;
	
	@Override
	public Graphic findOne(Integer graphicId){
		return graphicDAO.findOne(graphicId);
	}
	
	@Override
	public Graphic save(Graphic graphic){
		return graphicDAO.save(graphic);
	}

	@Override
	public void delete(Graphic entity) {
		reduceConnectedEvents(entity);
		graphicDAO.delete(entity);
	}
	
	private void reduceConnectedEvents(Graphic graphic) {
		UserDecision invited = userEventDecisionService.findOne(UserDecision.INVITED);
		UserDecision accepted = userEventDecisionService.findOne(UserDecision.ACCEPTED);
		UserDecision rejected = userEventDecisionService.findOne(UserDecision.REJECTED);
		UserEventRole guestRole = userEventRoleService.findOne(UserEventRole.GUEST);
		EventState inBasket = eventStateService.findOne(EventState.IN_A_BASKET);
		List<Event> events = graphic.getEvents();
		if (!events.isEmpty()) {
			for (Event e : events) {
				e.getUsersEvent()
						.stream()
						.filter(ue -> (ue.getDecision().equals(accepted) || ue.getDecision().equals(rejected)) 
								&& ue.getRole().equals(guestRole))
						.forEach((o) -> {
							o.setDecision(invited);
							userEventService.save(o);
						});
				e.setGraphic(null);
				e.setState(inBasket);
				eventService.save(e);
			}
		}
	}
}