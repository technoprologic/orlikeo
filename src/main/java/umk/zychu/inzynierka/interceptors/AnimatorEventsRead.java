package umk.zychu.inzynierka.interceptors;

import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;

import java.util.ArrayList;
import java.util.List;


@Service
public class AnimatorEventsRead extends BaseChannelInterceptor {

    private List<UserGameDetails> orlikGamesList = new ArrayList<>();

    private static final String DESTINATION = "/events/read";

    public AnimatorEventsRead() {
        super(USER_PREFIX + DESTINATION);
    }

    @Override
    protected void sendToTheUser(String name, String sessionId) {
        orlikGamesList = orlikService.getAllByManager(name);
        template.convertAndSendToUser(name, DESTINATION, orlikGamesList);
    }
}