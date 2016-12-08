package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.*;

import javax.validation.constraints.NotNull;
import java.util.*;

public class EventWindowBlock {

    private Integer stateId;
    private String address;
    private String city;
    private Date startTime;
    private Date endTime;
    private long goingToCome;
    private Integer playersLimit;
    private long countedInSameState;
    private String label;
    private Integer displayOrder;
    private Boolean incoming;

    private EventWindowBlock(Builder builder) {
        super();
        this.stateId = builder.stateId;
        this.address = builder.address;
        this.city = builder.city;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.playersLimit = builder.playersLimit;
        this.goingToCome = builder.goingToCome;
        this.countedInSameState = builder.countedInSameState;
        this.label = builder.label;
        this.displayOrder = builder.displayOrder;
        this.incoming = builder.incoming;
    }

    private EventWindowBlock() {
        super();
    }

    @Override
    public String toString() {
        return new StringBuffer("i: " + "id"
                + " City:" + city
                + " Address:" + address
                + " WillCome:" + incoming
                + " Limit:" + playersLimit
                + " State:" + stateId
                + " InTheSameStateCounter:" + "haveTheSameState"
                + " displayOrder:" + displayOrder
                + " label:" + label
        ).toString();
    }

    public String getAddress() {
        return address;
    }

    public Integer getStateId() {
        return stateId;
    }

    public String getCity() {
        return city;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public long getGoingToCome() {
        return goingToCome;
    }

    public Integer getPlayersLimit() {
        return playersLimit;
    }

    public long getCountedInSameState() {
        return countedInSameState;
    }

    public String getLabel() {
        return label;
    }

    public Boolean getIncoming() {
        return incoming;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public static class Builder {

        private Integer stateId;
        private String address;
        private String city;
        private Date startTime;
        private Date endTime;
        private long goingToCome;
        private Integer playersLimit;
        private long countedInSameState;
        private String label;
        private Integer displayOrder;
        private Boolean incoming;

        //next 48hrs
        private final Date incomingEventsDateInterval = new Date((new Date()).getTime() + 172400000);

        public Builder(List<UserEvent> userEvents, @NotNull EventState state, UserEventRole role, Boolean incoming, UserDecision decision) {
            this();
            List<UserEvent> filteredUserEvents = new ArrayList<>(userEvents);

            filteredUserEvents.removeIf(ue -> !ue.getEvent().getState().equals(state));
            this.stateId = state.getId();

            if (role != null) {
                filteredUserEvents.removeIf(ue -> !ue.getRole().equals(role));
            }

            if (incoming) {
                Date now = new Date();
                Date endDate = incomingEventsDateInterval;
                Date todayAndTomorrow = new Date(endDate.getYear(),
                        endDate.getMonth(), endDate.getDate());
                filteredUserEvents.removeIf(ue -> null == ue.getEvent().getGraphic() || ue.getEvent().getGraphic()
                        .getStartTime().after(todayAndTomorrow));
            }

            Collections.sort(filteredUserEvents);

            Event event = !filteredUserEvents.isEmpty() ? filteredUserEvents.get(0).getEvent() : null;
            Graphic graphic = event != null ? event.getGraphic() : null;
            Orlik orlik = graphic != null ? graphic.getOrlik() : null;

            this.goingToCome = event != null ? event.getUsersEvent().stream()
                    .filter(ue -> ue.getDecision().equals(decision)).count() : 0;
            this.address = orlik != null ? orlik.getAddress() : this.address;
            this.city = orlik != null ? orlik.getCity() : this.city;
            this.startTime = graphic != null ? graphic.getStartTime() : null;
            this.endTime = graphic != null ? graphic.getEndTime() : null;
            this.playersLimit = event != null ? event.getPlayersLimit() : 0;
            this.countedInSameState = filteredUserEvents.size();

            switch (state.getId()) {
                case 1:
                    this.label = "Kosz";
                    this.displayOrder = 0;
                    break;
                case 2:
                    this.label = "W budowie";
                    this.displayOrder = 1;
                    break;
                case 3:
                    this.label = "Do akceptacji";
                    this.displayOrder = 2;
                    break;
                case 4:
                    this.label = "Zagrożony";
                    this.displayOrder = 3;
                    break;
                case 5:
                    if (!incoming) {
                        this.label = "Przyjęty";
                        this.displayOrder = 4;
                    } else {
                        this.label = "Nadchodzący";
                        this.displayOrder = 5;
                    }
                    break;
                default:
                    break;
            }
        }

        private Builder() {
            this.stateId = 0;
            this.address = "brak orlika";
            this.city = " - ";
            this.startTime = null;
            this.endTime = null;
            this.goingToCome = 0;
            this.playersLimit = 14;
            this.countedInSameState = 0;
            this.label = " - ";
            this.displayOrder = -1;
            this.incoming = Boolean.FALSE;
        }

        public Builder state(Integer stateId) {
            this.stateId = stateId;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder startTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder goingToCome(long goingToCome) {
            this.goingToCome = goingToCome;
            return this;
        }

        public Builder playersLimit(Integer playersLimit) {
            this.playersLimit = playersLimit;
            return this;
        }

        public Builder countedInSameState(long inSameState) {
            this.countedInSameState = inSameState;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder displayOrder(Integer displayOrder) {
            this.displayOrder = displayOrder;
            return this;
        }

        public Builder incoming(Boolean incoming) {
            this.incoming = incoming;
            return this;
        }

        public EventWindowBlock build() {
            EventWindowBlock block = new EventWindowBlock(this);
            return block;
        }
    }

}
