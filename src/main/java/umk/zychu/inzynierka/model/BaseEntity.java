package umk.zychu.inzynierka.model;
 
 

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//Integer [-2147483648, +2147483647]
	
	public Integer getId() {
		return id;
	}

	public boolean isNew() {
		return id == null;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
}
