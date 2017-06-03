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

    public RegisterEventUser(){
        super();
    }

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

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getInviter() {
        return inviter;
    }

    public Integer getUserId() {
        return userId;
    }

    public Boolean getInvited() {
        return this.invited;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return this.position;
    }

    public static class Builder{

        private Boolean allowed;
        private Date dateOfBirth;
        private String email;
        private Boolean invited;
        private String inviter;
        private String position;
        private Integer userId;

        public Builder(User user){
            this.allowed = Boolean.FALSE;
            this.dateOfBirth = user.getDateOfBirth();
            this.email = user.getEmail();
            this.invited = Boolean.FALSE;
            this.inviter = null;
            this.position = user.getPosition();
            this.userId = user.getId();
        }

        public RegisterEventUser build(){
            RegisterEventUser registerEventUser = new RegisterEventUser(this);
            return registerEventUser;
        }

        public Builder setAllowed(Boolean allowed){
            this.allowed= allowed;
            return this;
        }

        public Builder setInvited(Boolean invited){
            this.invited = invited;
            return this;
        }

        public Builder setInviter(String inviter){
            this.inviter = inviter;
            return this;
        }
    }

}
