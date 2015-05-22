package umk.zychu.inzynierka.service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.repository.OrlikDaoRepository;

@Service
public class OrlikServiceImp implements OrlikService {
	
	
	@Autowired
	OrlikDaoRepository orlikDAO;
	
	@Override
	public Optional<Orlik> getOrlikById(long id){
		return orlikDAO.findById(id);
	}
	
	
	@Override
	public void saveOrlik(Orlik orlik){
		orlikDAO.save(orlik);
	}	
	
	
	public Map<String, String> getOrliksIdsAndNames(){

		List<Orlik> orlikList = orlikDAO.findAll();
		if(orlikList.size() > 0){
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("0", "Wybierz orlik...");
			for(Iterator<Orlik> iter = orlikList.iterator(); iter.hasNext(); )
			{
				Orlik item = iter.next();
				map.put(item.getId().toString(), item.getAddress());		
			}
			return map;
		}
		else{
			return null;
		}
		
	}


	@Override
	public List<User> getOrlikManagersByOrlikId(long orlikId) {
		return orlikDAO.getOrlikManagersByOrlikId(orlikId);
	}
}
