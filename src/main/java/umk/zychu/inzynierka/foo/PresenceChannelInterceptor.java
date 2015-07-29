package umk.zychu.inzynierka.foo;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;

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

import umk.zychu.inzynierka.service.UserService;

@Service
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PresenceChannelInterceptor.class);

	
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private UserService service;

	private TaskScheduler scheduler = new ConcurrentTaskScheduler();

	@SuppressWarnings("rawtypes")
	private Map<String, ScheduledFuture> sessions;
	private Random rand = new Random(System.currentTimeMillis());

	@SuppressWarnings("rawtypes")
	public PresenceChannelInterceptor() {
		// TODO Auto-generated constructor stub
		sessions = new Hashtable<String, ScheduledFuture>();
	}

	private void userSpecificInfo(String username) {
		template.convertAndSendToUser(username, "/topic/dedicated", username
				+ " " + rand.nextInt(10));
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel,
			boolean sent) {

		System.out.println();
		System.out.println();
		System.out.println();

		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
		// ignore non-STOMP messages like heartbeat messages
		if (sha.getCommand() == null) {
			System.out
					.println("ignored non-STOMP messages like heartbeat messages");
			return;
		}

		
		String sessionId = sha.getSessionId();
		System.out.println("session " + " " + sha.getSessionId()
				+ " and COMMAND IS " + sha.getCommand());
		System.out.println("user " + " " + sha.getUser().getName());
		System.out.println("SHA " + " " + sha.toString());

		switch (sha.getCommand()) {
		case CONNECT:
			LOGGER.debug("MY STOMP Connect [sessionId: " + sessionId + "]");
			break;
		case SUBSCRIBE:
			LOGGER.debug("MY STOMP SUBSCRIBE [sessionId: " + sessionId + "]");
			if (sha.getDestination().equals("/user/topic/dedicated")) {
				sessions.put(sessionId,
						scheduler.scheduleAtFixedRate(new Runnable() {
							@Override
							public void run() {
								userSpecificInfo(sha.getUser().getName());
							}
						}, 5000));
			}
			break;
		case CONNECTED:
			LOGGER.debug("MY STOMP Connected [sessionId: " + sessionId + "]");
			System.out.println("MY CONNECTED sessionid: " + " " + sessionId);
			break;
		case DISCONNECT:
			LOGGER.debug("MY STOMP Disconnect [sessionId: " + sessionId + "]");
			System.out.println("MY DISCONNECT sessionid: " + " " + sessionId);
			sessions.remove(sessionId);
			break;
		default:
			System.out.println("MY CANT TAKE AN ACTION");
			break;

		}
		System.out.println();
		System.out.println();
		System.out.println();
	}
}