package umk.zychu.inzynierka.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import umk.zychu.inzynierka.repository.EventStateDaoRepository;
import umk.zychu.inzynierka.repository.UserEventDecisionDAOrepository;
import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.GraphicService;
import umk.zychu.inzynierka.service.OrlikService;
import umk.zychu.inzynierka.service.UserEventRoleService;
import umk.zychu.inzynierka.service.UserEventService;



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
	private UserEventDecisionDAOrepository userEventDecisionDAO;
	@Autowired
	private EventStateDaoRepository stateDAO;
	@Autowired
	private UserEventRoleService userEventRoleService;
	
	public UserEventRoleService getUserEventRoleService() {
		return userEventRoleService;
	}

	public void setUserEventRoleService(UserEventRoleService userEventRoleService) {
		this.userEventRoleService = userEventRoleService;
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
	 * @return the userEventDecisionDAO
	 */
	public UserEventDecisionDAOrepository getUserEventDecisionDAO() {
		return userEventDecisionDAO;
	}

	/**
	 * @param userEventDecisionDAO the userEventDecisionDAO to set
	 */
	public void setUserEventDecisionDAO(
			UserEventDecisionDAOrepository userEventDecisionDAO) {
		this.userEventDecisionDAO = userEventDecisionDAO;
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

	/**
	 * @return the stateDAO
	 */
	public EventStateDaoRepository getStateDAO() {
		return stateDAO;
	}
	
}
