package umk.zychu.inzynierka.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.GraphicEntity;
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
	public GraphicEntity getGraphicEntityById(int graphicId){
		List<GraphicEntity> orlikGraphic = new ArrayList<GraphicEntity>();
		Query query = em.createQuery("from GraphicEntity o where o.id = :graphic");
		query.setParameter("graphic", graphicId);
		orlikGraphic = query.getResultList();
		if(orlikGraphic.size() > 0){
			return orlikGraphic.get(0);
		}
		else{
			return null;
		}
	}
}
