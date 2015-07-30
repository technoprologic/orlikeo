package umk.zychu.inzynierka.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.repository.OrlikDaoRepository;

@Service
@Transactional
public class OrlikServiceImp implements OrlikService {
	
	@Autowired
	OrlikDaoRepository orlikDAO;
	
	@Override
	public Orlik getOrlikById(Integer id){
		return orlikDAO.findOne(id);
	}
	
	@Override
	public void saveOrlik(Orlik orlik){
		orlikDAO.save(orlik);
	}	
	
	public Map<String, String> getOrliksIdsAndNames(){	
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("0", "Wybierz orlik...");
		List<Orlik> orliks = orlikDAO.findAll();
		orliks.forEach(o -> map.put(o.getId().toString(), o.getAddress()));
		return map;		
	}

	@Override
	public List<User> getOrlikManagersByOrlik(Orlik orlik) {
		return orlik.getOrlikManagers();
	}
}
