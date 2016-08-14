package umk.zychu.inzynierka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import umk.zychu.inzynierka.controller.DTObeans.JsonTestObject;
import umk.zychu.inzynierka.controller.util.AllServices;
import umk.zychu.inzynierka.controller.util.EventsManager;

import javax.servlet.http.HttpServletRequest;

@Component
@Controller
@RequestMapping("/api/rest/")
public class RestfulController {

	@Autowired
    AllServices services;

	@RequestMapping(value = "/json", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
    JsonTestObject postJSON(
			@RequestBody final JsonTestObject object) {
		return object;
	}

	@RequestMapping(value = "/json/{name}", method = RequestMethod.GET)
	public @ResponseBody
    JsonTestObject getJSON(@PathVariable String name) {
		JsonTestObject object = new JsonTestObject();
		object.setName(name);
		object.setSurname("nazwisko");
		return object;

	}

	// works correctly
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public String greetig(
			@RequestParam(value = "name", defaultValue = "World") String name) {
		return "GET : " + name;
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public @ResponseBody
    JsonTestObject greetig2(
			@RequestBody MultiValueMap<String, String> object) {
		JsonTestObject json = new JsonTestObject(object.getFirst("name"),
				object.getFirst("surname"));
		return json;
	}

	// works correctly : http://localhost:8080/jbossews/events?editing=false
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	@ResponseBody
	public String events(
			@RequestParam(value = "editing", required = false) Boolean ed,
			HttpServletRequest request) {
		EventsManager evs = new EventsManager(request, services);
		System.out.println("/events: " + ed);
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




















