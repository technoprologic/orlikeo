package umk.zychu.inzynierka.model;
 
 

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity  implements Serializable  {

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
