package umk.zychu.inzynierka.executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.service.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.partitioningBy;

@Component
public class GraphicsTaskExecutor {

    private TaskExecutor taskExecutor;
    private Date currentDate = null;
    private static long NOW = 0;
    private static final long MINUTE = 1000 * 60;
    private static final long HALF_AN_HOUR = MINUTE * 30;
    private static final long QUARTER_OF_AN_HOUR = MINUTE * 15;
    private UserEventRole guestRole;
    private UserDecision notInvited, invited;
    private EventState inBasket, inBuild, threatenedState, toApproveState;

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

    GraphicsTaskExecutor(TaskExecutor taskExecutor) {
        super();
        this.taskExecutor = taskExecutor;
    }

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

    @Scheduled(fixedRate = 10000)
    public void printMessages() {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                currentDate = new Date();
                NOW = currentDate.getTime();

                // Get all where is less than 30 minutes to start event.
                List<Graphic> halfHourToStartGraphics = graphicService.findAll().stream()
                        .filter(g -> g.getStartTime().getTime() - NOW < HALF_AN_HOUR).collect(Collectors.toList());

                // DIVIDING BY TIME :
                // true means it 0-15 min left,
                // false means it's 15-30 left.
                Map<Boolean, List<Graphic>> map = halfHourToStartGraphics.stream()
                        .collect(partitioningBy(g -> g.getStartTime().getTime() - NOW < QUARTER_OF_AN_HOUR));

                blockGraphics(halfHourToStartGraphics);
                manageByEventsState(map.get(Boolean.FALSE), event -> event.getState().equals(inBuild));
                manageByEventsState(map.get(Boolean.TRUE), event -> event.getState().equals(toApproveState)
                        || event.getState().equals(threatenedState));

                clearEventsGraphicsEnded30MinutesAgo();
                clear45minutesPastGraphics();
            }
        });
    }

    /**
     * Manages graphics and their events.
     *
     * @param graphics Where is 30-15-0 minutes to start the event.
     * @param predicate Predicate says which states should be processed.
     */
    private void manageByEventsState(List<Graphic> graphics, Predicate<Event> predicate) {
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
                    ue.setDecision(invited);
                    userEventService.save(ue);
                });

        eventsReadyToPrepare.forEach(e -> {
            e.setGraphic(null);
            if(e.getState().equals(toApproveState)){
                eventToApproveService.removeEventFromWaitingForCheckByManager(e);
            }
            e.setState(inBasket);
        });
    }

    /**
     * Remove all from graphics where it's 45minutes past.
     */
    @Transactional
    private void clear45minutesPastGraphics() {
        graphicService.findAll().stream()
                .filter(g -> NOW - g.getEndTime().getTime() > HALF_AN_HOUR + QUARTER_OF_AN_HOUR)
                .forEach(g -> graphicService.delete(g));
    }

    /**
     * Remove all graphics from events which are 30 minutes past.
     */
    @Transactional
    private void clearEventsGraphicsEnded30MinutesAgo() {
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
    private void blockGraphics(List<Graphic> halfHourToStartGraphics) {
        halfHourToStartGraphics.forEach(g -> {
            g.setAvailable(false);
            graphicService.save(g);
        });
    }
}