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
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.controller.util.EventType;
import umk.zychu.inzynierka.model.enums.EnumeratedEventRole;
import umk.zychu.inzynierka.service.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import static umk.zychu.inzynierka.interceptors.BaseChannelInterceptor.CHANNEL_DEBUG;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.GUEST;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.ORGANIZER;

@Service
public class UserEventsTable extends ChannelInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserEventsTable.class);

    @Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private EventService eventService;

    private Map<String, Object> blockWindowsAndDetailsTableSession = new HashMap<String, Object>();
    private Map<String, ScheduledFuture> showSessions;
    private Map<String, String> showSessionsPageHeader;
    private Map<String, String> showSessionsStateHeader;

    private TaskScheduler scheduler = new ConcurrentTaskScheduler();

	@SuppressWarnings("rawtypes")
	public UserEventsTable() {
		// TODO Auto-generated constructor stub
        showSessions = new Hashtable<>();
        showSessionsPageHeader = new Hashtable<>();
        showSessionsStateHeader = new Hashtable<>();
	}

    private void sendUserBlockWindowsAndDetailsTable(String username, String sessionId){
        EventType eventType = null;
        EnumeratedEventRole role = null;
        String page = null;
        String state = null;
        if(showSessionsPageHeader.containsKey(sessionId)){
            page = showSessionsPageHeader.get(sessionId);
        }
        if(showSessionsStateHeader.containsKey(sessionId)){
            state = showSessionsStateHeader.get(sessionId);
        }
        if (page != null) {
            switch (page) {
                case "organized":
                    eventType = eventType.ORGANIZED;
                    role = ORGANIZER;
                    break;
                case "invitations":
                    eventType = EventType.INVITATIONS;
                    role = GUEST;
                    break;
                default:
                    eventType = null;
                    break;
            }
            Integer stateId = state != null ? Integer.decode(state) : null;
            List<EventWindowBlock> eventsWindowsBlocks = eventService
                    .getEventWindowBlocks(username, role);
            List<UserGameDetails> userGamesDetails = eventService.getGamesDetails(
                    username, eventType, stateId);
            blockWindowsAndDetailsTableSession.put("blocks", eventsWindowsBlocks);
            blockWindowsAndDetailsTableSession.put("detailsTable", userGamesDetails);
            template.convertAndSendToUser(username, "/show/details", blockWindowsAndDetailsTableSession);
        }
    }

	@Override
	public void postSend(Message<?> message, MessageChannel channel,
			boolean sent) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
		// ignore non-STOMP messages like heartbeat messages
		if (sha.getCommand() == null) {
			System.out
					.println("ignored non-STOMP messages like heartbeat messages");
			return;
		}
		String sessionId = sha.getSessionId();
        switch (sha.getCommand()) {
		case CONNECT:
            if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP Connect [sessionId: " + sessionId + "]");
                if (sha.toNativeHeaderMap().containsKey("page"))
                    showSessionsPageHeader.put(sha.getSessionId(), sha.getFirstNativeHeader("page"));
                if (sha.toNativeHeaderMap().containsKey("state"))
                    showSessionsStateHeader.put(sha.getSessionId(), sha.getFirstNativeHeader("state"));
            break;
		case SUBSCRIBE:
            if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP SUBSCRIBE [sessionId: " + sessionId + "]");
            if (sha.getDestination().equals("/user/show/details")) {
                showSessions.put(sessionId,
                        scheduler.scheduleWithFixedDelay(new Runnable() {
                            @Override
                            public void run() {
                                sendUserBlockWindowsAndDetailsTable(sha.getUser()
                                        .getName(), sha.getSessionId());
                            }

                        }, 5000));
            }
			break;
		case CONNECTED:
            if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP Connected [sessionId: " + sessionId + "]");
			break;
		case DISCONNECT:
            if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP Disconnect [sessionId: " + sessionId + "]");
            showSessions.remove(sessionId);
            showSessionsPageHeader.remove(sha.getSessionId());
            showSessionsStateHeader.remove(sha.getSessionId());
			break;
		case SEND:
			break;
		default:
            if(!CHANNEL_DEBUG) LOGGER.debug("MY STOMP default [sessionId: " + sessionId + "]");
			break;
		}
        if(!CHANNEL_DEBUG) debug(sha);
        System.out.println();
    }

    private void debug(StompHeaderAccessor sha){
        System.out.println("SHA: " + sha.toString());
        System.out.println("Page headers: ");
        showSessionsPageHeader.forEach((k, v) -> System.out.println("Page key: " + k + " value: " + v));
        System.out.println("********* page ************");
        System.out.println("State headers: ");
        showSessionsStateHeader.forEach((k, v) -> System.out.println("State key: " + k + " value: " + v));
        System.out.println("********* state ***********");
        System.out.println("Run workers: ");
        showSessions.forEach((k, v) -> System.out.println("Wysyłanie key: " + k + " value: " + v));
        System.out.println("********* run workers ***********");
    }
}
