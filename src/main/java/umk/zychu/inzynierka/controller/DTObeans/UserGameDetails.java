package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.*;

import java.util.Date;

public class UserGameDetails {

    private Integer eventId;
    private Integer stateId;
    private Integer orlikId;
    private String address;
    private String city;
    private String organizerEmail;
    private Date startDate;
    private Date endDate;
    private Integer decisionId;
    private Integer roleId;
    private Boolean permission;
    private long willCome;
    private Integer playersLimit;
    private long invited;
    private Boolean lights;
    private Boolean water;
    private Boolean shower;
    private String shoes;

    private UserGameDetails(Builder builder) {
        this.eventId = builder.eventId;
        this.stateId = builder.stateId;
        this.orlikId = builder.orlikId;
        this.address = builder.address;
        this.city = builder.city;
        this.organizerEmail = builder.organizerEmail;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.decisionId = builder.decisionId;
        this.roleId = builder.roleId;
        this.permission = builder.permission;
        this.willCome = builder.willCome;
        this.playersLimit = builder.playersLimit;
        this.invited = builder.invited;
        this.lights = builder.lights;
        this.water = builder.water;
        this.shoes = builder.shoes;
        this.shower = builder.shower;
    }

    UserGameDetails() {
        super();
    }

    public Integer getEventId() {
        return eventId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public Integer getOrlikId() {
        return orlikId;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Integer getDecisionId() {
        return decisionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public Boolean getPermission() {
        return permission;
    }

    public long getWillCome() {
        return willCome;
    }

    public Integer getPlayersLimit() {
        return playersLimit;
    }

    public long getInvited() {
        return invited;
    }

    public Boolean getLights() {
        return lights;
    }

    public Boolean getWater() {
        return water;
    }

    public Boolean getShower() {
        return shower;
    }

    public String getShoes() {
        return shoes;
    }

    @Override
    public String toString() {
        return "UserGameDetails [eventId=" + eventId + ", stateId=" + stateId
                + ", orlikId=" + orlikId + ", address=" + address + ", city="
                + city + ", organizerEmail=" + organizerEmail + ", startDate="
                + startDate + ", endDate=" + endDate + ", willCome=" + willCome
                + ", playersLimit=" + playersLimit + ", water=" + water
                + ", shower=" + shower + ", shoes=" + shoes + "]";
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public static class Builder {
        private Integer eventId;
        private Integer stateId;
        private Integer orlikId;
        private String address;
        private String city;
        private String organizerEmail;
        private Date startDate;
        private Date endDate;
        private Integer decisionId;
        private Integer roleId;
        private Boolean permission;
        private long willCome;
        private Integer playersLimit;
        private long invited;
        private Boolean lights;
        private Boolean water;
        private String shoes;
        private Boolean shower;

        public Builder(final Integer eventId) {
            this.eventId = eventId;
            this.stateId = null;
            this.orlikId = null;
            this.address = "Without";
            this.city = "Without";
            this.organizerEmail = "empty@email";
            this.startDate = null;
            this.endDate = null;
            this.decisionId = null;
            this.roleId = null;
            this.permission = null;
            this.willCome = 0;
            this.playersLimit = 12;
            this.invited = 0;
            this.lights = Boolean.FALSE;
            this.water = Boolean.FALSE;
            this.shower = Boolean.FALSE;
        }

        public Builder stateId(final EventState eventState) {
            this.stateId = eventState.getId();
            return this;
        }

        public Builder orlikId(final Integer orlikId) {
            this.orlikId = orlikId;
            return this;
        }

        public Builder address(final String address) {
            this.address = address;
            return this;
        }

        public Builder city(final String city) {
            this.city = city;
            return this;
        }

        public Builder organizerEmail(final String organizerEmail) {
            this.organizerEmail = organizerEmail;
            return this;
        }

        public Builder startDate(final Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(final Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder decision(final Integer orlikId) {
            this.decisionId = null;
            return this;
        }

        public Builder role(final Integer roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder permission(final Boolean permission) {
            this.permission = permission;
            return this;
        }

        public Builder willCome(final long willCome) {
            this.willCome = willCome;
            return this;
        }

        public Builder playersLimit(final Integer playersLimit) {
            this.playersLimit = playersLimit;
            return this;
        }

        public Builder invited(final long invited) {
            this.invited = invited;
            return this;
        }

        public Builder lights(final Boolean lights) {
            this.lights = lights;
            return this;
        }

        public Builder water(final Boolean water) {
            this.water = water;
            return this;
        }

        public Builder shoes(final String shoes) {
            this.shoes = shoes;
            return this;
        }

        public Builder shower(final Boolean shower) {
            this.shower = shower;
            return this;
        }

        public UserGameDetails build() {
            final UserGameDetails ugd = new UserGameDetails(this);
            return ugd;
        }

    }
}
