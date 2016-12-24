package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.Orlik;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrlikForm implements Serializable{

    private Integer id;
    private String address;
    private String city;
    private Boolean lights;
    private Boolean water;
    private Boolean shower;
    private String shoes;
    private String animatorEmail;

    Map<String, String> select;

    OrlikForm(){
        super();
    }

    private OrlikForm(Builder builder) {
        super();
        id = builder.id;
        address = builder.address;
        city = builder.city;
        lights = builder.lights;
        water = builder.water;
        shower = builder.shower;
        shoes = builder.shoes;
        animatorEmail = builder.animatorEmail;
        select = builder.select;
    }

    public Map<String, String> getSelect() {
        return select;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnimatorEmail() {
        return animatorEmail;
    }

    public void setAnimatorEmail(String animatorEmail) {
        this.animatorEmail = animatorEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getLights() {
        return lights;
    }

    public void setLights(Boolean lights) {
        this.lights = lights;
    }

    public Boolean getWater() {
        return water;
    }

    public void setWater(Boolean water) {
        this.water = water;
    }

    public Boolean getShower() {
        return shower;
    }

    public void setShower(Boolean shower) {
        this.shower = shower;
    }

    public String getShoes() {
        return shoes;
    }

    public void setShoes(String shoes) {
        this.shoes = shoes;
    }

    public static class Builder{
        private Integer id;
        private String address;
        private String city;
        private String animatorEmail;
        private Boolean lights;
        private Boolean water;
        private Boolean shower;
        private String shoes;

        private Map<String, String> select;

        public Builder(){
            this.id = null;
            this.city = "";
            this.address = "";
            this.animatorEmail = "";
            this.lights = Boolean.FALSE;
            this.water = Boolean.FALSE;
            this.shower = Boolean.FALSE;
            this.shoes = "turf";
            this.select = makeDefaultSelect();
        }

        public Builder(Integer id,
                String address,
                String city,
                String animatorEmail){
            this();
            this.id = id;
            this.city = null != city ? city : this.city;
            this.address = null != address ? address : this.city;
            this.animatorEmail = null != animatorEmail ? animatorEmail  : this.animatorEmail;
        }

        public Builder(Orlik orlik){
            this();
            this.id = orlik.getId();
            this.city = orlik.getCity();
            this.address = orlik.getAddress();
            this.animatorEmail = null != orlik.getAnimator()
                        &&  null != orlik.getAnimator().getEmail() ? orlik.getAnimator().getEmail()  : this.animatorEmail;
            this.lights = orlik.getLights();
            this.water = orlik.getWater();
            this.shower = orlik.getShower();
            this.shoes = orlik.getShoes();
        }

        private static LinkedHashMap<String, String> makeDefaultSelect(){
            LinkedHashMap<String, String> select = new LinkedHashMap<>();
            select.put("false", "NIE");
            select.put("true", "TAK");
            return select;
        }

        public Builder id(Integer id){
            this.id = id;
            return this;
        }

        public Builder city(String city){
            this.city = city;
            return this;
        }

        public Builder address(String address){
            this.address = address;
            return this;
        }

        public Builder animatorEmail(String animatorEmail){
            this.animatorEmail = animatorEmail;
            return this;
        }

        public Builder select(HashMap<String, String> select){
            this.select = select;
            return this;
        }

        public OrlikForm build(){
            OrlikForm form = new OrlikForm(this);
            return form;
        }


    }
}
