package umk.zychu.inzynierka.service;

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
	public Optional<Graphic> getGraphicById(long graphicId){
		System.out.println("GRAPHIC ID: " + graphicId);
		return graphicDAO.findById(graphicId);
	}

}
