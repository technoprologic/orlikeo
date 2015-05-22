package umk.zychu.inzynierka.controller.DTObeans;

import java.util.Date;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;

public class DHXCustomEvent extends DHXEvent{

	
	
	public DHXCustomEvent() {
		super();
		this.allow = false;
		this.orlik = 0;
		// TODO Auto-generated constructor stub
	}

	public DHXCustomEvent(Integer id, long orlikId, String start_date, String end_date,
			String text, Boolean allow) {
		super(id, start_date, end_date, text);
		
		// TODO Auto-generated constructor stub
		this.allow = allow;
		this.orlik = orlikId;
	}

	public DHXCustomEvent(String id, long orlikId, Date start_date, Date end_date, String text, Boolean allow) {
		super(id, start_date, end_date, text);
		// TODO Auto-generated constructor stub
		this.allow = allow;
		this.orlik = orlikId;
	}

	public DHXCustomEvent(String id, long orlikId, String start_date, String end_date,
			String text, Boolean allow) {
		super(id, start_date, end_date, text);
		// TODO Auto-generated constructor stub
		this.allow = allow;
		this.orlik = orlikId;
	}

	public DHXCustomEvent(Integer id, long orlikId, Date start_date, Date end_date,
			String text, Boolean allow) {
		super(id, start_date, end_date, text);
		this.allow = allow;
		this.orlik = orlikId;
	}


	public Boolean allow;
	public Boolean getAllow() {
		return allow;
	}
	public void setAllow(Boolean allow) {
		this.allow = allow;
	}
	
	
	//orlikId
	public long orlik;
	public long getOrlik() {
		return orlik;
	}

	public void setOrlik(long orlik) {
		this.orlik = orlik;
	}
	

}
