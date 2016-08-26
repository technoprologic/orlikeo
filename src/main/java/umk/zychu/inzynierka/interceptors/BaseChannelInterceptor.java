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
import umk.zychu.inzynierka.service.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public abstract class BaseChannelInterceptor extends ChannelInterceptorAdapter {

    protected final Logger LOGGER = LoggerFactory
            .getLogger(BaseChannelInterceptor.class);

    protected static long DELAY = 2000;

    protected static final String USER_PREFIX = "/user";

    protected String subscribeDestination;

    @Autowired
    protected SimpMessagingTemplate template;
    @Autowired
    protected EventService eventService;
    @Autowired
    protected EventStateService eventStateService;
    @Autowired
    protected OrlikService orlikService;
    @Autowired
    protected EventToApproveService eventToApproveService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected UserEventRoleService userEventRoleService;

    BaseChannelInterceptor() {
        super();
    }

    BaseChannelInterceptor(String destination) {
        subscribeDestination = destination;
    }

    protected Map<String, Object> sessionObject = new HashMap<>();
    protected Map<String, ScheduledFuture> sessions = new Hashtable<>();
    private TaskScheduler scheduler = new ConcurrentTaskScheduler();

    @Override
    public void postSend(Message<?> message, MessageChannel channel,
                         boolean sent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        // ignore non-STOMP messages like heartbeat messages
        if (sha.getCommand() == null) {
            nullCommand();
            return;
        }
        switch (sha.getCommand()) {
            case CONNECT:
                connect(sha);
                break;
            case SUBSCRIBE:
                subscribe(sha);
                break;
            case CONNECTED:
                connected(sha);
                break;
            case DISCONNECT:
                disconnect(sha);
                break;
            case SEND:
                send(sha);
                break;
            default:
                default_method();
                break;
        }
    }

    protected void connect(StompHeaderAccessor sha) {
        LOGGER.debug("CONNECT [sessionId: " + sha.getSessionId() + "]");
    }

    private void subscribe(StompHeaderAccessor sha) {
        String sessionId = sha.getSessionId();
        LOGGER.debug("SUBSCRIBE [sessionId: " + sessionId + "]");
        if (sha.getDestination().equals(subscribeDestination)) {
            sessions.put(sessionId,
                    scheduler.scheduleWithFixedDelay(new Runnable() {
                        @Override
                        public void run() {
                            sendToTheUser(sha.getUser()
                                    .getName(), sha.getSessionId());
                        }
                    }, DELAY));
        }
    }

    private void connected(StompHeaderAccessor sha) {
        LOGGER.debug("CONNECTED [sessionId: " + sha.getSessionId() + "]");
    }

    protected void disconnect(StompHeaderAccessor sha) {
        LOGGER.debug("DISCONNECT [sessionId: " + sha.getSessionId() + "]");
        sessions.remove(sha.getSessionId());
    }

    private void send(StompHeaderAccessor sha) {
        LOGGER.debug("SEND [sessionId: " + sha.getSessionId() + "]");
    }

    private void nullCommand() {
        System.out.println("NULL_COMMAND - ignored non-STOMP messages like heartbeat messages");
    }

    private void default_method() {
        System.out.println("MY CANT TAKE AN ACTION");
    }

    // TODO Resolve redundant sessionId paramter
    // TODO (Required only for overriding in WindowsBlockChannelInterceptor.class)
    protected abstract void sendToTheUser(String name, String sessionId);
}
