package umk.zychu.inzynierka.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


@SuppressWarnings("serial")
@Entity
@Table(name="orlik")
public class Orlik extends BaseEntity {
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
	private Collection<GraphicEntity> graphicCollection;
	
	@NotBlank
	@Column(name = "address")
	String address;
	
	@NotBlank
	@Column(name = "city")
	String city;
	
	@Column(name = "lights")
	Boolean lights;
	
	@Column(name = "water")	
	Boolean water;
	
	@Column(name = "shower")	
	Boolean shower;
	
	@Column(name = "required_shoes")	
	String shoes;
	
	public Orlik(){
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
}