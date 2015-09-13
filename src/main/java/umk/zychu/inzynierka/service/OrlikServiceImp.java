package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.repository.OrlikDaoRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrlikServiceImp implements OrlikService {

	@Autowired
	OrlikDaoRepository orlikDAO;
	@Autowired
	UserService userService;
	@Autowired
	EventService eventService;

	@Override
	public Orlik getOrlikById(Integer id) {
		return orlikDAO.findOne(id);
	}

	@Override
	public void saveOrlik(Orlik orlik) {
		orlikDAO.save(orlik);
	}

	public Map<String, String> getOrliksIdsAndNames() {
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

	@Override
	public List<UserGameDetails> getAllByManager(String username) {
		User manager = userService.getUser(username);
		Optional<Orlik> optOrlik = orlikDAO.findAll().stream()
				.filter(o -> o.getOrlikManagers().contains(manager))
				.findFirst();
		if (optOrlik.isPresent()) {
			Orlik orlik = optOrlik.get();
			List<UserEvent> organizersUserEvents = eventService
					.findAll()
					.stream()
					.filter(e -> e.getGraphic() != null && e.getGraphic().getOrlik().equals(orlik))
					//.peek(e -> System.out.println(e.getId()))
					.flatMap(e -> e.getUsersEvent().stream())
					.filter(ue -> ue.getUser().equals(
							ue.getEvent().getUserOrganizer()))
					.sorted((a, b) -> {
						if (a.getEvent()
								.getGraphic()
								.getStartTime()
								.before(b.getEvent().getGraphic()
										.getStartTime()))
							return -1;
						else if (a
								.getEvent()
								.getGraphic()
								.getStartTime()
								.after(b.getEvent().getGraphic().getStartTime()))
							return 1;
						else if (a.getEvent().getCreationDate()
								.before(b.getEvent().getCreationDate()))
							return -1;
						else if (a.getEvent().getCreationDate()
								.after(b.getEvent().getCreationDate()))
							return 1;
						else
							return 0;
					})
					//.peek(ue -> System.out.println(ue.getId()))
					.collect(Collectors.toList());
			return eventService
					.generateUserGameDetailsList(organizersUserEvents);
		} else
			return new ArrayList<UserGameDetails>();
	}
}
