package umk.zychu.inzynierka.controller.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
 

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
 



























import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryAccessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import umk.zychu.inzynierka.model.EventX;
import umk.zychu.inzynierka.repository.EventXRepository;
import umk.zychu.inzynierka.service.EventXService;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;
import com.dhtmlx.planner.DHXEventsManager;
import com.dhtmlx.planner.DHXStatus;


public class EventsManager extends DHXEventsManager {
 
	
	protected EntityManager manager;
	
	
    public EventsManager(HttpServletRequest request, EntityManager manager) {
             super(request);
             this.manager = manager;
       }
 
    public EventsManager(HttpServletRequest request) {
        super(request);
  }
    
	@Override
	public Iterable<DHXEv> getEvents() {
		ArrayList<DHXEv> events = new ArrayList<DHXEv>();
		try {
			Collection<EventX> xCollection = manager.createQuery(
					"SELECT ex FROM EventX ex").getResultList();
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
    	   System.out.println("sveEvent!");
       return status;
       }
       
       
       @Override
   	public DHXEv createEvent(String id, DHXStatus status) {
   		return new DHXEvent();
   	}
       
}








