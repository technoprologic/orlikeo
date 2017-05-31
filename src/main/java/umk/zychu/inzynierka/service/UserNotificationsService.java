package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.model.*;

import java.util.List;
import java.util.Set;

/**
 * Created by emagdnim on 2015-09-12.
 */
public interface UserNotificationsService {
    void delete(UserNotification userNotification);

    void deleteAllOnEvent(Event event);

    void eventCreated(Event event);

    void eventGraphicChangedByOrganizer(Event event);

    void eventIsRemovedByOrganizer(Event event);

    void eventStateChanged(Event event);

    void eventWonRaceForGraphic(Event event);

    void eventsLostRaceForGraphic(Set<Event> events);

    List<UserNotification> findAll();

    UserNotification findOne(Integer id);

    void graphicChangedByAnimator(Graphic graphic);

    void save(UserNotification userNotification);

    void setCheckedForEvent(Event event);

    //FriendshipNotifications
    void invitation(Friendship friendship);

    void acceptation(Friendship friendship);

    void denial(Friendship friendship);

    void blocking(Friendship friendship);

    void unblocking(Friendship friendship);

    void cancelInvitation(User user, User userTarget);

    void deleteAllWithFriend(User user);

    void remove(User actionUser, User targetUser);

    void notifyAboutInvitingPermission(UserEvent ue);

    void notifyAboutInvitingPermissionRevoke(UserEvent ue);

    void notifyAboutEventInvitation(UserEvent ue);

    void notifyAboutEventInvitationRevoke(UserEvent ue);
}
