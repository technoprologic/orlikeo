package umk.zychu.inzynierka.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/*import com.dhtmlx.planner.DHXPlanner;
import com.dhtmlx.planner.DHXSkin;
import com.dhtmlx.planner.controls.DHXLightboxSelect;
import com.dhtmlx.planner.controls.DHXLightboxText;
import com.dhtmlx.planner.controls.DHXLightboxTime;
import com.dhtmlx.planner.controls.DHXTimelineView;
import com.dhtmlx.planner.controls.DHXTimelineView.RenderModes;
import com.dhtmlx.planner.controls.DHXTimelineView.XScaleUnits;
import com.dhtmlx.planner.data.DHXDataFormat;
import com.dhtmlx.planner.extensions.DHXExtension;*/

import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.UserEventService;
import umk.zychu.inzynierka.service.UserService;

@Controller
@RequestMapping(value = { "/", "", "/home" })
public class HomeController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
	
	
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UserEventService objectService;
	
	@Autowired
	UserService userService;

	
	

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home(ModelMap model) {	
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		List<EventWindowBlock> eventWindowBlockList = eventService.getEventsBlockWindowList(user);
		debug(eventWindowBlockList);
		model.addAttribute("eventWindowsList", eventWindowBlockList );
		model.addAttribute("page", "fast");
		model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
		return new ModelAndView("home");
	}
	
	
	private void debug(List<EventWindowBlock> eventWindowBlockList ){
		for(int i=0; i<eventWindowBlockList.size(); i++){
			if(eventWindowBlockList.get(i) != null){
				long eventId = eventWindowBlockList.get(i).getEventId();
				String city = eventWindowBlockList.get(i).getCity();
				String address = eventWindowBlockList.get(i).getAddress();
				int willCome = eventWindowBlockList.get(i).getGoingToCome();
				int limit = eventWindowBlockList.get(i).getLimit();
				long state = eventWindowBlockList.get(i).getStateId();
				int haveTheSameState = eventWindowBlockList.get(i).getCountedInSameState();
				
				logger.debug("i: " + i + " Event id:" + eventId 
							+ " City:" + city
							+ " Address:" + address 
							+ " WillCome:" + willCome
							+ " Limit:" + limit
							+ " State:" + state
							+ " InTheSameStateCounter:" + haveTheSameState 
							);
			}else{
				logger.debug("Event id: brak");
			}
		}
		
	}
	
	
	/*
	
	 @RequestMapping(value="/javaplanner", method = RequestMethod.GET)
	 public String javaplanner(HttpServletRequest request, ModelMap model){
         DHXPlanner s = new DHXPlanner("./codebase/", DHXSkin.TERRACE);
         s.setWidth(900);
         s.setInitialDate(2013, 0, 21);
         s.load("events.jsp", DHXDataFormat.JSON);
         model.put("planner", s);
		 return "javaplanner";
	 }
	
	
	
	
	 @RequestMapping("/javaplanner2")
	 public ModelAndView scheduler(HttpServletRequest request) throws Exception {
	 DHXPlanner s = new DHXPlanner("./codebase/", DHXSkin.TERRACE);
	 s.setWidth(1000);
	 s.setHeight(126);
	 s.setInitialDate(2013, 3, 11);
	 s.setInitialView("timeline");
	  
	 DHXTimelineView view = new DHXTimelineView("timeline", "table", "Booking");
	 view.setRenderMode(RenderModes.TREE);
	 view.setXScaleUnit(XScaleUnits.MINUTE);
	 view.setXStep(60);
	 view.setXSize(12);
	 view.setSectionAutoheight(false);
	 view.setXStart(11);
	 view.setXLength(24);
	 view.setDx(110);
	 view.setDy(46);
	 view.setFolderDy(20);
	 view.setEventDy(41);
	 view.setServerList("tables_tree");
	 view.setTabClass("dhx_cal_tab_standalone");
	 s.views.clear();
	 s.views.add(view);
	 view.setTabPosition(10);
	  
	 // clear default lightbox fields
	 s.lightbox.clear();
	 // contact details field
	 DHXLightboxText contacts = new DHXLightboxText("contacts", "Contact details");
	 contacts.setHeight(40);
	 contacts.setFocus(true);
	 s.lightbox.add(contacts);
	 // contact details field
	 DHXLightboxText notes = new DHXLightboxText("notes", "Notes");
	 notes.setHeight(40);
	 s.lightbox.add(notes);
	 // table field
	 DHXLightboxSelect table = new DHXLightboxSelect("table", "Table");
	 table.setServerList("tables");
	 s.lightbox.add(table);
	 // time field
	 DHXLightboxTime time = new DHXLightboxTime("time", "Time period");
	 s.lightbox.add(time);
	  
	 // configuring templates
	 s.templates.setTimelineDate("{date1:date(%j %F %Y)}");
	  
	 // configuring sizes and times
	 s.xy.setBarHeight(76);
	 s.config.setFixTabPosition(false);
	 s.config.setTimeStep(60);
	 s.config.setFirstHour(10);
	 s.config.setLastHour(24);
	 s.config.setLimitTimeSelect(true);
	  
	 s.calendars.attachMiniCalendar();
	 s.extensions.add(DHXExtension.COLLISION);
	 s.extensions.add(DHXExtension.CONTAINER_AUTORESIZE);
	  
	 // loading and saving data
	 s.load("events", DHXDataFormat.JSON);
	 s.data.dataprocessor.setURL("events");
	  
	 // creating required view
	 ModelAndView mnv = new ModelAndView("javaplanner");
	 mnv.addObject("body", s.render());
	  
	 return mnv;
	 }*/
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}