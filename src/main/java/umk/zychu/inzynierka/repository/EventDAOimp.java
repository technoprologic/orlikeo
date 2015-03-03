package umk.zychu.inzynierka.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.GraphicEntity;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.UserEvent;


@Repository
public class EventDAOimp implements EventDAO  {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public Event getEventById(long id){
		List<Event> eventList = new ArrayList<Event>();
		Query query = em.createQuery("from Event e where e.id = :id");
		query.setParameter("id", id);
		eventList = query.getResultList();
		if(eventList.size() > 0){
			return eventList.get(0);
		}
		else{
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<GraphicEntity> getOrlikGraphicByOrlik(Orlik orlik){
		List<GraphicEntity> orlikGraphic = new ArrayList<GraphicEntity>();
		Query query = em.createQuery("from GraphicEntity o where o.orlik = :orlik");
		query.setParameter("orlik", orlik);
		orlikGraphic = query.getResultList();
		if(orlikGraphic.size() > 0){
			return orlikGraphic;
		}
		else{
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public GraphicEntity getGraphicEntityById(long id){
		List<GraphicEntity> orlikGraphic = new ArrayList<GraphicEntity>();
		Query query = em.createQuery("from GraphicEntity o where o.id = :graphic");
		query.setParameter("graphic", id);
		orlikGraphic = query.getResultList();
		if(orlikGraphic.size() > 0){
			return orlikGraphic.get(0);
		}
		else{
			return null;
		}
	}
	
	@Override
	public void saveUserEvent(UserEvent event){
		em.persist(event);
	}
	
	@Override
	public void saveEvent(Event event){
		em.persist(event);
		em.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEvent> getUserEvent(long id) {
		List<UserEvent> usersEvent = new ArrayList<UserEvent>();
		Query query = em.createQuery("from UserEvent e where e.eventId = :eventId");
		query.setParameter("eventId", id);
		usersEvent = query.getResultList();
		if(usersEvent.size() > 0){
			return usersEvent;
		}
		else{
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Event> getEventsWithStateByUserName(String username, long state){
		List<Event> events = new ArrayList<Event>();
		Query query = em.createQuery("select e.event from UserEvent e where e.user.email = :username and e.event.stateId = :state order by e.event.graphic.startTime DESC");
		query.setParameter("username", username);
		query.setParameter("state", state);
		events = query.getResultList();
		if(events.size()>0){
			return events;
		}else{
			return null;
		}
	}
	
	

	
}
