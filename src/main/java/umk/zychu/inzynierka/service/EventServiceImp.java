package umk.zychu.inzynierka.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventUser;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.controller.util.EventType;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.repository.EventDaoRepository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static umk.zychu.inzynierka.converter.EventStateConverter.convertToEnum;
import static umk.zychu.inzynierka.model.EnumeratedEventState.*;
import static umk.zychu.inzynierka.model.FriendshipType.ACCEPT;

@Service
@Transactional
public class EventServiceImp implements EventService {

    private static final org.slf4j.Logger logger = LoggerFactory
            .getLogger(EventServiceImp.class);

    private UserDecision invited;

    private UserDecision accepted;

    private UserDecision rejected;

    private UserEventRole guestRole;

    @Autowired
    EventDaoRepository eventDAO;
    @Autowired
    UserEventService userEventService;
    @Autowired
    GraphicService graphicService;
    @Autowired
    UserService userService;
    @Autowired
    UserEventDecisionService userEventDecisionService;
    @Autowired
    UserEventRoleService userEventRoleService;

    @Autowired
    FriendshipService friendshipService;

    @Autowired
    EventToApproveService eventToApproveService;

    @Autowired
    UserNotificationsService userNotificationsService;

    //next 48hrs
    private final Date incomingEventsDateInterval = new Date((new Date()).getTime() + 172400000);

