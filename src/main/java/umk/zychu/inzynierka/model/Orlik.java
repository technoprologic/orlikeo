package umk.zychu.inzynierka.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name="orlik")
public class Orlik extends BaseEntity {
	
	@NotBlank
	@Column(name = "address")
	private String address;
	
	@NotBlank
	@Column(name = "city")
	private String city;
	
	@Column(name = "lights")
	private Boolean lights;
	
	@Column(name = "water")	
	private Boolean water;
	
	@Column(name = "shower")	
	private Boolean shower;
	
	@Column(name = "required_shoes")	
	private String shoes = "turf";
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "orlik", orphanRemoval = true)
	private List<Graphic> graphicCollection;

	@OneToOne
	@JoinColumn(name = "animator_id", unique = true)
	private User animator;

	private Orlik(){}

	private Orlik(Builder builder) {
		super();
		this.address = builder.address;
		this.city = builder.city;
		this.lights = builder.lights;
		this.water = builder.water;
		this.shower = builder.shower;
		this.shoes = builder.shoes;
		this.animator = builder.animator;
		this.graphicCollection = new ArrayList<>();
	}

	public User getAnimator() {
		return animator;
	}

	public void setAnimator(User animator) {
		this.animator = animator;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return address;		
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public String getCity(){
		return city;		
	}	
	
	public void setLights(Boolean lights){
		this.lights = lights;
	}
	
	public Boolean getLights(){
		return lights;		
	}
	
	public void setWater(Boolean water){
		this.water = water;
	}
	
	public Boolean getWater(){
		return water;		
	}

	public void setShower(Boolean shower){
		this.shower = shower;
	}
	
	public Boolean getShower(){
		return shower;		
	}
	
	public void setShoes(String shoes){
		this.shoes = shoes;
	}
	
	public String getShoes(){
		return shoes;		
	}

	public List<Graphic> getGraphicCollection() {
		return graphicCollection;
	}

	public static class Builder {
		private String address;
		private String city;
		private User animator;
		private Boolean lights;
		private Boolean water;
		private Boolean shower;
		private String shoes;

		public Builder(final String address, final String city, final User animator) {
			this.address = address;
			this.city = city;
			this.animator = animator;
			this.shower = Boolean.FALSE;
			this.lights = Boolean.FALSE;
			this.water = Boolean.FALSE;
			this.shoes = "turf";
		}

		public Builder setShower(final Boolean hasShower){
			this.shower = hasShower;
			return this;
		}

		public Builder setLights(final Boolean hasLight){
			this.lights = hasLight;
			return this;
		}

		public Builder setWater(final Boolean hasWater){
			this.water = hasWater;
			return this;
		}

		public Builder setShoes(final String shoes){
			this.shoes = shoes;
			return this;
		}

		public Orlik build(){
			Orlik orlik = new Orlik(this);
			return orlik;
		}
	}

}