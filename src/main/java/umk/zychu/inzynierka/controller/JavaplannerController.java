package umk.zychu.inzynierka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import umk.zychu.inzynierka.controller.DTObeans.AcceptEventForm;
import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.EventToApproveService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
public class JavaplannerController {

	@Autowired
	private EventService eventService;
	@Autowired
	private EventToApproveService eventToApproveService;
	
	@RequestMapping("/planner")
	public String planner(@RequestParam("orlik") Integer id, HttpServletRequest request, ModelMap model)
			throws Exception {
		model.addAttribute("orlik", id);
		return "javaplanner";
	}
	
	
	@RequestMapping("/pane")
	public String animatorPane(Map<String, Object> model){
		eventToApproveService.setNotificationsChecked();
		model.put("acceptEventForm", new AcceptEventForm());
		return "animatorPane";
	}
	
	 
	@RequestMapping(value="/pane/accept", method = RequestMethod.POST)
	public String acceptEvent(@RequestParam("ev") Integer id){
		System.out.println("event do zatwierdzenia " + id);
		eventService.acceptEvent(id);
		return "redirect:/pane";
		
	}
}











