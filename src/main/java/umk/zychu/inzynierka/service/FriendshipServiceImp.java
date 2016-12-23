package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.enums.FriendshipType;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.repository.FriendshipDaoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static umk.zychu.inzynierka.model.enums.FriendshipType.*;

@Service
public class FriendshipServiceImp implements FriendshipService {

    @Autowired
    FriendshipDaoRepository friendshipDAO;
    @Autowired
    UserService userService;
    @Autowired
    UserEventService userEventService;
    @Autowired
    OrlikService orlikService;
    @Autowired
    UserNotificationsService userNotificationsService;

    /**
     * Changes friendship status or creates new one.
     *
     * @param email Email of the user for block.
     * @param type  FriendshipType to set.
     */
    @Override
    public void changeFriendshipStatus(final String email, final FriendshipType type){
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        User target = userService.getUser(email);
        boolean statusChanged = false;
        Friendship friendship;
        Optional<Friendship> friendshipOptional = getFriendship(target);
        try {
            friendship = friendshipOptional.isPresent() ? friendshipOptional.get() : null;
            if (friendshipOptional.isPresent()) {
                if(fullFillsPolicies(user, friendship, type)){
                    friendship = friendship.changeState(user, target, type);
                    statusChanged=true;
                }
            } else if(type.equals(INVITE) || type.equals(BLOCK)){
                friendship = new Friendship.Builder(user, target, type)
                        .build();
                statusChanged = true;
            }
            if (statusChanged) {
                friendshipDAO.save(friendship);
                if(type.equals(BLOCK)){
                    removeFromFriendEvents(user, target);
                }
                //todo universal status changed notification
                userNotificationsService.invitation(friendship);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return set of friends (excludes animators).
     *
     * @param requester Boolean value indicates if is creator of actual status.
     * @param state     FriendshipType value of state.
     * @return
     */
    @Override
    public List<User> getFriends(final Boolean requester, final FriendshipType state) {
        final User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        List<User> friends = new ArrayList<>();
        List<Friendship> friendships = new ArrayList<>();
        if(null == requester){
            friendships.addAll(user.getFriendAccepterList());
            friendships.addAll(user.getFriendRequesterList());
            friends.addAll(friendships.stream().
                    filter(f -> f.getState().equals(state))
                    .map(f -> {
                        if (f.getRequester().equals(user)) {
                            return f.getTarget();
                        }
                        return f.getRequester();
                    })
                    .collect(Collectors.toList())
            );
        }else {
            if(requester){
                friends.addAll(user.getFriendRequesterList().stream()
                        .filter(f -> f.getState().equals(state))
                        .map(Friendship::getTarget)
                .collect(Collectors.toList()));
            }else{
                    friends.addAll(user.getFriendAccepterList().stream()
                            .filter(f -> f.getState().equals(state))
                            .map(Friendship::getRequester)
                            .collect(Collectors.toList()));
            }
        }
        return friends.stream().filter(u -> true != orlikService.isOrlikManager(u)).collect(Collectors.toList());
    }

    @Override
    public Optional<Friendship> getFriendship(final User userRequest) {
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Friendship> friendships = new ArrayList<>();
        friendships.addAll(user.getFriendRequesterList());
        friendships.addAll(user.getFriendAccepterList());
       return friendships.stream()
                .filter(f -> (f.getRequester().equals(userRequest)
                        || f.getTarget().equals(userRequest)))
                .findFirst();
    }

    /**
     * Removes users bidirectional from events where they're organizers.
     *
     * @param user User who invoke blocking.
     * @param target User target.
     */
    private void removeFromFriendEvents(final User user, final User target) {
        user.getOrganizedEvents().stream()
                .flatMap(e -> e.getUsersEvent().stream())
                .filter(ue -> ue.getUser().equals(target))
                .forEach(ue -> userEventService.delete(ue)); //todo notify users about cancelled ue
        target.getOrganizedEvents().stream()
                .flatMap(e -> e.getUsersEvent().stream())
                .filter(ue -> ue.getUser().equals(user))
                .forEach(ue -> userEventService.delete(ue));

    }

    private boolean fullFillsPolicies(final User user, final Friendship friendship, final FriendshipType type) {
        FriendshipType state = friendship.getState();
        Boolean madeChange = friendship.getRequester().equals(user);
        if(type == INVITE){
            switch (state){
                case BLOCK   : return madeChange;
                case DECLINE : return true;
                case CANCEL  : return true;
                case REMOVE  : return true;
            }
        }else if(type == ACCEPT){
            switch (state){
                case INVITE  : return !madeChange;
                case DECLINE : return madeChange;
            }
        }else if(type == BLOCK){
            return !(state == BLOCK);
        }else if (type == DECLINE){
            switch (state){
                case INVITE  : return !madeChange;
            }
        }else if(type == CANCEL){
            switch ((state)){
                case INVITE  : return madeChange;
            }
        }else if(type == REMOVE){
            return state == ACCEPT || (state == BLOCK && madeChange);
        }
        return false;
    }

}
