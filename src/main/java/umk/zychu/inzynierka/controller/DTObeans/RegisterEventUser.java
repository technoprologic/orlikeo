package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.User;

import java.util.Date;

public class RegisterEventUser {

    private Integer userId;
    private Boolean allowed;
    private Boolean invited;
    private String email;
    private String inviter;
    private Date dateOfBirth;
    private String position;

    private RegisterEventUser(Builder builder) {
        super();
        this.userId = builder.userId;
        this.allowed = builder.allowed;
        this.invited = builder.invited;
        this.email = builder.email;
        this.dateOfBirth = builder.dateOfBirth;
        this.position = builder.position;
        this.inviter = builder.inviter;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    public Boolean getAllowed() {
        return this.allowed;
    }

    public void setInvited(Boolean decision) {
        this.invited = decision;
    }

    public Boolean getInvited() {
        return this.invited;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return this.position;
    }

    public static class Builder{

        private Integer userId;
        private String email;
        private Date dateOfBirth;
        private String position;
        private String inviter;
        private Boolean allowed;
        private Boolean invited;

        public Builder(User user){
            this.userId = user.getId();
            this.email = user.getEmail();
            this.dateOfBirth = user.getDateOfBirth();
            this.position = user.getPosition();
            this.inviter = null;
            this.allowed = Boolean.FALSE;
            this.invited = Boolean.FALSE;
        }

        public Builder setInviter(String inviter){
            this.inviter = inviter;
            return this;
        }

        public Builder setAllowed(Boolean allowed){
            this.allowed= allowed;
            return this;
        }

        public Builder setInvited(Boolean invited){
            this.invited = invited;
            return this;
        }

        public RegisterEventUser build(){
            RegisterEventUser registerEventUser = new RegisterEventUser(this);
            return registerEventUser;
        }
    }

}
