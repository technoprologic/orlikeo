package umk.zychu.inzynierka.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.OrlikService;
import umk.zychu.inzynierka.service.UserService;
import umk.zychu.inzynierka.controller.DTObeans.ChoosenOrlikBean;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventUser;
import umk.zychu.inzynierka.controller.DTObeans.JsonEventObject;
import umk.zychu.inzynierka.controller.validator.ChoosenOrlikBeanValidator;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.GraphicEntity;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserEvent;

@Controller
@RequestMapping("/events")
public class EventsController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
	
	@Autowired
	private OrlikService orlikService;
	@Autowired
	private EventService eventService;
	@Autowired
	private UserService userService;

	@Autowired
	private ChoosenOrlikBeanValidator choosenOrlikBeanValidator;

	@InitBinder("chooseOrlikBean")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(choosenOrlikBeanValidator);
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String initForm(Map<String, Object> model) {
		ChoosenOrlikBean choosenOrlikBean = new ChoosenOrlikBean();
		model.put("choosenOrlikBean", choosenOrlikBean);
		model.put("orliks", orlikService.getOrliksIdsAndNames());
		return "create";
	}

	@RequestMapping(value = "/graphic", method = RequestMethod.POST)
	public String graphic(
			@ModelAttribute @Valid ChoosenOrlikBean chooseOrlikBean,
			ModelMap model, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			return "create";
		}
		return "redirect:graphic/" + chooseOrlikBean.getId();
	}

	@RequestMapping(value = "/graphic/{orlik}", method = RequestMethod.GET)
	public ModelAndView graphic(@PathVariable("orlik") long id) {

		ObjectMapper mapper = new ObjectMapper();
		List<GraphicEntity> graphic = eventService.getOrlikGraphicByOrlik(orlikService.getOrlikById(id));
		List<JsonEventObject> graphicEntityList = new ArrayList<JsonEventObject>();

		for (Iterator<GraphicEntity> i = graphic.iterator(); i.hasNext();) {
			GraphicEntity graphicEntity = i.next();
			JsonEventObject jObj = new JsonEventObject();
			jObj.id = graphicEntity.getId();
			jObj.title = graphicEntity.getTitle();
			jObj.start = (graphicEntity.getStartTime()).getTime();
			jObj.end = (graphicEntity.getEndTime()).getTime();
			jObj.url = null;
			jObj.allDay = false;
			graphicEntityList.add(jObj);
		}

		ModelAndView model = new ModelAndView("graphic");
		String json = "";
		try {
			json = mapper.writeValueAsString(graphicEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("list", json);
		model.addObject("orlikInfo", orlikService.getOrlikById(id));
		return model;
	}

	@RequestMapping(value = "/reserve/{id}", method = RequestMethod.GET)
	public String reserve(@PathVariable("id") long graphicId, Model model) {
		GraphicEntity graphicEntity = eventService.getGraphicEntityById(graphicId);
		Orlik orlik = orlikService.getOrlikById(graphicEntity.getOrlik().getId());	
		List<User> userFriends = userService.getUserFriends(SecurityContextHolder.getContext().getAuthentication().getName());
		List<RegisterEventUser> users = new ArrayList<RegisterEventUser>();
		for(User user : userFriends){
			RegisterEventUser e = new RegisterEventUser(user.getId(), false, false, user.getEmail(), user.getAge(), user.getPosition());
			users.add(e);
		}
		
		RegisterEventForm form = new RegisterEventForm();
		form.setGraphicId(graphicId);
		form.setUserFriends(users);
		model.addAttribute("orlik", orlik);
		model.addAttribute("event", graphicEntity);
		model.addAttribute("registerEventForm", form);
		return "reserve";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register( @ModelAttribute("registerEventForm") RegisterEventForm form, ModelMap model, BindingResult result, HttpServletRequest request) {
		
		if(result.hasErrors()) return "error";
		
		Event event = eventService.registerEventForm(form);
		System.out.println(event.getId());
		List <UserEvent> usersEvent = eventService.getUserEvent(event.getId());	
		model.addAttribute("event", event);
		model.addAttribute("usersEvent", usersEvent);
		return "eventCreated";
	}

	

	@RequestMapping(value = "/organized", method = RequestMethod.GET)
	public ModelAndView organized(ModelMap model) {

		return new ModelAndView("organized");
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(ModelMap model) {

		return new ModelAndView("details");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(ModelMap model) {

		return new ModelAndView("edit");
	}
	
	@RequestMapping(value="/allInState/{state}", method = RequestMethod.GET)
	public String allInState(@PathVariable("state") long stateId, Model model){
		List<Event> events = eventService.getUserEvents(SecurityContextHolder.getContext().getAuthentication().getName(), stateId);
		model.addAttribute("eventsInBuildState", events);
		return "eventsInState";
	}
	
	
	
	
}