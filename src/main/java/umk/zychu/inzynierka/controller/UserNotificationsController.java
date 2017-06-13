package umk.zychu.inzynierka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserNotification;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

/**
 * Created by emagdnim on 2015-09-29.
 */
@Controller
@RequestMapping("/notifications")
public class UserNotificationsController extends ServicesAwareController{

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String notifications(ModelMap model, Principal principal){
        User user = userService.getUser(principal.getName());
        List<UserNotification> notifications = user.getUserNotifications();
        notifications.stream()
                .filter(n -> !n.isChecked())
                .forEach(n -> {
                            n.setChecked();
                            userNotificationsService.save(n);
                        }
        );
        Collections.sort(notifications);
        model.addAttribute("notifications", notifications);
        return "notifications";
    }
}
