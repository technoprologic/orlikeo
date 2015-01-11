package umk.zychu.inzynierka.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import umk.zychu.inzynierka.model.GraphicEntity;
//import umk.zychu.inzynierka.controller.DTObeans.RegisterOrlikBean;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.repository.OrlikDAO;

@Service
@Transactional
public class OrlikServiceImp implements OrlikService {
	
	
	@Autowired
	OrlikDAO orlikDAO;
	
	@Override
	public Orlik getOrlik(int id){
		return orlikDAO.getOrlikById(id);
	}
	
	
	public Map<String, String> getOrliksIdsAndNames()
	{
		return orlikDAO.getOrliksIdsAndNames();
	}
	
	@Override
	public void saveOrlik(Orlik orlik){
		orlikDAO.saveOrlik(orlik);
	}
	
	@Override
	public List<GraphicEntity> getOrlikGraphicByOrlikId(int id){
		return orlikDAO.getOrlikGraphicByOrlikId(id);
	}
	
	@Override
	public GraphicEntity getGraphicEntityById(int graphicId){
		return orlikDAO.getGraphicEntityById(graphicId);
	}
	
	
}