    @PostConstruct
    private void setDecisions() {
        invited = userEventDecisionService.findOne(UserDecision.INVITED);
        accepted = userEventDecisionService.findOne(UserDecision.ACCEPTED);
        rejected = userEventDecisionService.findOne(UserDecision.REJECTED);
        guestRole = userEventRoleService.findOne(UserEventRole.GUEST);
    }

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
                        .filter(ue -> (ue.getDecision().equals(accepted) || ue.getDecision().equals(rejected))
                                && ue.getRole().equals(guestRole))
                        .forEach((o) -> {
                            o.setDecision(invited);
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
        List<RegisterEventUser> users = new ArrayList<RegisterEventUser>();

        for (UserEvent userEvent : usersEvents) {
            boolean decision = false;
            if (userEvent.getDecision().getId() != 4)
                decision = true;
            String inviterEmail = userEvent.getInviter() != null ? userEvent
                    .getInviter().getEmail() : null;
            RegisterEventUser e = new RegisterEventUser(userEvent.getUser()
                    .getId(), userEvent.getUserPermission(), decision,
                    userEvent.getUser().getEmail(), userEvent.getUser()
                    .getDateOfBirth(), userEvent.getUser()
                    .getPosition(), inviterEmail);
            users.add(e);
        }
        for (User friend : friends) {
            boolean isInvited = false;
            String inviterEmail = null;
            for (UserEvent userEvent : usersEvents) {
                if (friend.getId() == userEvent.getUser().getId()) {
                    isInvited = true;
                    inviterEmail = userEvent.getInviter() != null ? userEvent.getInviter().getEmail() : null;
                }
            }
            if (isInvited == false) {
                RegisterEventUser e1 = new RegisterEventUser(friend.getId(),
                        false,
                        false,
                        friend.getEmail(),
                        friend.getDateOfBirth(),
                        friend.getPosition(), inviterEmail);
                users.add(e1);
            }
        }
        return new RegisterEventForm(event.getId(), users);
    }

    @Override
    public List<UserGameDetails> generateUserGameDetailsList(List<UserEvent> userEvents) {
        List<UserGameDetails> userGamesDetailsList = new LinkedList<UserGameDetails>();
        Iterator<UserEvent> it = userEvents.iterator();
        while (it.hasNext()) {
            UserEvent userEvent = it.next();
            UserGameDetails userGameDetails = generateUserGameDetails(userEvent);
            userGamesDetailsList.add(userGameDetails);
        }
        return userGamesDetailsList;
    }

    @Override
    public User getEventOrganizerUser(Event event) {
        UserEventRole role = userEventRoleService.findOne(UserEventRole.ORGANIZER);
        Optional<User> eventOrganizerOpt = event.getUsersEvent().stream()
                .filter(ue -> ue.getRole().equals(role)
                        && ue.getEvent().equals(event))
                .map(ue -> ue.getUser())
                .findFirst();
        if (eventOrganizerOpt.isPresent()) {
            return eventOrganizerOpt.get();
        } else {
            return null;
        }
    }

    @Override
    public List<EventWindowBlock> getEventWindowBlocks(UserEventRole role) {
        List<EventWindowBlock> windowBlocks = new ArrayList<>();
        for (EnumeratedEventState state : EnumeratedEventState.values()) {
            windowBlocks.add(getBlock(state, role, false));
        }
        windowBlocks.add(getBlock(APPROVED, role, true));
        Collections.sort(windowBlocks, (lhs, rhs) -> rhs.getDisplayOrder() - lhs.getDisplayOrder());
        return windowBlocks;
    }

    @Override
    public List<EventWindowBlock> getEventWindowBlocks(String username, UserEventRole role) {
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
        UserEventRole role = null;
        if (type != null) {
            switch (type) {
                case INVITATIONS:
                    role = userEventRoleService.findOne(UserEventRole.GUEST);
                    break;
                case ORGANIZED:
                    role = userEventRoleService.findOne(UserEventRole.ORGANIZER);
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
            UserEventRole efectiveFinalRole = role;
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
        } else {
            return false;
        }
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
            UserEventRole organizerRole = userEventRoleService.findOne(UserEventRole.ORGANIZER);
            UserDecision organizerDecision = userEventDecisionService.findOne(UserDecision.ACCEPTED);
            UserEvent organizerUserEvent = new UserEvent.Builder(userOrganizer, null, event, organizerRole, organizerDecision)
                    .setPermission(Boolean.TRUE)
                    .build();
            List<UserEvent> usersEvents = new LinkedList<>();
            usersEvents.add(organizerUserEvent);
            ArrayList<RegisterEventUser> regUsersList = form.getEventFormMembers() != null ? (ArrayList<RegisterEventUser>) form.getEventFormMembers() : new ArrayList<>();
            for (RegisterEventUser regEventUser : regUsersList) {
                if (regEventUser.getAllowed() || regEventUser.getInvited()) {
                    User userTarget = userService.getUser(regEventUser.getEmail());
                    UserDecision decision = (regEventUser.getInvited()) ? userEventDecisionService
                            .findOne(UserDecision.INVITED)
                            : userEventDecisionService.findOne(UserDecision.NOT_INVITED);
                    UserEventRole role = userEventRoleService.findOne(UserEventRole.GUEST);
                    Boolean permission = regEventUser.getAllowed();
                    UserEvent ue = new UserEvent.Builder(userTarget, userOrganizer, event, role, decision)
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
        UserDecision invited = userEventDecisionService.findOne(UserDecision.INVITED);
        UserDecision notInvited = userEventDecisionService.findOne(UserDecision.NOT_INVITED);
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
                                    if (ue.getDecision().equals(notInvited)) {
                                        ue.setDecision(invited);
                                    }
                                } else if (!reu.getAllowed()
                                        && reu.getInvited()) {
                                    ue.setUserPermission(false);
                                    if (ue.getDecision().equals(notInvited)) {
                                        ue.setDecision(invited);
                                    }
                                    ;
                                } else if (reu.getAllowed()
                                        && !reu.getInvited()) {
                                    ue.setUserPermission(true);
                                    ue.setDecision(notInvited);
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
                    UserEventRole role = userEventRoleService.findOne(UserEventRole.GUEST);
                    UserDecision decision = reu.getInvited() ? userEventDecisionService.findOne(UserDecision.INVITED)
                            : userEventDecisionService.findOne(UserDecision.NOT_INVITED);
                    Boolean permission = reu.getAllowed();
                    UserEvent ue = new UserEvent.Builder(u, user, event, role, decision)
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

    private EventWindowBlock getBlock(EnumeratedEventState state, UserEventRole role, Boolean incoming) {
        User user = userService.getUser(SecurityContextHolder.getContext()
                .getAuthentication().getName());
        return generateBlock(user, state, role, incoming);
    }

    private EventWindowBlock getBlock(String username, EnumeratedEventState state, UserEventRole role, boolean incoming) {
        User user = userService.getUser(username);
        return generateBlock(user, state, role, incoming);
    }

    private EventWindowBlock generateBlock(User user, EnumeratedEventState state, UserEventRole role, Boolean incoming) {
        List<UserEvent> userEvents = user.getUserEvents();
        EventWindowBlock.Builder ewbBuilder = new EventWindowBlock.Builder(userEvents, state, role, incoming, accepted);
        return ewbBuilder.build();
    }

    private UserGameDetails generateUserGameDetails(UserEvent userEvent) {
        UserDecision accept = userEventDecisionService.findOne(UserDecision.ACCEPTED);
        UserDecision not_invited = userEventDecisionService.findOne(UserDecision.NOT_INVITED);
        Event event = userEvent.getEvent();
        UserGameDetails.Builder ugdBuilder = new UserGameDetails.Builder(event.getId());

        ugdBuilder.stateId(event.getEnumeratedEventState().getValue())
                .organizerEmail(event.getUserOrganizer().getEmail())
                .decision(userEvent.getDecision().getId())
                .role(userEvent.getRole().getId())
                .permission(userEvent.getUserPermission())
                .willCome(event.getUsersEvent().stream()
                        .filter(ue -> ue.getDecision().equals(accept))
                        .count())
                .playersLimit(event.getPlayersLimit())
                .invited(event.getUsersEvent().stream()
                        .filter(ue -> !ue.getDecision().equals(not_invited))
                        .count());

        if (event.getGraphic() != null) {
            Graphic graphic = event.getGraphic();
            Orlik orlik = graphic.getOrlik();
            //fill up builder
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
