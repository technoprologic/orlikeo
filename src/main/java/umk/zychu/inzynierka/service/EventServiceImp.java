package umk.zychu.inzynierka.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.controller.DTObeans.*;
import umk.zychu.inzynierka.controller.util.EventType;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.model.enums.EnumeratedEventRole;
import umk.zychu.inzynierka.model.enums.EnumeratedEventState;
import umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision;
import umk.zychu.inzynierka.repository.EventDaoRepository;

import java.util.*;
import java.util.stream.Collectors;

import static umk.zychu.inzynierka.converter.EventStateConverter.convertToEnum;
import static umk.zychu.inzynierka.model.enums.FriendshipType.ACCEPT;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.GUEST;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.ORGANIZER;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventState.*;
import static umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision.*;

@Service
@Transactional
public class EventServiceImp implements EventService {

    private static final org.slf4j.Logger logger = LoggerFactory
            .getLogger(EventServiceImp.class);

    @Autowired
    EventDaoRepository eventDAO;
    @Autowired
    UserEventService userEventService;
    @Autowired
    GraphicService graphicService;
    @Autowired
    UserService userService;

    @Autowired
    FriendshipService friendshipService;

    @Autowired
    EventToApproveService eventToApproveService;

    @Autowired
    UserNotificationsService userNotificationsService;

    //next 48hrs
    private final Date incomingEventsDateInterval = new Date((new Date()).getTime() + 172400000);


    @Override
    public void acceptEvent(Integer id) {
        Event event = eventDAO.findOne(id);
        Graphic graphic = event.getGraphic();
        Orlik orlik = graphic.getOrlik();
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (orlik.getAnimator() != null && orlik.getAnimator().equals(user) && event.getEnumeratedEventState().equals(READY_TO_ACCEPT)) {
            changeConcurrentEvents(event);
            eventToApproveService.removeEventFromWaitingForCheckByManager(event);
            event.setEnumeratedEventState(APPROVED);
            save(event);
            userNotificationsService.eventWonRaceForGraphic(event);
        }
    }

    @Override
    public void changeConcurrentEvents(Event event) {
        Graphic graphic = event.getGraphic();
        Set<Event> events = graphic.getEvents().stream()
                .filter(e -> !e.equals(event))
                .collect(Collectors.toSet());
        userNotificationsService.eventsLostRaceForGraphic(events);
        downgradeEventToBasket(events);
    }

    @Override
    public void delete(Event e) {
        delete(e.getId());
    }

    @Override
    public void downgradeEventToBasket(Set<Event> events) {
        if (!events.isEmpty()) {
            for (Event e : events) {
                e.getUsersEvent()
                        .stream()
                        .filter(ue -> (ue.getDecision().equals(ACCEPTED) || ue.getDecision().equals(REJECTED))
                                && ue.getRole().equals(GUEST))
                        .forEach((o) -> {
                            o.setDecision(INVITED);
                            userEventService.save(o);
                        });
                e.setGraphic(null);
                if (e.getEnumeratedEventState().equals(READY_TO_ACCEPT)) {
                    eventToApproveService.removeEventFromWaitingForCheckByManager(e);
                }
                e.setEnumeratedEventState(IN_A_BASKET);
                save(e);
            }
        }
    }

    @Override
    public List<Event> findAll() {
        return eventDAO.findAll();
    }

    @Override
    public Optional<Event> getEventById(Integer id) {
        eventDAO.findOne(id);
        return Optional.ofNullable(eventDAO.findOne(id));
    }

    @Override
    public RegisterEventForm generateRegisterEventForm(Event event) {
        List<UserEvent> usersEvents = event.getUsersEvent();
        List<User> friends = friendshipService.getFriends(null, ACCEPT);
        List<RegisterEventUser> users = new ArrayList<>();

        for (UserEvent userEvent : usersEvents) {
            String inviterEmail = userEvent.getInviter() != null ? userEvent.getInviter().getEmail() : null;
            RegisterEventUser e = new RegisterEventUser.Builder(userEvent.getUser())
                    .setAllowed(userEvent.getUserPermission())
                    .setInviter(inviterEmail)
                    .setInvited(!userEvent.getDecision().equals(NOT_INVITED))
                    .build();

            users.add(e);
        }
        for (User friend : friends) {
            boolean isInvited = false;
            String inviterEmail = null;
            for (UserEvent userEvent : usersEvents) {
                if (friend.getId().equals(userEvent.getUser().getId())) {
                    isInvited = true;
                    inviterEmail = userEvent.getInviter() != null ? userEvent.getInviter().getEmail() : null;
                }
            }
            if (!isInvited) {
                RegisterEventUser e1 = new RegisterEventUser.Builder(friend)
                        .setInviter(inviterEmail)
                        .build();
                users.add(e1);
            }
        }
        return new RegisterEventForm(event.getId(), users);
    }

