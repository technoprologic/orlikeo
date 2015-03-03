package umk.zychu.inzynierka.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="test")
public class Test  extends BaseEntity{

	@Column(name="text")
	String txt;
	
	public void setTxt(String txt){
		this.txt = txt;
	}
	
	public String getTxt(){
		return this.txt;
	}
	
	
	public Test(){}
}
