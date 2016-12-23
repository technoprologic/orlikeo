package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.repository.UserNotificationsDaoRepository;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision.NOT_INVITED;
import static umk.zychu.inzynierka.model.enums.EnumeratedUserEventDecision.REJECTED;

/**
 * Created by emagdnim on 2015-09-12.
 */
@Service
@Transactional
public class UserNotificationsServiceImp implements UserNotificationsService {

    //todo When user got permission for inviting and invites his friends
    //todo they do not get noticication

    private static final String FRIEND_USER_DETAIL = "/friends/userDetail/";

    @Autowired
    private UserNotificationsDaoRepository userNotificationsDAO;
    @Autowired
    private UserService userService;

    private SimpleDateFormat ftStart = new SimpleDateFormat("dd.mm.yyyy, hh:mm");

    private SimpleDateFormat ftEnd = new SimpleDateFormat("hh:mm");

    UserNotificationsServiceImp() {
        super();
        ftStart = new SimpleDateFormat("dd.mm.yyyy, hh:mm");
        ftEnd = new SimpleDateFormat("hh:mm");
    }

    @Override
    public List<UserNotification> findAll() {
        return userNotificationsDAO.findAll();
    }

    @Override
    public UserNotification findOne(Integer id) {
        return userNotificationsDAO.findOne(id);
    }

    @Override
    public void graphicChangedByAnimator(Graphic graphic) {
        String address = graphic.getOrlik().getAddress();
        String date = ftStart.format(graphic.getStartTime()) + " - " + ftEnd.format(graphic.getEndTime());
        graphic.getEvents().stream()
                .flatMap(e -> e.getUsersEvent().stream())
                .filter(ue -> !ue.getDecision().equals(NOT_INVITED))
                .forEach(ue -> {
                    String description = "Termin niedostępny!";
                    if (graphic.getAvailable()) {
                        description = "Zmienione godziny wydarzenia: " + address + ", <br>" + date;
                    }
                    UserNotification notify = new UserNotification.Builder(ue.getUser(),
                            "Animator dokonał zmian w grafiku",
                            description)
                            .build();
                    save(notify);
                });
    }

    @Override
    public void eventIsRemovedByOrganizer(Event event) {
        if (event.getGraphic() != null) {
            Graphic graphic = event.getGraphic();
            String address = graphic.getOrlik().getAddress();
            String date = ftStart.format(event.getGraphic().getStartTime()) + " - " + ftEnd.format(event.getGraphic().getEndTime());
            event.getUsersEvent().stream()
                    .filter(ue -> ue.getInviter() != null && !ue.getDecision().equals(REJECTED))
                    .forEach(ue -> {
                        UserNotification notify = new UserNotification.Builder(ue.getUser(),
                                "Usunięto wydarzenie (" + event.getUserOrganizer().getEmail() + ")",
                                address + ", <br>" + date)
                                .build();
                        save(notify);
                    });
        }
    }


    @Override
    public void eventStateChanged(Event event) {
        String stateName = "";
        switch (event.getEnumeratedEventState()) {
            case IN_A_BASKET:
                stateName = "W koszu";
                break;
            case IN_PROGRESS:
                stateName = "W budowie";
                break;
            case READY_TO_ACCEPT:
                stateName = "Do akceptacji";
                break;
            case THREATENED:
                stateName = "Zagrożony";
                break;
            case APPROVED:
                stateName = "Przyjęty";
                break;
            default:
                break;
        }
        String subject = "Zmiana statusu wydarzenia  (" + stateName + ")";
        String description = "";
        if (event.getGraphic() != null) {
            Graphic graphic = event.getGraphic();
            String address = graphic.getOrlik().getAddress();
            String date = ftStart.format(event.getGraphic().getStartTime()) + " - " + ftEnd.format(event.getGraphic().getEndTime());
            description = address + ", <br>" + date;
        } else {
            description = "Wydarzenie utraciło termin";
        }
        String efFinalDescription = description;
        event.getUsersEvent().stream()
                .filter(ue -> !ue.getDecision().equals(REJECTED)
                        && !ue.getDecision().equals(NOT_INVITED))
                .forEach(ue -> {
                    UserNotification notify = new UserNotification.Builder(ue.getUser(),
                            subject,
                            efFinalDescription)
                            .build();
                    save(notify);
                });

    }

    @Override
    public void eventWonRaceForGraphic(Event event) {
        eventStateChanged(event);

    }

    @Override
    public void eventsLostRaceForGraphic(Set<Event> events) {
        if (!events.isEmpty()) {
            Graphic graphic = events.stream().findFirst().get().getGraphic();
            String address = graphic.getOrlik().getAddress();
            String date = ftStart.format(graphic.getStartTime()) + " - " + ftEnd.format(graphic.getEndTime());
            String subject = "Wydarzenie przegrało wyścig o termin";
            String description = address + ", <br>" + date + " został zajęty";
            events.stream()
                    .flatMap(e -> e.getUsersEvent().stream())
                    .filter(ue -> !ue.getDecision().equals(REJECTED)
                            && !ue.getDecision().equals(NOT_INVITED))
                    .forEach(ue -> {
                        UserNotification notify = new UserNotification.Builder(ue.getUser(),
                                subject, description)
                                .build();
                        save(notify);
                    });
        }
    }

    @Override
    public void save(UserNotification userNotification) {
        userNotificationsDAO.save(userNotification);
    }

