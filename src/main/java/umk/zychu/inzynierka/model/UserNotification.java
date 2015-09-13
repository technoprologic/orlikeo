package umk.zychu.inzynierka.model;

import javax.persistence.*;

/**
 * Created by emagdnim on 2015-09-12.
 */
@Entity
@Table(name = "user_notifications")
public class UserNotification extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @Column
    String subject;

    @Column
    String description;

    @Column
    String link;

    @Column
    Boolean checked = false;

    UserNotification() {
        super();
    }

    public UserNotification(UserEvent ue) {
        super();
        this.user = ue.getUser();
        String descriptionSuffix = "";
        this.subject = "Zaproszenie na wydarzenie";
        if(ue.getInviter() != null && ue.getInviter().equals(ue.getEvent().getUserOrganizer())) {
             descriptionSuffix = " które organizuje";
        }else{
            descriptionSuffix = " którego organizatorem jest " + ue.getEvent().getUserOrganizer().getEmail();
        }
        this.description = "Użytkownik " + ue.getEvent().getUserOrganizer().getEmail()
                + "zaprasza na wydarzenie, " + descriptionSuffix;
        this.link = "/events/details/" + ue.getEvent().getId();
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
