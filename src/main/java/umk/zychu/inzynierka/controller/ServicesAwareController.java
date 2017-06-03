package umk.zychu.inzynierka.controller;


import org.springframework.beans.factory.annotation.Autowired;
import umk.zychu.inzynierka.service.*;

public class ServicesAwareController {

    @Autowired
    OrlikService orlikService;

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @Autowired
    GraphicService graphicService;

    @Autowired
    UserEventService userEventService;

    @Autowired
    FriendshipService friendshipService;

    @Autowired
    EventToApproveService eventToApproveService;

    @Autowired
    UserNotificationsService userNotificationsService;

}
