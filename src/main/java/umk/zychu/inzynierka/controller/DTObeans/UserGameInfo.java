package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;

public class UserGameInfo {

	Event event;
	
	Graphic graphic;
	
	Orlik orlik;
	
	long goingToCome;
	
	public UserGameInfo(Event event, Graphic graphic, Orlik orlik) {
		this.event = event;
		this.graphic = graphic;
		this.orlik = orlik;
		this.goingToCome = 0;
	}
	
	
	public Graphic getGraphic() {
		return graphic;
	}

	public void setGraphic(Graphic graphic) {
		this.graphic = graphic;
	}

	public Orlik getOrlik() {
		return orlik;
	}

	public void setOrlik(Orlik orlik) {
		this.orlik = orlik;
	}


	
	public long getGoingToCome(){
		return goingToCome;
	}
	
	public Event getEvent(){
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public void setGoingToCome(long goingToCome) {
		this.goingToCome = goingToCome;
	}
}
