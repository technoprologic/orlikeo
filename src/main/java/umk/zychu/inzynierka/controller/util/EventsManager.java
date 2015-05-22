package umk.zychu.inzynierka.controller.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;






import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.controller.DTObeans.DHXCustomEvent;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.UserEvent;
import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;
import com.dhtmlx.planner.DHXEventsManager;
import com.dhtmlx.planner.DHXStatus;

@Transactional
public class EventsManager extends DHXEventsManager {

	protected AllServices services;
	private Boolean allowReservation = false;
	Long orlikId;

	
	

	public EventsManager(HttpServletRequest request, AllServices allServices) {
		super(request);
		this.services = allServices;	
		if(request.getParameter("allow") != null){
			allowReservation = Boolean.parseBoolean(request.getParameter("allow"));
		}
		if(request.getParameter("orlik") != null){
			orlikId = Long.parseLong(request.getParameter("orlik"));
		}
	}
	
	

	public EventsManager(HttpServletRequest request) {
		super(request);
	}


	@Override
	public Iterable<DHXEv> getEvents() {
		ArrayList<DHXEv> events = new ArrayList<DHXEv>();
		try {
			Collection<Graphic> orlikGraphics = services.getGraphicService().getOrlikGraphicByOrlikId(orlikId);

			Iterator<Graphic> i = orlikGraphics.iterator();
			while (i.hasNext()) {
				Graphic graphic = (Graphic) i.next();				
				DHXCustomEvent dhxEvent = new DHXCustomEvent(graphic.getId(), graphic.getOrlikId(), graphic.getStartTime(),
						graphic.getEndTime(), graphic.getTitle(), graphic.getAvailable());
				events.add(dhxEvent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}
	
	
	@Override
	public DHXStatus saveEvent(DHXEv event, DHXStatus status) {

		try {
			Graphic entity = new Graphic(event);
			entity.setAvailable(allowReservation);
			entity.setOrlikId(orlikId);
			if (status == DHXStatus.UPDATE) {
				entity.setId(event.getId());
				updateWithDependencies(entity);
			} else if (status == DHXStatus.INSERT) {
				System.out.println(entity.toString());
				services.getGraphicService().save(entity);
			} else if (status == DHXStatus.DELETE) {
				entity.setId(event.getId());
				services.getGraphicService().delete(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	@Override
	public DHXEv createEvent(String id, DHXStatus status) {
		return new DHXEvent();
	}
	
	
	
	
	private void updateWithDependencies(Graphic entity){
		Collection<Event> withGraphicConnected = services.getEventService().getAllWithGraphic(entity);
		Iterator<Event> i = withGraphicConnected.iterator();
		while(i.hasNext()){
			Event e = (Event)i.next();
			if(!entity.getAvailable()){
				e.setStateId(1);
			}
			Iterator<UserEvent> it = e.getUsersEvent().iterator();
			while(it.hasNext()){
				UserEvent ue = it.next();
				if(ue.getRoleId() != 1 && ue.getUserDecision() != 4){
						ue.setUserDecision(1);
				}
				services.getUserEventService().update(ue);
			}
			services.getEventService().update(e);
		}
		services.getGraphicService().update(entity);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
