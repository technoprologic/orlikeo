package umk.zychu.inzynierka.service;

import java.util.Map;
import umk.zychu.inzynierka.model.Orlik;

public interface OrlikService {
	Orlik getOrlikById(long id);
	void saveOrlik(Orlik orlik);
	Map<String, String> getOrliksIdsAndNames();
}