    @Override
    public List<UserGameDetails> generateUserGameDetailsList(List<UserEvent> userEvents) {
        List<UserGameDetails> userGamesDetailsList = new LinkedList<>();
        for (UserEvent userEvent : userEvents) {
            UserGameDetails userGameDetails = generateUserGameDetails(userEvent);
            userGamesDetailsList.add(userGameDetails);
        }
        return userGamesDetailsList;
    }

    @Override
    public User getEventOrganizerUser(Event event) {
        Optional<User> eventOrganizerOpt = event.getUsersEvent().stream()
                .filter(ue -> ue.getRole().equals(ORGANIZER)
                        && ue.getEvent().equals(event))
                .map(UserEvent::getUser)
                .findFirst();

        return eventOrganizerOpt.isPresent() ? eventOrganizerOpt.get() : null;
    }

    @Override
    public List<EventWindowBlock> getEventWindowBlocks(EnumeratedEventRole role) {
        List<EventWindowBlock> windowBlocks = new ArrayList<>();
        for (EnumeratedEventState state : EnumeratedEventState.values()) {
            windowBlocks.add(getBlock(state, role, false));
        }
        windowBlocks.add(getBlock(APPROVED, role, true));
        Collections.sort(windowBlocks, (lhs, rhs) -> rhs.getDisplayOrder() - lhs.getDisplayOrder());
        return windowBlocks;
    }

    @Override
    public List<EventWindowBlock> getEventWindowBlocks(String username, EnumeratedEventRole role) {
        List<EventWindowBlock> windowBlocks = new ArrayList<>();
        for (EnumeratedEventState state : EnumeratedEventState.values()) {
            windowBlocks.add(getBlock(username, state, role, false));
        }
        windowBlocks.add(getBlock(username, APPROVED, role, true));
        Collections.sort(windowBlocks, (lhs, rhs) -> rhs.getDisplayOrder() - lhs.getDisplayOrder());
        return windowBlocks;
    }

    @Override
    public UserGameDetails getGameDetails(Event event) {
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        UserEvent userEvent = user.getUserEvents().stream()
                .filter(ue -> ue.getEvent().equals(event))
                .findFirst()
                .get();
        return generateUserGameDetails(userEvent);
    }

    @Override
    public List<UserGameDetails> getGamesDetails(String username,
                                                 EventType type, Integer stateId) {
        User user = userService.getUser(username);
        EnumeratedEventRole role = null;
        if (type != null) {
            switch (type) {
                case INVITATIONS:
                    role = GUEST;
                    break;
                case ORGANIZED:
                    role = ORGANIZER;
                    break;
                default:
                    role = null;
                    break;
            }
        }
        EnumeratedEventState state = stateId == null ? null : stateId == 5
                || stateId == 6 ? APPROVED: convertToEnum(stateId);

        List<UserEvent> userEvents = user.getUserEvents();
        Date endDate = incomingEventsDateInterval;
        Date todayAndTomorrow = new Date(endDate.getYear(),
                endDate.getMonth(), endDate.getDate());
        if (stateId != null && stateId == 6) {
            userEvents.removeIf(ue -> ue.getEvent().getGraphic().getStartTime().after(todayAndTomorrow));
        }
        if (role != null) {
            EnumeratedEventRole efectiveFinalRole = role;
            userEvents = userEvents.stream()
                    .filter(ue -> ue.getRole().equals(efectiveFinalRole))
                    .collect(Collectors.toList());
        }
        if (state != null) {
            userEvents = userEvents
                    .stream()
                    .filter(ue -> ue.getEvent().getEnumeratedEventState()
                            .equals(state))
                    .collect(Collectors.toList());
        }
        List<UserGameDetails> userGameDetails = generateUserGameDetailsList(userEvents);
        userGameDetails.stream().forEach(gd -> {
            if (gd.getStateId() == 5 && null != gd.getStartDate() && gd.getStartDate().before(endDate)) {
                // TODO Remove unnecessary setter for only those purpose and causes neccesserity of creating special id (db incopalibity)
                gd.setStateId(6);
            }
        });
        Collections.sort(userGameDetails, (o1, o2) -> {
            // return 1 if rhs should be before lhs
            // return -1 if lhs should be before rhs
            // return 0 otherwise
            Date ldate = o1.getStartDate();
            Date rdate = o2.getStartDate();
            if (ldate == null || rdate == null) {
                if (ldate == null && rdate == null)
                    return 0;
                else if (ldate == null)
                    return 1;
                else
                    return -1;
            }
            if (rdate.before(ldate))
                return 1;
            else if (ldate.before(rdate))
                return -1;
            else
                return 0;
        });
        return new ArrayList<>(userGameDetails);
    }

