package umk.zychu.inzynierka.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.controller.DTObeans.UserNotificationDTO;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.UserNotificationsService;
import umk.zychu.inzynierka.service.UserService;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

import static umk.zychu.inzynierka.interceptors.BaseChannelInterceptor.CHANNEL_DEBUG;

@Service
public class UserNotificationsChannelInterceptor extends ChannelInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserNotificationsChannelInterceptor.class);

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private UserNotificationsService userNotificationsService;
    @Autowired
    private UserService userService;
    private Map<String, ScheduledFuture> userNotificationsSessions;
    private TaskScheduler scheduler = new ConcurrentTaskScheduler();

    public UserNotificationsChannelInterceptor() {
        userNotificationsSessions = new Hashtable<>();
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel,
                         boolean sent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        // ignore non-STOMP messages like heartbeat messages
        if (sha.getCommand() == null) {
            return;
        }
        String sessionId = sha.getSessionId();
        switch (sha.getCommand()) {
            case CONNECT:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP Connect [sessionId: " + sessionId + "]");
                break;
            case SUBSCRIBE:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP SUBSCRIBE [sessionId: " + sessionId + "]");
                if (sha.getDestination().equals("/user/notifications/dedicated")) {
                    userNotificationsSessions.put(sessionId,
                            scheduler.scheduleWithFixedDelay(new Runnable() {
                                @Override
                                @Transactional
                                public void run() {
                                    sendNotificationToUser(sha.getUser()
                                            .getName());
                                }
                            }, 2000));
                }
                break;
            case CONNECTED:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP Connected [sessionId: " + sessionId + "]");
                break;
            case DISCONNECT:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP Disconnect [sessionId: " + sessionId + "]");
                userNotificationsSessions.remove(sessionId);
                break;
            case SEND:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP SEND [sessionId: " + sessionId + "]");
                if(sha.getDestination().equals("/user/notifications/dedicated")
                        && sha.toNativeHeaderMap().containsKey("checked")){
                        Integer checked = Integer.decode(sha.getNativeHeader("checked").get(0));
                        if(checked == 0){
                            setAllUserNotificationsChecked(sha.getUser().getName());
                        }else{
                            setUserNotificationChecked(sha.getUser().getName(), checked);
                        }
                }
                break;
            default:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP default [sessionId: " + sessionId + "]");
                break;
        }
    }

    private void sendNotificationToUser(String username){
        User user = userService.getUser(username);
        if(user != null) {
            List<UserNotificationDTO> userNotifications = user.getUserNotifications().stream()
                    .filter(un -> !un.isChecked())
                    .map(un -> (new UserNotificationDTO(un)))
                    .collect(Collectors.toList());
            Collections.sort(userNotifications);
            template.convertAndSendToUser(username, "/notifications/dedicated", userNotifications);
        }
    }

    private void setAllUserNotificationsChecked(String username){
        User user = userService.getUser(username);
        user.getUserNotifications().stream()
                .filter(un -> !un.isChecked())
                .forEach(un -> {
            un.setChecked();
            userNotificationsService.save(un);
        });
    }

    private void setUserNotificationChecked(String username, Integer notifyId ){
        User user = userService.getUser(username);
        user.getUserNotifications().stream()
                .filter(un -> un.getId().equals(notifyId))
                .findFirst()
                .ifPresent(un -> {
                    un.setChecked();
                    userNotificationsService.save(un);
                });
    }
}