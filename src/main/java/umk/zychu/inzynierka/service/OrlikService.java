package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;

import java.util.List;
import java.util.Map;

public interface OrlikService {
	Orlik getOrlikById(Integer id);
	void saveOrlik(Orlik orlik);
	Map<String, String> getOrliksIdsAndNames();
	List<User> getOrlikManagersByOrlik(Orlik orlik);
	List<UserGameDetails> getAllByManager(String username);
}
