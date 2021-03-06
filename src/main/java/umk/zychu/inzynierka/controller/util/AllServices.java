package umk.zychu.inzynierka.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import umk.zychu.inzynierka.service.*;


@Component
public class AllServices{

	@Autowired
	private GraphicService graphicService;
	@Autowired
	private EventService eventService;
	@Autowired
	private UserEventService userEventService;
	@Autowired
	private OrlikService orlikService;

	@Autowired
	private EventToApproveService eventToApproveService;


	public UserNotificationsService getUserNotificationsService() {
		return userNotificationsService;
	}

	public void setUserNotificationsService(UserNotificationsService userNotificationsService) {
		this.userNotificationsService = userNotificationsService;
	}

	@Autowired
	private UserNotificationsService userNotificationsService;


    public EventToApproveService getEventToApproveService() {
		return eventToApproveService;
	}

	public void setEventToApproveService(EventToApproveService eventToApproveService) {
		this.eventToApproveService = eventToApproveService;
	}

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
	}

	/**
	 * @return the orlikService
	 */
	public OrlikService getOrlikService() {
		return orlikService;
	}

	/**
	 * @param orlikService the orlikService to set
	 */
	public void setOrlikService(OrlikService orlikService) {
		this.orlikService = orlikService;
	}
	
}
