package umk.zychu.inzynierka.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;

public interface OrlikService {
	Optional<Orlik> getOrlikById(long id);
	void saveOrlik(Orlik orlik);
	Map<String, String> getOrliksIdsAndNames();
	List<User> getOrlikManagersByOrlikId(long orlikId);
}
