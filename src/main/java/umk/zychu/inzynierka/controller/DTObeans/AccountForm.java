package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.User;

import java.util.Date;

public class AccountForm {

    private String name;
    private String surname;
    private Date dateOfBirth;
    private String position;
    private Integer weight;
    private Integer height;
    private String foot;

    public AccountForm() {
        super();
    }

    public static AccountForm generateForm(User user){
        Builder builder = new Builder(user.getName(), user.getSurname())
                .dateOfBirth(user.getDateOfBirth())
                .position(user.getPosition())
                .weight(user.getWeight())
                .height(user
                .getHeight())
                .foot(user.getFoot());
        return new AccountForm(builder);

    }

    private AccountForm(Builder builder) {
        super();
        this.name = builder.name;
        this.surname = builder.surname;
        this.dateOfBirth = builder.dateOfBirth;
        this.position = builder.position;
        this.weight = builder.weight;
        this.height = builder.height;
        this.foot = builder.foot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
    }


    public static class Builder{

        private final String name;
        private final String surname;
        private Date dateOfBirth;
        private String position;
        private Integer weight;
        private Integer height;
        private String foot;

        public Builder(final String name, final String surname) {
            this.name = name;
            this.surname = surname;
            this.dateOfBirth = null;
            this.position = "not set";
            this.weight = 0;
            this.height = 0;
            this.foot = "not set";
        }

        public Builder dateOfBirth(Date dateOfBirth){
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder position(String position){
            this.position = position;
            return this;
        }

        public Builder weight(Integer weight){
            this.weight  = weight;
            return this;
        }

        public Builder height(Integer height){
            this.height = height;
            return this;
        }

        public Builder foot(String foot){
            this.foot = foot;
            return this;
        }

        public AccountForm build(){
            AccountForm form = new AccountForm(this);
            return form;
        }
    }
}
