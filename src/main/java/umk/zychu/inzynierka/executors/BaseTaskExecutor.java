package umk.zychu.inzynierka.executors;

import org.springframework.beans.factory.annotation.Autowired;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.service.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BaseTaskExecutor {

    protected Date currentDate = null;
    protected static long NOW = 0;
    protected static final long MINUTE = 1000 * 60;
    protected static final long HALF_AN_HOUR = MINUTE * 30;
    protected static final long QUARTER_OF_AN_HOUR = MINUTE * 15;
    protected UserEventRole guestRole;
    protected UserDecision notInvited, invited;
    protected EventState inBasket, inBuild, threatenedState, toApproveState;

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

    @PostConstruct
    private void post() {
        this.inBuild = eventStateService.findOne(EventState.IN_PROGRESS);
        this.guestRole = userEventRoleService.findOne(UserEventRole.GUEST);
        this.notInvited = userEventDecisionService.findOne(UserDecision.NOT_INVITED);
        this.invited = userEventDecisionService.findOne(UserDecision.INVITED);
        this.inBasket = eventStateService.findOne(EventState.IN_A_BASKET);
        this.threatenedState = eventStateService.findOne(EventState.THREATENED);
        this.toApproveState = eventStateService.findOne(EventState.READY_TO_ACCEPT);
    }

    /**
     * Removes all broken events without graphic, and eventState other than IN_BASKET.
     */
    protected void removeAllBrokenEvents() {
        eventService.findAll().stream()
                .filter(e -> null == e.getGraphic() && !e.getState().equals(inBasket))

                .forEach(e -> eventService.delete(e));
    }

    /**
     * Manages graphics and their events.
     *@Component
     * @param graphics Where is 30-15-0 minutes to start the event.
     * @param predicate Predicate says which states should be processed.
     */
    protected void manageByEventsState(List<Graphic> graphics, Predicate<Event> predicate) {
        // Filter by predicate.
        Set<Event> eventsReadyToPrepare = graphics.stream()
                .flatMap(g -> g.getEvents().stream())
                .filter(predicate)
                .collect(Collectors.toSet());

        // Change all decisions (who's been invited) to INVITED.
        eventsReadyToPrepare.stream()
                .flatMap(e -> e.getUsersEvent().stream())
                .filter(ue -> ue.getRole().equals(guestRole)
                        && !ue.getDecision().equals(notInvited))
                .forEach(ue -> {
                    if(!ue.getUserPermission()) {
                        ue.setDecision(invited);
                    }
                    userEventService.save(ue);
                });

        // Remove graphics from events
        eventsReadyToPrepare.forEach(e -> {
            e.setGraphic(null);
            if(e.getState().equals(toApproveState)){
                eventToApproveService.removeEventFromWaitingForCheckByManager(e);
            }
            e.setState(inBasket);
            eventService.save(e);
        });
    }

    /**
     * Remove all from graphics where it's 45minutes past.
     */
    @Transactional
    protected void clear45minutesPastGraphics() {
        graphicService.findAll().stream()
                .filter(g -> NOW - g.getEndTime().getTime() > HALF_AN_HOUR + QUARTER_OF_AN_HOUR)
                .forEach(g -> graphicService.delete(g));
    }

    /**
     * Remove all graphics from events which are 30 minutes past.
     */
    @Transactional
    protected void clearEventsGraphicsEnded30MinutesAgo() {
        graphicService.findAll().stream()
                .filter(g -> NOW - g.getEndTime().getTime() > HALF_AN_HOUR)
                .flatMap(g -> g.getEvents().stream())
                .forEach(e -> {
                    e.setGraphic(null);
                    eventService.save(e);
                });
    }

    /**
     * Sets graphics reservation availability to false.
     *
     * @param halfHourToStartGraphics Graphics to set unavailable for registering new events.
     */
    protected void blockGraphics(List<Graphic> halfHourToStartGraphics) {
        halfHourToStartGraphics.forEach(g -> {
            g.setAvailable(false);
            graphicService.save(g);
        });
    }
}
