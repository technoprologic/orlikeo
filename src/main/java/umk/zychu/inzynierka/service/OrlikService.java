package umk.zychu.inzynierka.service;

import java.util.List;
import java.util.Map;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;

public interface OrlikService {
	Orlik getOrlikById(Integer id);
	void saveOrlik(Orlik orlik);
	Map<String, String> getOrliksIdsAndNames();
	List<User> getOrlikManagersByOrlik(Orlik orlik);
}
