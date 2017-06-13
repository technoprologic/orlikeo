package umk.zychu.inzynierka.executors;

import org.springframework.beans.factory.annotation.Autowired;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.service.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.GUEST;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventState.IN_A_BASKET;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventState.READY_TO_ACCEPT;
import static umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision.INVITED;
import static umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision.NOT_INVITED;

public class BaseTaskExecutor {

    protected Date currentDate = null;
    protected static long NOW = 0;
    protected static final long MINUTE = 1000 * 60;
    protected static final long HALF_AN_HOUR = MINUTE * 30;
    protected static final long QUARTER_OF_AN_HOUR = MINUTE * 15;

    @Autowired
    GraphicService graphicService;

    @Autowired
    EventService eventService;

    @Autowired
    UserEventService userEventService;
    @Autowired
    EventToApproveService eventToApproveService;

    /**
     * Removes all broken events without graphic, and eventState other than IN_BASKET.
     */
    protected void removeAllBrokenEvents() {
        eventService.findAll().stream()
                .filter(e -> null == e.getGraphic() && !e.getEventState().equals(IN_A_BASKET))
                .forEach(e -> eventService.delete(e));
    }

    /**
     * Manages graphics and their events.
     *
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
        changeUsersDecisions(eventsReadyToPrepare);

        // Remove graphics from events
        eventsReadyToPrepare.forEach(e -> {
            e.setGraphic(null);
            if(e.getEventState().equals(READY_TO_ACCEPT)){
                eventToApproveService.removeEventFromWaitingForCheckByManager(e);
            }
            e.setEventState(IN_A_BASKET);
            eventService.save(e);
        });
    }

    /**
     * Remove all from graphics where it's 45minutes past.
     */
    @Transactional
    protected void clear45minutesPastGraphics() {
        Set<Graphic> forClear = graphicService.findAll().stream()
                .filter(g -> NOW - g.getEndTime().getTime() > HALF_AN_HOUR + QUARTER_OF_AN_HOUR).collect(Collectors.toSet());

        forClear.stream().map(g -> g.getEvents())
                .forEach(events -> changeUsersDecisions(events));

        forClear.forEach(g -> graphicService.delete(g));
    }

    /**
     * Remove all graphics from events which are 30 minutes past.
     */
    @Transactional
    protected void clearEventsGraphicsEnded30MinutesAgo() {
        Set<Event> ended30minAgo = graphicService.findAll().stream()
                .filter(g -> NOW - g.getEndTime().getTime() > HALF_AN_HOUR)
                .flatMap(g -> g.getEvents().stream()).collect(Collectors.toSet());

        changeUsersDecisions(ended30minAgo);

        ended30minAgo.forEach(e -> {
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

    /**
     * Changes user decisions to default
     *
     * @param eventsReadyToPrepare
     */
    private void changeUsersDecisions(Set<Event> eventsReadyToPrepare) {
        eventsReadyToPrepare.stream()
                .flatMap(e -> e.getUsersEvent().stream())
                .filter(ue -> ue.getRole().equals(GUEST)
                        && !ue.getDecision().equals(NOT_INVITED))
                .forEach(ue -> {
                    ue.setDecision(INVITED);
                    userEventService.save(ue);
                });
    }
}
