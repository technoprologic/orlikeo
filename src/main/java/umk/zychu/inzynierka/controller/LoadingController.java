package umk.zychu.inzynierka.controller;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dhtmlx.planner.controls.DHXLocalization;

import umk.zychu.inzynierka.service.EventXService;


@Controller
public class LoadingController {

	@Autowired
	private EventXService xService;

/*	@Autowired
	EntityManager manager;

	@RequestMapping("/planner")
	public String planner(HttpServletRequest request, ModelMap model)
			throws Exception {
		try {
			DHXPlanner s = new DHXPlanner("./resources/codebase/",
					DHXSkin.TERRACE);
			s.setWidth(900);
			s.localizations.set(DHXLocalization.Polish);
			s.setInitialDate(LocalDateTime.now().getYear(), LocalDateTime.now()
					.getMonthValue() - 1, LocalDateTime.now().getDayOfMonth());
			EventsManager eventsManager = new EventsManager(request, manager);

			s.parse(eventsManager.getEvents());
			System.out.println(s.data.dataprocessor.getURL());
			s.data.dataprocessor.setURL("/planner");
			System.out.println(s.data.dataprocessor.getURL());
			model.addAttribute("body", s.render());
			
		} catch (NullPointerException e) {
			 e.printStackTrace();
		}
		return "javaplanner1";
	}
	*/
	
	
	@RequestMapping("/planner")
	public String planner(HttpServletRequest request, ModelMap model)
			throws Exception {
		System.out.println(request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()));
		return "javaplanner1";
	}
	
	


}