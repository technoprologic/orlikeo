package umk.zychu.inzynierka.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emagdnim on 2015-09-12.
 */
@Entity
@Table(name = "user_notifications")
public class UserNotification extends BaseEntity implements Comparable<UserNotification>{

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    private String subject;

    @Column
    private String description;

    @Column
    private String link;

    @Column
    private Boolean checked = false;

    @Column(name = "creation_date")
    private Date notificationDate;

    UserNotification() {
        super();
    }

    private UserNotification(Builder builder){
        this.user = builder.user;
        this.subject = builder.subject;
        this.description = builder.description;
        this.link = builder.link;
        this.checked = builder.checked;
        this.notificationDate = builder.notificationDate;
    }

    public Boolean isChecked(){
        return checked;
    }

    public void setChecked(){
        this.checked = Boolean.TRUE;
    }

    public String getLink() {
        return link;
    }

    public User getUser() {
        return user;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    @Override
    public int compareTo(UserNotification o) {
        if(notificationDate.before(o.getNotificationDate())){
            return 1;
        }else if(notificationDate.after(o.getNotificationDate())){
            return -1;
        }else{
            return 0;
        }
    }

    public static class Builder {
        private User user;
        private String subject;
        private String description;
        private String link = "#";
        private Boolean checked = false;
        private Date notificationDate = new Date();

        public Builder(User user, String subject, String description){
            this.user = user;
            this.subject = subject;
            this.description = description;
        }

        public Builder link(String link){
            this.link = link;
            return this;
        }

        public UserNotification build(){
            UserNotification userNotification = new UserNotification(this);
            return userNotification;
        }
    }
}
