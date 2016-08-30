package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.FriendshipType;
import umk.zychu.inzynierka.model.User;

import java.util.List;
import java.util.Optional;

public interface FriendshipService {

    void changeFriendshipStatus(final String email, final FriendshipType type);

    List<User> getFriends(final Boolean requester, final FriendshipType state);

    Optional<Friendship> getFriendship(final User userRequest);
}
