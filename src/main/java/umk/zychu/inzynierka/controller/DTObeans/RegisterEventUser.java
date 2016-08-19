package umk.zychu.inzynierka.controller.DTObeans;

import java.util.Date;

public class RegisterEventUser {

    //TODO Builder pattern
    private Integer userId;
    private Boolean allowed;
    private Boolean invited;
    private String email;
    private String inviter;
    private Date dateOfBirth;
    private String position;

    public RegisterEventUser() {
        super();
        // TODO Auto-generated constructor stub
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

    public RegisterEventUser(Integer userId, Boolean hasRight, Boolean decision, String email, Date dateOfBirth, String position, String inviter) {
        this.userId = userId;
        this.allowed = hasRight;
        this.invited = decision;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
        this.inviter = inviter;
    }

}
