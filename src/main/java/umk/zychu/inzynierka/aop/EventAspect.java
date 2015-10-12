package umk.zychu.inzynierka.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.service.*;

/**
 * Created by emagdnim on 2015-09-17.
 */
@Aspect
@Component
public class EventAspect {

    @Autowired
    EventService eventService;
    @Autowired
    UserNotificationsService userNotificationsService;
    @Autowired
    EventStateService eventStateService;
    @Autowired
    UserEventDecisionService userEventDecisionService;
    @Autowired
    UserEventRoleService userEventRoleService;
    @Autowired
    GraphicService graphicService;

    @Pointcut("execution(* umk.zychu.inzynierka.service.EventService.save(..)) && args(event)")
    public void abcPointcut(Event event){}

    @Around("abcPointcut(event)")
    public void logAround(ProceedingJoinPoint pjp, Event event) throws Throwable {
   /*     EventState beforeState = eventService.findOne(event.getId()).getState();
        Graphic graphicBefore = eventService.findOne(event.getId()).getGraphic() != null ?
                eventService.findOne(event.getId()).getGraphic()
                : null;
        Event newEvent = (Event) pjp.proceed(new Object[]{event});
        if(!beforeState.equals(newEvent.getState())){
            UserDecision invited = userEventDecisionService.findOne(UserDecision.INVITED);
            UserDecision accepted = userEventDecisionService.findOne(UserDecision.ACCEPTED);
            EventState inBasket = eventStateService.findOne(EventState.IN_A_BASKET);
            UserEventRole guest = userEventRoleService.findOne(UserEventRole.GUEST);
            String subject = "";
            String description = "";
            String address = "";
            SimpleDateFormat ftStart =
                    new SimpleDateFormat("dd.mm.yyyy, hh:mm");
            SimpleDateFormat ftEnd =
                    new SimpleDateFormat("hh:mm");
            if (beforeState.equals(inBasket)) {
                subject = newEvent.getUserOrganizer().getEmail() + " ustalił miejsce i czas wydarzenia!";
                address = newEvent.getGraphic().getOrlik().getAddress();
                description = address + ",<br>"
                        + ftStart.format(newEvent.getGraphic().getStartTime())
                        + " - " + ftEnd.format(newEvent.getGraphic().getEndTime());
            }else if(newEvent.getState().equals(inBasket)){
                address = graphicBefore.getOrlik().getAddress();
                subject = address + ",<br>"
                        + ftStart.format(graphicBefore.getStartTime())
                        + " - " + ftEnd.format(graphicBefore.getEndTime());
                description = "Animator dokonał zmian terminu lub wygrało zdarzenie konkurencyjne";
            }else{
                address = graphicBefore.getOrlik().getAddress();
                String state = "";
                switch(newEvent.getState().getState()){
                    case "in_progress" : state = "W budowie";
                        break;
                    case "ready_to_accept" : state = "Do akcpetacji";
                        break;
                    case "threatened" : state = "Zagrożony";
                        break;
                    case "approved" : state = "Przyjęty";
                }
                subject = "Wydarzenie zmieniło status (" + state + ")!";
                description = address + ",<br>"
                        + ftStart.format(graphicBefore.getStartTime())
                        + " - " + ftEnd.format(graphicBefore.getEndTime());
            }
            String finalSubject = subject;
            String finalDescription = description;
            newEvent.getUsersEvent().stream()
                    .filter(ue -> ue.getDecision().equals(invited)
                            || ue.getDecision().equals(accepted))
                    .forEach(ue -> {
                        if(beforeState.equals(inBasket)){
                            if(ue.getRole().equals(guest)) {
                                UserNotification notify = new UserNotification(finalSubject, finalDescription, ue);
                                userNotificationsService.save(notify);
                            }
                        }else{
                            UserNotification notify = new UserNotification(finalSubject, finalDescription, ue);
                            userNotificationsService.save(notify);
                        }
                    });
        }*/
    }
}
