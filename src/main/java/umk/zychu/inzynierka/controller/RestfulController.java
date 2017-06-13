package umk.zychu.inzynierka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import umk.zychu.inzynierka.controller.util.AllServices;
import umk.zychu.inzynierka.controller.util.EventsManager;

import javax.servlet.http.HttpServletRequest;

@Component
@Controller
@RequestMapping("/api/rest/")
public class RestfulController {

	@Autowired
	AllServices services;

	//TODO Check unused request params are needed
	// works correctly : http://localhost:8080/jbossews/events?editing=false
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	@ResponseBody
	public String events(
			@RequestParam(value = "editing", required = false) Boolean editing,
			HttpServletRequest request) {
		EventsManager evs = new EventsManager(request, services);
		return evs.run();
	}

	@RequestMapping(value = "/events", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	@ResponseBody
	public String events2(@RequestParam("editing") Boolean editing,
			HttpServletRequest request) {
		EventsManager evs = new EventsManager(request, services);
		return evs.run();
	}
}




















