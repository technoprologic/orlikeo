package umk.zychu.inzynierka.controller.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import umk.zychu.inzynierka.controller.DTObeans.EventForm;
import umk.zychu.inzynierka.controller.DTObeans.EventMember;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.UserEventService;
import umk.zychu.inzynierka.service.UserNotificationsService;
import umk.zychu.inzynierka.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.GUEST;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.ORGANIZER;
import static umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision.INVITED;
import static umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision.NOT_INVITED;

@Component
public class UpdateEventCommandHandler implements CommandHandler<Command<EventForm>, Optional<Event>> {

    private Event event;

    private EventForm form;

    private static final Optional<Event> empty =  Optional.empty();

    private Boolean editedAsOrganizer = false;

    private List<String> oldRostersEmails;

    private List<String> newRostersEmails;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserEventService userEventService;

    @Autowired
    private UserNotificationsService userNotificationsService;

    @Autowired
    private UserService userService;

    @Override
    public Optional<Event> apply(Command<EventForm> eventFormCommand) {
        form = eventFormCommand.getForm();
        if (null == form) return empty;
        event = eventService.findOne(form.getEventId());
        if(null == event) return empty;
        editedAsOrganizer = event.getUserOrganizer().equals(userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()));
        consumeForm(form);
        return Optional.of(eventService.save(event));
    }

    private void consumeForm(EventForm form) {
        form.removeUnnecessaryEventMembers();
        oldRostersEmails = event.getUsersEvent().stream().map(ue -> ue.getUser().getEmail()).collect(Collectors.toList());
        newRostersEmails = form.getEventFormMembers().stream().map(fem -> fem.getEmail()).collect(Collectors.toList());
        //handle who's no longer event member
        removeNonActualUserEvents();
        //Handle old members
        handleOldMembers();
        //Handle new members
        handleNewMembers();
        //Change event players limit if required
        if(form.getUsersLimit() != event.getPlayersLimit()) {
            event.setPlayersLimit(form.getUsersLimit());
        }
    }

    private void handleNewMembers() {
            List<UserEvent> usersEvents = event.getUsersEvent();
            Predicate<EventMember> wereNotInvitedBefore = em -> !oldRostersEmails.contains(em.getEmail());
            List<UserEvent> newUserEvents = new ArrayList<>();
            form.getEventFormMembers().stream().filter(wereNotInvitedBefore)
                    .forEach(fem -> {
                        final UserEvent newInstance = generateNewInstance(fem);
                        if(fem.getAllowed()) userNotificationsService.notifyAboutInvitingPermission(newInstance);
                        if(fem.getInvited()) userNotificationsService.notifyAboutEventInvitation(newInstance);
                        newUserEvents.add(userEventService.save(newInstance));
                    });
            usersEvents.addAll(newUserEvents);
    }

    private void removeNonActualUserEvents() {
        //todo check if its allowed user friend
        List<UserEvent> toRemove =  event.getUsersEvent().stream().filter(ue -> !newRostersEmails.contains(ue.getUser().getEmail())).collect(Collectors.toList());
        //Send notifications
        toRemove.forEach(ue -> {
            //user was invited
            if (!ue.getDecision().equals(NOT_INVITED)) {
                userNotificationsService.notifyAboutEventInvitationRevoke(ue);
            }
            if (ue.getUserPermission()) {
                userNotificationsService.notifyAboutInvitingPermissionRevoke(ue);
            }
        });
        //Remove them
        event.getUsersEvent().removeAll(toRemove);
    }

    private void handleOldMembers() {
        form.getEventFormMembers().stream()
                .filter(em -> oldRostersEmails.contains(em.getEmail()))
                .forEach(em -> updateOldInstance(em));
    }

    private Optional<UserEvent> updateOldInstance(final EventMember eventMember) {
        UserEvent updatedUserEvent = null;
        List<UserEvent> usersEvents = event.getUsersEvent();
        Optional<UserEvent> userEventOpt = usersEvents.stream().filter(ue -> ue.getUser().getEmail().equals(eventMember.getEmail())).findFirst();
        if (userEventOpt.isPresent()) {
            updatedUserEvent = userEventOpt.get();
            if (editedAsOrganizer) {
                //permission status changed
                if (!eventMember.getAllowed().equals(updatedUserEvent.getUserPermission())) {
                    updatedUserEvent.setUserPermission(eventMember.getAllowed());
                    if(eventMember.getAllowed()) {
                        userNotificationsService.notifyAboutInvitingPermission(updatedUserEvent);
                    }else {
                        userNotificationsService.notifyAboutInvitingPermissionRevoke(updatedUserEvent);
                    }
                }
            }
            //todo check if it's his friend and was invited by him
            //invitation status changed to invited
            if (updatedUserEvent.getDecision().equals(NOT_INVITED) && eventMember.getInvited()) {
                updatedUserEvent.setDecision(INVITED);
                userNotificationsService.notifyAboutEventInvitation(updatedUserEvent);
            }

            //invitation status changed to NOT invited
            if (!updatedUserEvent.getDecision().equals(NOT_INVITED) && !eventMember.getInvited()) {
                updatedUserEvent.setDecision(NOT_INVITED);
                userNotificationsService.notifyAboutEventInvitationRevoke(updatedUserEvent);
            }
        }
        return Optional.of(userEventService.save(updatedUserEvent));
    }

    private UserEvent generateNewInstance(final EventMember eventMember) {
        return new UserEvent.Builder(
                userService.getUser(eventMember.getEmail()),
                userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),
                event,
                eventMember.getAllowed() ? GUEST : ORGANIZER,
                eventMember.getInvited() ? INVITED : NOT_INVITED)
                .setPermission(eventMember.getAllowed())
                .build();
    }
}
