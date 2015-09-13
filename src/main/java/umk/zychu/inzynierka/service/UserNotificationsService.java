package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserNotification;

import java.util.List;

/**
 * Created by emagdnim on 2015-09-12.
 */
public interface UserNotificationsService {
    List<UserNotification> findAll();
    List<UserNotification> findAllByUser(User user);
    UserNotification findOne(Integer id);
    void save(UserNotification userNotification);
}
