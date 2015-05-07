package umk.zychu.inzynierka.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umk.zychu.inzynierka.model.EventX;
import umk.zychu.inzynierka.repository.EventXRepository;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;

@Service
public class EventXServiceImp implements EventXService{

	@Autowired
	private EventXRepository xRepository;
	
	@Override
	public Collection<EventX> getCustomEvents() {
		
		try{
			Collection<EventX> eList = new ArrayList<EventX>();
			eList = xRepository.getXevents();
			return eList;
		}catch(NullPointerException e){
			System.out.println("błąd:       " + e);
		}
		return null;
	}

}
