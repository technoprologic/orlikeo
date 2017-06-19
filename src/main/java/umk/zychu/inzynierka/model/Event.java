package umk.zychu.inzynierka.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import umk.zychu.inzynierka.converter.EventStateConverter;
import umk.zychu.inzynierka.model.enums.EnumeratedEventState;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static umk.zychu.inzynierka.model.enums.EnumeratedEventState.IN_A_BASKET;

@SuppressWarnings("serial")
@Entity
@Table(name = "event")
public class Event extends BaseEntity {

    @Column(name = "players_limit")
    private Integer playersLimit;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserEvent> usersEvent;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "graphic_id", referencedColumnName = "id")
    private Graphic graphic;

    @ManyToOne
    @JoinColumn(name = "user_organizer", referencedColumnName = "id")
    private User userOrganizer;

    @Column(name = "state_id")
    @Convert(converter = EventStateConverter.class)
    private EnumeratedEventState eventState;

    private Event() {
        super();
    }

    private Event(Builder builder) {
        super();
        this.userOrganizer = builder.userOrganizer;
        this.graphic = builder.graphic;
        this.playersLimit = null == builder.playersLimit ? 12 : builder.playersLimit;
        this.creationDate = new Date();
        this.eventState = builder.enumeratedEventState;
    }

    public Integer getPlayersLimit() {
        return playersLimit;
    }

    public void setPlayersLimit(Integer playersLimit) {
        this.playersLimit = playersLimit;
    }

    public List<UserEvent> getUsersEvent() {
        return this.usersEvent;
    }

    public void setUsersEvent(List<UserEvent> usersEvent) {
        this.usersEvent = usersEvent;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Graphic getGraphic() {
        return this.graphic;
    }

    public void setGraphic(Graphic graphic) {
        this.graphic = graphic;
    }

    public User getUserOrganizer() {
        return userOrganizer;
    }

    public void setUserOrganizer(User userOrganizer) {
        this.userOrganizer = userOrganizer;
    }

    public EnumeratedEventState getEventState() {
        return eventState;
    }

    public void setEventState(EnumeratedEventState eventState) {
        this.eventState = eventState;
    }

    public static class Builder {
        private User userOrganizer;
        private Graphic graphic;
        private Integer playersLimit;
        private EnumeratedEventState enumeratedEventState;

        public Builder(final User organizer) {
            this.userOrganizer = organizer;
            this.enumeratedEventState = IN_A_BASKET;
            this.graphic = null;
            this.playersLimit = 12;
        }

        public Builder graphic(final Graphic graphic) {
            this.graphic = graphic;
            return this;
        }

        public Builder playersLimit(final Integer playersLimit) {
            this.playersLimit = playersLimit;
            return this;
        }

        public Builder enumeratedEventState(EnumeratedEventState enumeratedEventState) {
            this.enumeratedEventState = enumeratedEventState;
            return this;
        }

        public Event build() {
            Event event = new Event(this);
            return event;
        }
    }
}
