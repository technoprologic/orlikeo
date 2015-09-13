package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserNotification;
import umk.zychu.inzynierka.repository.UserNotificationsDaoRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by emagdnim on 2015-09-12.
 */
@Service
public class UserNotificationsServiceImp implements UserNotificationsService {

    @Autowired
    private UserNotificationsDaoRepository userNotificationsDAO;

    @Override
    public List<UserNotification> findAll() {
        return userNotificationsDAO.findAll();
    }

    @Override
    public List<UserNotification> findAllByUser(User user) {
        return userNotificationsDAO.findAll().stream()
                .filter(un -> un.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserNotification findOne(Integer id) {
        return userNotificationsDAO.findOne(id);
    }

    @Override
    public void save(UserNotification userNotification){
        userNotificationsDAO.save(userNotification);
    }
}
