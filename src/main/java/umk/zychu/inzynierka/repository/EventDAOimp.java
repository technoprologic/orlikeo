package umk.zychu.inzynierka.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.Orlik;

@Repository
public class EventDAOimp implements EventDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public Event getEventById(int id){
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Event> getEventsByOrlikId(int id){
		List<Event> eventList = new ArrayList<Event>();
		Query query = em.createQuery("from Event e where e.id = :id AND e.startDate = :startDate");
		query.setParameter("id", id);
		query.setParameter("startDate", new Date());
		eventList = query.getResultList();
		if(eventList.size() > 0){
			return eventList;
		}
		else{
			return null;
		}
	}
}
