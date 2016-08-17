package umk.zychu.inzynierka.foo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import umk.zychu.inzynierka.model.EventState;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.model.UserEventRole;
import umk.zychu.inzynierka.service.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GraphicsTaskExecutor {

	private TaskExecutor taskExecutor;
	private Date now = null;
	private long fromNow = 0;
	private long minute = 1000*60;
	private long halfAnHour = minute*30;
	private long quarterOfAnHour = minute*15;
	private EventState inBuild;
	private UserEventRole guestRole;
	private UserDecision notInvited;
	private UserDecision invited;
	private EventState inBasket;
	private EventState threatenedState;
	private EventState toApproveState;
	private EventState approvedState;


	@Autowired
	GraphicService graphicService;
	@Autowired
	EventStateService eventStateService;
	@Autowired
	EventService eventService;
	@Autowired
	UserEventRoleService userEventRoleService;
	@Autowired
	UserEventDecisionService userEventDecisionService;
	@Autowired
	UserEventService userEventService;
	@Autowired
	EventToApproveService eventToApproveService;


	public GraphicsTaskExecutor() {
    	super();

    }

	GraphicsTaskExecutor(TaskExecutor taskExecutor){
		super();
		this.taskExecutor = taskExecutor;
	}

	@PostConstruct
	private void post(){
		this.inBuild = eventStateService.findOne(EventState.IN_PROGRESS);
		this.guestRole = userEventRoleService.findOne(UserEventRole.GUEST);
		this.notInvited = userEventDecisionService.findOne(UserDecision.NOT_INVITED);
		this.invited = userEventDecisionService.findOne(UserDecision.INVITED);
		this.inBasket = eventStateService.findOne(EventState.IN_A_BASKET);
		this.threatenedState = eventStateService.findOne(EventState.THREATENED);
		this.toApproveState = eventStateService.findOne(EventState.READY_TO_ACCEPT);
		this.approvedState = eventStateService.findOne(EventState.APPROVED);
	}

	@Scheduled(fixedRate = 10000)
	public void printMessages() {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				now = new Date();
				fromNow = now.getTime();
				removeAllInBuildState();
				removeAllThreatenedAndToApprove();
				setGraphicAvailableForAllIfNotReserved();
				removeAllEventsOutOfTerm();
				clear45minutesPastGraphics();
			}
		});
	}

	/**
	 *  Renove all from graphics where it's 45minutes past.
	 */
	@Transactional
	private void clear45minutesPastGraphics() {
		//clear all
		graphicService.findAll().stream()
				.filter(g -> fromNow - g.getEndTime().getTime() > halfAnHour + quarterOfAnHour)
				.forEach(g -> graphicService.delete(g));
	}

	/**
	 * Sets graphics to unavailable for reserve
	 * if it's less than 15 minutes to event.
	 * Takes all UserEvents
	 */
	@Transactional
	private void setGraphicAvailableForAllIfNotReserved() {
		// All past graphics
		List<Graphic> pastGraphics = graphicService.findAll().stream()
				.filter(g -> g.getAvailable() && g.getStartTime().getTime() - fromNow < quarterOfAnHour)
				.collect(Collectors.toList());

		Long count = pastGraphics.stream()
				.flatMap(g -> g.getEvents().stream())
				.filter(e -> e.getState().equals(approvedState)).count();

		// If there is now APPROVED event set unavailable for reservation.
		if (count == 0) {
			pastGraphics.stream()
					.forEach(g -> {
						g.setAvailable(false);
						g.setTitle("Dostępny dla wszystkich (brak rezerwacji)");
						graphicService.save(g);
					});
		}

	}

	/**
	 * At the end remove all events from graphics which are past.
	 */
	@Transactional
	private void removeAllEventsOutOfTerm() {
		graphicService.findAll().stream()
				.filter(g -> fromNow - g.getEndTime().getTime() > halfAnHour)
				.flatMap(g -> g.getEvents().stream())
				.forEach(e -> eventService.delete(e));
	}

	/**
	 *	1. Take all graphics (IN_BUILD) that's left 30 minutes to event.
	 *  2. Set invited people decisions to INVITED (basic).
	 *  3. Set events states to IN_BASKET, remove their graphics.
	 */
	@Transactional
	private void removeAllInBuildState(){
		List<Graphic> pastGraphics = graphicService.findAll().stream()
				.filter(g -> g.getStartTime().getTime() - fromNow < halfAnHour).collect(Collectors.toList());

		// Take all who was invited and set their decisions to (basic) invited.
		pastGraphics.stream()
		.flatMap(g -> g.getEvents().stream())
				.filter(e -> e.getState().equals(inBuild))
				.flatMap(e -> e.getUsersEvent().stream())
				.filter(ue -> ue.getRole().equals(guestRole)
                				&& !ue.getDecision().equals(notInvited))
				.forEach(ue -> {
					ue.setDecision(invited);
					userEventService.save(ue);
				});

		//change events in build states to basket
		pastGraphics.stream()
				.flatMap(g -> g.getEvents().stream())
				.filter(e -> e.getState().equals(inBuild))
				.forEach(e -> {
					e.setState(inBasket);
					e.setGraphic(null);
					eventService.save(e);
				});

		// Disable reservation for all graphics where time is les than 30 minutes.
		pastGraphics.stream()
				.forEach(g -> g.setAvailable(false));

	}

	/**
	 *	1. Take all graphics where time to start is less than 15 minutes.
	 *	2. Filter their events by state THREATENED or TO_APPROVE.
	 *	3. Set all users events who has been invited to INVITED.
	 *	4. For all events with state TO_APPROVE remove from waiting.
	 * 	5. Set events states to IN_BASKET.
	 */
	@Transactional
	private void removeAllThreatenedAndToApprove() {
		graphicService.findAll().stream()
				.filter(g -> g.getStartTime().getTime() - fromNow < quarterOfAnHour)
				.flatMap(g ->g.getEvents().stream())
				.filter(e -> e.getState().equals(threatenedState) || e.getState().equals(toApproveState))
				.flatMap( e -> e.getUsersEvent().stream())
				.filter(ue -> ue.getRole().equals(guestRole)
						&& !ue.getDecision().equals(notInvited))
				.forEach(ue -> {
					ue.setDecision(invited);
					userEventService.save(ue);
				});

		graphicService.findAll().stream()
				.filter(g -> g.getStartTime().getTime() - fromNow < quarterOfAnHour)
				.flatMap(g ->g.getEvents().stream())
				.filter(e -> e.getState().equals(threatenedState) || e.getState().equals(toApproveState))
				.forEach(e -> {
					if(e.getState().equals(toApproveState)){
						eventToApproveService.removeEventFromWaitingForCheckByManager(e);
					}
					e.setState(inBasket);
					eventService.save(e);
				});
	}
}