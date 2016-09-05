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
import umk.zychu.inzynierka.model.EventToApprove;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.*;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

import static umk.zychu.inzynierka.interceptors.BaseChannelInterceptor.CHANNEL_DEBUG;

@Service
public class AnimatorNotificationsRead extends ChannelInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AnimatorNotificationsRead.class);

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private EventToApproveService eventToApproveService;
    @Autowired
    private UserService userService;
    private Map<String, ScheduledFuture> notificationsSessions;
    private TaskScheduler scheduler = new ConcurrentTaskScheduler();

    public AnimatorNotificationsRead() {
        notificationsSessions = new Hashtable<>();
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel,
                         boolean sent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        if (sha.getCommand() == null) {
            if(!CHANNEL_DEBUG) LOGGER.debug("ignored non-STOMP messages like heartbeat messages ");
            return;
        }
        String sessionId = sha.getSessionId();
        switch (sha.getCommand()) {
            case CONNECT:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP Connect [sessionId: " + sessionId + "]");
                break;
            case SUBSCRIBE:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP SUBSCRIBE [sessionId: " + sessionId + "]");
                if (sha.getDestination().equals("/user/notifications/read")) {
                    notificationsSessions.put(sessionId,
                            scheduler.scheduleWithFixedDelay(() -> sendNotificationToManager(sha.getUser()
                                    .getName()), 5000));
                }
                break;
            case CONNECTED:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP Connected [sessionId: " + sessionId + "]");
                break;
            case DISCONNECT:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP Disconnect [sessionId: " + sessionId + "]");
                notificationsSessions.remove(sessionId);
                break;
            case SEND:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP SEND [sessionId: " + sessionId + "]");
                break;
            default:
                if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP default [sessionId: " + sessionId + "]");
                break;
        }
    }

    private void sendNotificationToManager(String username){
        User manager = userService.getUser(username);
        List<EventToApprove> eventsToApprove = eventToApproveService
                .findAll()
                .stream()
                .filter(eta -> eta.getEvent().getGraphic() != null
                        && eta.getEvent().getGraphic().getOrlik().getAnimator() != null
                        && eta.getEvent().getGraphic().getOrlik().getAnimator().equals(manager)
                        && !eta.isChecked())
                .collect(Collectors.toList());
        long counter = eventsToApprove.size();
        template.convertAndSendToUser(username, "/notifications/read", counter);
    }
}