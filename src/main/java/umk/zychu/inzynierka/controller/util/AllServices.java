package umk.zychu.inzynierka.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.GraphicService;
import umk.zychu.inzynierka.service.UserEventService;



@Component
public class AllServices{
	


	@Autowired
	private GraphicService graphicService;
	@Autowired
	private EventService eventService;
	@Autowired
	private UserEventService userEventService;
	
	
	
	
	public UserEventService getUserEventService() {
		return userEventService;
	}

	public EventService getEventService() {
		return eventService;
	}

	public GraphicService getGraphicService() {
		return graphicService;
	}	
	
	public AllServices() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
