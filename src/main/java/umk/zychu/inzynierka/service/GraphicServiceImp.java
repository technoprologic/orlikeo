package umk.zychu.inzynierka.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.repository.GraphicDaoRepository;


@Service
public class GraphicServiceImp implements GraphicService{

	@Autowired
	private GraphicDaoRepository graphicDAO;
	
	
	@Override
	public List<Graphic> getOrlikGraphicByOrlik(Orlik orlik) {
		
		return graphicDAO.getOrlikGraphicByOrlik(orlik);
	}
	
	@Override
	public Optional<Graphic> getGraphicById(Integer graphicId){
		System.out.println("GRAPHIC ID: " + graphicId);
		return graphicDAO.findById(graphicId);
	}
	
	@Override
	public Collection<Graphic> getOrlikGraphicByOrlikId(Long id) {
		return graphicDAO.getOrlikGraphicByOrlikId(id);
	}

	@Override
	public void update(Graphic entity) {
		graphicDAO.update(entity.getId(), entity.getTitle(), entity.getStartTime(), entity.getEndTime(), entity.getAvailable());
	}
	
	@Override
	public void save(Graphic graphic){
		graphicDAO.save(graphic);
	}

	@Override
	public void delete(Graphic entity) {
		graphicDAO.delete(entity);
	}

}
