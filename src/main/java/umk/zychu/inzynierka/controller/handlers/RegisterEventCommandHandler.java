package umk.zychu.inzynierka.controller.handlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventUser;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision;
import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.GraphicService;
import umk.zychu.inzynierka.service.UserNotificationsService;
import umk.zychu.inzynierka.service.UserService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.GUEST;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.ORGANIZER;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventState.IN_PROGRESS;
import static umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision.*;

@Component
public class RegisterEventCommandHandler implements CommandHandler<Command<RegisterEventForm>, Event>{

    @Autowired
    private UserService userService;

    @Autowired
    private GraphicService graphicService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserNotificationsService userNotificationsService;

    @Override
    public Event apply(Command<RegisterEventForm> eventCommand) {
        try {
            RegisterEventForm form = eventCommand.getForm();
            return registerNewEvent(form);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Event registerNewEvent(final RegisterEventForm form) {
        User userOrganizer = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        Graphic graphic = graphicService.findOne(form.getGraphicId());
        Event event = new Event.Builder(userOrganizer)
                .enumeratedEventState(IN_PROGRESS)
                .graphic(graphic)
                .playersLimit(form.getUsersLimit())
                .build();

        List<UserEvent> usersEvents = new LinkedList<>();
        usersEvents.add(createUserEventForOrganizer(userOrganizer, event));
        usersEvents.addAll(createAllNotAsOrganizerUsersEvents(form, event));

        event.setUsersEvent(usersEvents);
        event = eventService.save(event);
        userNotificationsService.eventCreated(event);
        return event;
    }

    private List<UserEvent> createAllNotAsOrganizerUsersEvents(final RegisterEventForm form, Event event) {
        LinkedList<UserEvent> usersEvents = new LinkedList<>();
        ArrayList<RegisterEventUser> regUsersList = form.getEventFormMembers() != null ? (ArrayList<RegisterEventUser>) form.getEventFormMembers() : new ArrayList<>();
        for (RegisterEventUser regEventUser : regUsersList) {
            if (regEventUser.getAllowed() || regEventUser.getInvited()) {
                User userTarget = userService.getUser(regEventUser.getEmail());
                EnumeratedUserEventDecision decision = (regEventUser.getInvited()) ? INVITED : NOT_INVITED;
                Boolean permission = regEventUser.getAllowed();
                UserEvent ue = new UserEvent.Builder(userTarget, event.getUserOrganizer(), event, GUEST, decision)
                        .setPermission(permission)
                        .build();
                usersEvents.add(ue);
            }
        }
        return usersEvents;
    }

    private UserEvent createUserEventForOrganizer(final User userOrganizer, final Event event) {
        return new UserEvent.Builder(userOrganizer, null, event, ORGANIZER, ACCEPTED)
                .setPermission(Boolean.TRUE)
                .build();
    }
}