    @Override
    public void setCheckedForEvent(Event event) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username);
        user.getUserNotifications().stream()
                .filter(un -> un.getLink().contains("event")
                        && un.getLink().contains(event.getId().toString()))
                .forEach(un -> {
                    un.setChecked();
                    save(un);
                });
    }

    @Override
    public void invitation(Friendship friendship) {
        String title = "Zaproszenie do znajomych!";
        String description = "Od użytkownika " + friendship.getRequester().getEmail();
        User targetUser = friendship.getTarget();
        String href = FRIEND_USER_DETAIL + friendship.getRequester().getEmail();
        UserNotification notify = new UserNotification.Builder(targetUser, title, description)
                .link(href)
                .build();
        save(notify);
    }

    @Override
    public void acceptation(Friendship friendship) {
        String title = "Masz nowego znajomego!";
        String description = "Użytkownika " + friendship.getRequester().getEmail() + " zaaceptował Twoje zaproszenie.";
        User targetUser = friendship.getRequester().equals(friendship.getTarget()) ? friendship.getRequester() : friendship.getTarget();
        String href = "/friends/userDetail/" + friendship.getRequester().getEmail();
        UserNotification notify = new UserNotification.Builder(targetUser,
                title, description)
                .link(href)
                .build();
        save(notify);
    }

    @Override
    public void denial(Friendship friendship) {
        String title = "Twoje zaproszenie zostało odrzucone!";
        String description = "Użytkownik " + friendship.getRequester().getEmail() + " odrzucił zaproszenie.";
        User targetUser = friendship.getRequester().equals(friendship.getTarget()) ? friendship.getRequester() : friendship.getTarget();
        String href = "/friends/userDetail/" + friendship.getRequester().getEmail();
        UserNotification notify = new UserNotification.Builder(targetUser,
                title, description)
                .link(href)
                .build();
        save(notify);
    }

    @Override
    public void blocking(Friendship friendship) {
        String title = "Zostałeś zablokowany!";
        String description = "Użytkownik " + friendship.getRequester().getEmail() + " zablokował Cię.";
        User targetUser = friendship.getRequester().equals(friendship.getTarget()) ? friendship.getRequester() : friendship.getTarget();
        String href = "/friends/userDetail/" + friendship.getRequester().getEmail();
        UserNotification notify = new UserNotification.Builder(targetUser,
                title, description)
                .link(href)
                .build();
        save(notify);
    }

    @Override
    public void unblocking(Friendship friendship) {
        String title = "Zostałeś odblokowany!";
        String description = "Użytkownik " + friendship.getRequester().getEmail() + " odblokował Cię.";
        User targetUser = friendship.getRequester().equals(friendship.getTarget()) ? friendship.getRequester() : friendship.getTarget();
        String href = "/friends/userDetail/" + friendship.getRequester().getEmail();
        UserNotification notify = new UserNotification.Builder(targetUser,
                title,
                description)
                .link(href)
                .build();
        save(notify);
    }


    @Override
    public void cancelInvitation(User user, User userTarget) {
        String title = "Cofnięto zaproszenie!";
        String description = "Użytkownik " + user.getEmail() + " wycofał zaproszenie.";
        User targetUsr = userTarget;
        UserNotification notify = new UserNotification.Builder(targetUsr,
                title,
                description)
                .build();
        save(notify);
    }

    @Override
    public void deleteAllWithFriend(User friend) {
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        user.getUserNotifications().stream()
                .filter(un -> un.getLink().contains(friend.getEmail()))
                .forEach(un -> un.setChecked());
    }

    @Override
    public void remove(User actionUser, User targetUser) {
        String title = "Usunięta znajomość!";
        String description = "Użytkownik " + actionUser.getEmail() + " usunął Ciebie ze znajomych.";
        User targetUsr = targetUser;
        UserNotification notify = new UserNotification.Builder(targetUsr,
                title,
                description)
                .build();
        save(notify);
    }

    @Override
    public void deleteAllOnEvent(Event event) {
        List<User> users = event.getUsersEvent().stream()
                .map(ue -> ue.getUser())
                .collect(Collectors.toList());
        Set<UserNotification> notifications = users.stream()
                .peek(u -> System.out.println(u.getUserNotifications()))
                .flatMap(u -> u.getUserNotifications().stream())
                .collect(Collectors.toSet());

        notifications = notifications.stream()
                .filter(un -> un.getLink().contains("event")
                        && un.getLink().contains(event.getId().toString()))
                .collect(Collectors.toSet());
        notifications.stream()
                .forEach(un -> userNotificationsDAO.delete(un));
    }

    @Override
    public void eventCreated(Event event) {
        Graphic graphic = event.getGraphic();
        String address = graphic.getOrlik().getAddress();
        String date = ftStart.format(graphic.getStartTime()) + " - " + ftEnd.format(graphic.getEndTime());
        String subject = "Użytkownik " + event.getUserOrganizer().getEmail() + " utworzył wydarzenie";
        String description = address + ", <br>" + date;
        event.getUsersEvent().stream()
                .filter(ue -> ue.getInviter() != null)
                .forEach(ue -> {
                    UserNotification notify = new UserNotification.Builder(ue.getUser(),
                            subject,
                            description)
                            .build();
                    save(notify);
                });
    }

    @Override
    public void eventGraphicChangedByOrganizer(Event event) {
        Graphic graphic = event.getGraphic();
        String address = graphic.getOrlik().getAddress();
        String date = ftStart.format(graphic.getStartTime()) + " - " + ftEnd.format(graphic.getEndTime());
        String subject = "Użytkownik " + event.getUserOrganizer().getEmail() + " zmienił miejsce wydarzenia";
        String description = address + ", <br>" + date;
        event.getUsersEvent().stream()
                .filter(ue -> ue.getInviter() != null)
                .forEach(ue -> {
                    UserNotification notify = new UserNotification.Builder(ue.getUser(),
                            subject, description)
                            .build();
                    save(notify);
                });
    }

    @Override
    public void delete(UserNotification userNotification) {
        userNotificationsDAO.delete(userNotification);
    }

    //todo user notification page exception when user doesn't have notifications
}
