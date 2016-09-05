package umk.zychu.inzynierka.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "friendship")
public class Friendship extends BaseEntity implements Serializable {

    @ManyToOne()
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    @JsonIgnore
    private User requester;

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "id")
    @JsonIgnore
    private User target;

    @Column(name = "state")
    private Integer state;

    private Friendship(){ super(); }

    private Friendship(final Builder builder) {
        super();
        this.requester = builder.requester;
        this.target = builder.target;
        this.state = builder.state;
    }

    public Friendship changeState(final User userRequester, final User target, final FriendshipType state) {
        this.requester = userRequester;
        this.target = target;
        this.state = state.getValue();
        return this;
    }

    public User getRequester() {
        return requester;
    }

    public User getTarget() {
        return target;
    }

    public FriendshipType getState() {
        return FriendshipType.fromId(state);
    }

    public static class Builder {
        User requester;
        User target;
        Integer state;

        public Builder(final User requester, final User targetUser, final FriendshipType state) {
            this.requester = requester;
            this.target = targetUser;
            this.state = state.getValue();
        }

        public Builder setState(final Integer state) {
            this.state = state;
            return this;
        }

        public Friendship build() {
            Friendship f = new Friendship(this);
            return f;
        }
    }

}