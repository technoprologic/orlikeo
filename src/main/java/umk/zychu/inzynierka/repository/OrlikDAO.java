package umk.zychu.inzynierka.repository;

import java.util.List;
import java.util.Map;

import umk.zychu.inzynierka.model.Orlik;



public interface OrlikDAO {
	Orlik getOrlikById(long id);
	List<Orlik> getOrliks();
	void saveOrlik(Orlik orlik);
	Map<String, String> getOrliksIdsAndNames(); 
}
