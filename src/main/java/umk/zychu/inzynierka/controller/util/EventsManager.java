package umk.zychu.inzynierka.controller.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import umk.zychu.inzynierka.model.EventX;
import umk.zychu.inzynierka.service.EventXService;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;
import com.dhtmlx.planner.DHXEventsManager;
import com.dhtmlx.planner.DHXStatus;


public class EventsManager extends DHXEventsManager {

	protected EventXService service;

	public EventsManager(HttpServletRequest request, EventXService service) {
		super(request);
		this.service = service;
	}

	public EventsManager(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Iterable<DHXEv> getEvents() {
		ArrayList<DHXEv> events = new ArrayList<DHXEv>();
		try {
			Collection<EventX> xCollection = service.getCustomEvents();
			Iterator<EventX> i = xCollection.iterator();
			while (i.hasNext()) {
				EventX tmpX = (EventX) i.next();
				events.add(new DHXEvent(tmpX.getId(), tmpX.getStart_date(),
						tmpX.getEnd_date(), tmpX.getNotes()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}

	
	@Override
	public DHXStatus saveEvent(DHXEv event, DHXStatus status) {

		try {
			String query = null;
			if (status == DHXStatus.UPDATE) {
				EventX entity = new EventX(event);
				entity.setId(event.getId());
				service.update(entity);

			} else if (status == DHXStatus.INSERT) {
				
				EventX entity = new EventX(event);
				service.save(entity);
				
			} else if (status == DHXStatus.DELETE) {
				EventX entity = new EventX(event);
				entity.setId(event.getId());
				service.delete(entity);
			}

			System.out.println(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	@Override
	public DHXEv createEvent(String id, DHXStatus status) {
		return new DHXEvent();
	}

}
