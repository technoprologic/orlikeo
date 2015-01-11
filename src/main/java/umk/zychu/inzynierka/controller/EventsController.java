package umk.zychu.inzynierka.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.BindingType;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.OrlikService;
import umk.zychu.inzynierka.controller.DTObeans.ChoosenOrlikBean;
import umk.zychu.inzynierka.controller.DTObeans.RegisterUserBean;
import umk.zychu.inzynierka.controller.DTObeans.JsonEventObject;
import umk.zychu.inzynierka.controller.validator.ChoosenOrlikBeanValidator;
import umk.zychu.inzynierka.controller.validator.RegisterUserBeanValidator;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.GraphicEntity;
import umk.zychu.inzynierka.model.Orlik;

@Controller
@RequestMapping("/events")
public class EventsController {

	@Autowired
	private OrlikService orlikService;

	@Autowired
	private EventService eventService;

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
	public ModelAndView graphic(@PathVariable("orlik") int id) {

		ObjectMapper mapper = new ObjectMapper();
		List<GraphicEntity> graphic = orlikService.getOrlikGraphicByOrlikId(id);
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
		return model;
	}

	@RequestMapping(value = "/reserve/{id}", method = RequestMethod.GET)
	public ModelAndView reserve(@PathVariable("id") int graphicId, ModelAndView model) {
		GraphicEntity graphicEntity = orlikService.getGraphicEntityById(graphicId);
		Orlik orlik = orlikService.getOrlik(graphicEntity.getOrlikId());
		model.setViewName("reserve");
		model.addObject("orlik", orlik);
		model.addObject("event", graphicEntity);

		return model;
	}

	@RequestMapping(value = "/registerEvent", method = RequestMethod.GET)
	public ModelAndView register(ModelMap model) {

		return new ModelAndView("organized");
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
}