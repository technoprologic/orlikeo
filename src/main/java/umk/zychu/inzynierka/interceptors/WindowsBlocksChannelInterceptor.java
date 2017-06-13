package umk.zychu.inzynierka.interceptors;


import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.model.enums.EnumeratedEventRole;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.GUEST;
import static umk.zychu.inzynierka.model.enums.EnumeratedEventRole.ORGANIZER;

@Service
public class WindowsBlocksChannelInterceptor extends BaseChannelInterceptor {

    private static final String DESTINATION = "/blocks/get";
    private Map<String, String> showSessionsPageHeader;

	@SuppressWarnings("rawtypes")
	public WindowsBlocksChannelInterceptor() {
        super(USER_PREFIX + DESTINATION);
        showSessionsPageHeader = new Hashtable<>();
	}

    @Override
    protected void sendToTheUser(String username, String sessionId){
        EnumeratedEventRole role = null;
        String page = null;
        if(showSessionsPageHeader.containsKey(sessionId)){
            page = showSessionsPageHeader.get(sessionId);
        }
        if (page != null) {
            switch (page) {
                case "organized":
                    role = ORGANIZER;
                    break;
                case "invitations":
                    role = GUEST;
                    break;
                default:
                    break;
            }
            List<EventWindowBlock> eventsWindowsBlocks = eventService
                    .getEventWindowBlocks(username, role);
            sessionObject.put("blocks", eventsWindowsBlocks);
            template.convertAndSendToUser(username, DESTINATION, sessionObject);
        }
    }

    @Override
    protected void connect(StompHeaderAccessor sha) {
        super.connect(sha);
        if (sha.toNativeHeaderMap().containsKey("page"))
            showSessionsPageHeader.put(sha.getSessionId(), sha.getFirstNativeHeader("page"));
    }


    @Override
    protected void disconnect(StompHeaderAccessor sha){
        super.disconnect(sha);
        showSessionsPageHeader.remove(sha.getSessionId());
    }

}
