package umk.zychu.inzynierka.controller.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.controller.DTObeans.DHXCustomEvent;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.EventState;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.model.UserEventRole;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;
import com.dhtmlx.planner.DHXEventsManager;
import com.dhtmlx.planner.DHXStatus;

@Transactional
public class EventsManager extends DHXEventsManager {

	protected AllServices services;
	private Boolean allowReservation = false;	
	Orlik  orlik;

	public EventsManager(HttpServletRequest request, AllServices allServices) {
		super(request);
		this.services = allServices;	
		if(request.getParameter("allow") != null){
			allowReservation = Boolean.parseBoolean(request.getParameter("allow"));
		}
		if(request.getParameter("orlik") != null){
			Integer orlikId = Integer.parseInt(request.getParameter("orlik"));
			orlik = services.getOrlikService().getOrlikById(orlikId);
		}
	}
	
	public EventsManager(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Iterable<DHXEv> getEvents() {
		ArrayList<DHXEv> dhxEvents = new ArrayList<DHXEv>();
		try {
			Collection<Graphic> orlikGraphics = orlik.getGraphicCollection();

			Iterator<Graphic> i = orlikGraphics.iterator();
			while (i.hasNext()) {
				Graphic graphic = (Graphic) i.next();				
				DHXCustomEvent dhxEvent = new DHXCustomEvent(graphic);
				dhxEvents.add(dhxEvent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dhxEvents;
	}
		
	@Override
	public DHXStatus saveEvent(DHXEv dhxEvent, DHXStatus dhxStatus) {
		try {
			if (dhxStatus == DHXStatus.UPDATE) {
				Graphic graphic = services.getGraphicService().findOne(dhxEvent.getId());
				graphic.setAvailable(allowReservation);
				graphic.setStartTime(dhxEvent.getStart_date());
				graphic.setEndTime(dhxEvent.getEnd_date());
				graphic.setTitle(dhxEvent.getText());
				graphic = services.getGraphicService().save(graphic);
				updateWithDependencies(graphic);
			} else if (dhxStatus == DHXStatus.INSERT) {
				Graphic graphic = new Graphic(dhxEvent, orlik);
				graphic.setAvailable(allowReservation);
				services.getGraphicService().save(graphic);
			} else if (dhxStatus == DHXStatus.DELETE) {
				Graphic graphic = services.getGraphicService().findOne(dhxEvent.getId());
				services.getGraphicService().delete(graphic);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return dhxStatus;
	}

	@Override
	public DHXEv createEvent(String id, DHXStatus status) {
		return new DHXEvent();
	}
	
	private void updateWithDependencies(Graphic graphic){
		EventState state = services.getStateDAO().findOne(EventState.IN_A_BASKET);
		UserEventRole guestRole = services.getUserEventRoleService().findOne(UserEventRole.GUEST); 
		UserDecision notInvited = services.getUserEventDecisionDAO().findOne(UserDecision.NOT_INVITED);
		UserDecision invited = services.getUserEventDecisionDAO().findOne(UserDecision.INVITED);
		graphic.getEvents().stream()
			.forEach(e -> {
				if(!e.getGraphic().getAvailable()){
					e.setState(state);
					services.getEventService().save(e);
				}
			});
		graphic.getEvents().stream()
			.map(e -> e.getUsersEvent())
			.flatMap(ue -> ue.stream())
			.filter(ue -> ue.getRole().equals(guestRole) 
					&& !ue.getDecision().equals(notInvited))
			.forEach(ue -> {
				ue.setDecision(invited);
				services.getUserEventService().save(ue);
			});
	}
}
