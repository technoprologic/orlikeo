package umk.zychu.inzynierka.interceptors;


import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.model.UserEventRole;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class WindowsBlocksChannelInterceptor extends BaseChannelInterceptor {

    private static final String DESTINATION = "/blocks/get";
    private Map<String, String> showSessionsPageHeader;

	@SuppressWarnings("rawtypes")
	public WindowsBlocksChannelInterceptor() {
        super("/user/blocks/get");
        showSessionsPageHeader = new Hashtable<>();
	}

    @Override
    protected void sendToTheUser(String username, String sessionId){
        UserEventRole userEventRole = null;
        String page = null;
        if(showSessionsPageHeader.containsKey(sessionId)){
            page = showSessionsPageHeader.get(sessionId);
        }
        if (page != null) {
            switch (page) {
                case "organized":
                    userEventRole = userEventRoleService.findOne(1);
                    break;
                case "invitations":
                    userEventRole = userEventRoleService.findOne(2);
                    break;
                default:
                    break;
            }
            List<EventWindowBlock> eventsWindowsBlocks = eventService
                    .getEventWindowBlocks(username, userEventRole);
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