    @Override
    public Boolean isEventMember(Event event) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(userEmail);
        Optional<UserEvent> userEventOpt = user.getUserEvents().stream()
                .filter(ue -> ue.getEvent().equals(event))
                .findFirst();

        if (userEventOpt.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public Event registerEventForm(RegisterEventForm form) {
        try {
            User userOrganizer = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
            Graphic graphic = graphicService.findOne(form.getGraphicId());
            Integer playersLimit = form.getUsersLimit();
            Event event = new Event.Builder(userOrganizer)
                    .enumeratedEventState(IN_PROGRESS)
                    .graphic(graphic)
                    .playersLimit(playersLimit)
                    .build();

            UserEvent organizerUserEvent = new UserEvent.Builder(userOrganizer, null, event, ORGANIZER, ACCEPTED)
                    .setPermission(Boolean.TRUE)
                    .build();
            List<UserEvent> usersEvents = new LinkedList<>();
            usersEvents.add(organizerUserEvent);
            ArrayList<RegisterEventUser> regUsersList = form.getEventFormMembers() != null ? (ArrayList<RegisterEventUser>) form.getEventFormMembers() : new ArrayList<>();
            for (RegisterEventUser regEventUser : regUsersList) {
                if (regEventUser.getAllowed() || regEventUser.getInvited()) {
                    User userTarget = userService.getUser(regEventUser.getEmail());
                    EnumeratedUserEventDecision decision = (regEventUser.getInvited()) ? INVITED : NOT_INVITED;
                    Boolean permission = regEventUser.getAllowed();
                    UserEvent ue = new UserEvent.Builder(userTarget, userOrganizer, event, GUEST, decision)
                            .setPermission(permission)
                            .build();
                    usersEvents.add(ue);
                }
            }
            event.setUsersEvent(usersEvents);
            event = save(event);
            userNotificationsService.eventCreated(event);
            return event;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Event save(Event event) {
        return eventDAO.saveAndFlush(event);
    }

    @Override
    public void updateEvent(RegisterEventForm form) {
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        Event event = eventDAO.findOne(form.getEventId());
        // remove userOrganizer && editing user
        form.getEventFormMembers()
                .removeIf(reu -> reu.getEmail().equals(event.getUserOrganizer().getEmail()) || reu.getEmail().equals(user.getEmail()));
        // remove all who's !invited && has !rights &&  !have userEvent
        form.getEventFormMembers()
                .removeIf(reu -> !reu.getAllowed()
                        && !reu.getInvited()
                        && !userEventService.findOne(event, userService.getUser(reu.getEmail()))
                        .isPresent());
        if (!event.getUserOrganizer().equals(user)) {
            form.getEventFormMembers()
                    .removeIf(reu -> userEventService.findOne(event, userService.getUser(reu.getEmail())).isPresent()
                            && !userEventService.findOne(event, userService.getUser(reu.getEmail())).get().getInviter().equals(user));
        } else {
            event.setPlayersLimit(form.getUsersLimit());
            save(event);
        }
        updateUsersEvents(form.getEventFormMembers(), event);
        userEventService.changeEventStateIfRequired(event);
        //TODO powiadomienia o dokonanych zmianach wobec zaproszonych/usuniętych itd.
    }

    @Override
    public Event findOne(Integer id) {
        return eventDAO.findOne(id);
    }

    @Override
    public void delete(Integer id) throws IllegalArgumentException {
        if (eventDAO.exists(id)) {
            Event event = eventDAO.findOne(id);
            if (event.getEnumeratedEventState().equals(READY_TO_ACCEPT)) {
                eventToApproveService.removeEventFromWaitingForCheckByManager(event);
            }
            userNotificationsService.deleteAllOnEvent(event);
            userNotificationsService.eventIsRemovedByOrganizer(event);
            event.getUsersEvent().stream()
                    .forEach(ue -> userEventService.delete(ue));
            eventDAO.delete(event);
        }
    }

    private void updateUsersEvents(List<RegisterEventUser> eventFormMembers, Event event) {
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        // are still event members
        eventFormMembers
                .stream()
                .filter(reu -> reu.getAllowed() || reu.getInvited())
                .forEach(
                        reu -> {
                            User u = userService.getUser(reu.getEmail());
                            Optional<UserEvent> ueOpt = userEventService
                                    .findOne(event, u);
                            if (ueOpt.isPresent()) {
                                UserEvent ue = ueOpt.get();
                                ue.setInviter(user);
                                if (reu.getAllowed() && reu.getInvited()) {
                                    ue.setUserPermission(true);
                                    if (ue.getDecision().equals(NOT_INVITED)) {
                                        ue.setDecision(INVITED);
                                    }
                                } else if (!reu.getAllowed()
                                        && reu.getInvited()) {
                                    ue.setUserPermission(false);
                                    if (ue.getDecision().equals(NOT_INVITED)) {
                                        ue.setDecision(INVITED);
                                    }
                                } else if (reu.getAllowed()
                                        && !reu.getInvited()) {
                                    ue.setUserPermission(true);
                                    ue.setDecision(NOT_INVITED);
                                }
                                userEventService.save(ue);
                            }
                        });
        //are new to the event
        eventFormMembers.stream()
                .filter(reu -> (reu.getAllowed() || reu.getInvited())
                        && !userEventService.findOne(event, userService.getUser(reu.getEmail()))
                        .isPresent())
                .forEach(reu -> {
                    User u = userService.getUser(reu.getEmail());
                    EnumeratedUserEventDecision decision = reu.getInvited() ? INVITED : NOT_INVITED;
                    Boolean permission = reu.getAllowed();
                    UserEvent ue = new UserEvent.Builder(u, user, event, GUEST, decision)
                            .setPermission(permission)
                            .build();
                    userEventService.save(ue);
                });
        // are no longer event members
        eventFormMembers
                .stream()
                .filter(reu -> !reu.getAllowed() && !reu.getInvited())
                .forEach(
                        reu -> {
                            User u = userService.getUser(reu.getEmail());
                            Optional<UserEvent> ueOpt = userEventService
                                    .findOne(event, u);
                            if (ueOpt.isPresent()) {
                                UserEvent ue = ueOpt.get();
                                event.getUsersEvent().remove(ue);
                                save(event);
                            }
                        });
        eventDAO.findOne(event.getId());
    }

    private EventWindowBlock getBlock(EnumeratedEventState state, EnumeratedEventRole role, Boolean incoming) {
        User user = userService.getUser(SecurityContextHolder.getContext()
                .getAuthentication().getName());
        return generateBlock(user, state, role, incoming);
    }

    private EventWindowBlock getBlock(String username, EnumeratedEventState state, EnumeratedEventRole role, boolean incoming) {
        User user = userService.getUser(username);
        return generateBlock(user, state, role, incoming);
    }

    private EventWindowBlock generateBlock(User user, EnumeratedEventState state, EnumeratedEventRole role, Boolean incoming) {
        List<UserEvent> userEvents = user.getUserEvents();
        EventWindowBlock.Builder ewbBuilder = new EventWindowBlock.Builder(userEvents, state, role, incoming, ACCEPTED);
        return ewbBuilder.build();
    }

    private UserGameDetails generateUserGameDetails(UserEvent userEvent) {
        Event event = userEvent.getEvent();
        UserGameDetails.Builder ugdBuilder = new UserGameDetails.Builder(event.getId());

        ugdBuilder.stateId(event.getEnumeratedEventState().getValue())
                .organizerEmail(event.getUserOrganizer().getEmail())
                .decision(userEvent.getDecision().getValue())
                .role(userEvent.getRole())
                .permission(userEvent.getUserPermission())
                .willCome(event.getUsersEvent().stream()
                        .filter(ue -> ue.getDecision().equals(ACCEPTED))
                        .count())
                .playersLimit(event.getPlayersLimit())
                .invited(event.getUsersEvent().stream()
                        .filter(ue -> !ue.getDecision().equals(NOT_INVITED))
                        .count());

        if (event.getGraphic() != null) {
            Graphic graphic = event.getGraphic();
            Orlik orlik = graphic.getOrlik();
            ugdBuilder.startDate(graphic.getStartTime())
                    .endDate(graphic.getEndTime())
                    .orlikId(orlik.getId())
                    .address(orlik.getAddress())
                    .city(orlik.getCity())
                    .lights(orlik.getLights())
                    .water(orlik.getWater())
                    .shower(orlik.getShower())
                    .shoes(orlik.getShoes());
        }

        return ugdBuilder.build();
    }
}
