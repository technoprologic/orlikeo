package umk.zychu.inzynierka.controller.DTObeans;

import java.util.List;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.UserEvent;

public class CreatedEventDetails {

	Event event;
	
	Orlik orlik;
	
	Graphic graphic;
	
	long invitedPlayers;
	
	
	public long getInvitedPlayers() {
		return invitedPlayers;
	}

	public void setInvitedPlayers(long invitedPlayers) {
		this.invitedPlayers = invitedPlayers;
	}

	public CreatedEventDetails(Event event, Graphic graphic, Orlik orlik) {
		this.event = event;
		this.graphic = graphic;
		this.orlik = orlik;
		this.invitedPlayers = 0;
	}
	
	public void setEvent(Event event){
		this.event = event;
	}
	
	public Event getEvent(){
		return this.event;
	}
	
	
	public void setGraphic(Graphic graphic){
		this.graphic = graphic;
	}
	
	public Graphic getGraphic(){
		return this.graphic;
	}
	
	public void setOrlik(Orlik orlik){
		this.orlik = orlik;
	}
	
	public Orlik getOrlik(){
		return this.orlik;
	}

}
