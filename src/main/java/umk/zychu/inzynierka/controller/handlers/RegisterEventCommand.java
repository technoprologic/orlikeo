package umk.zychu.inzynierka.controller.handlers;

import umk.zychu.inzynierka.controller.DTObeans.EventForm;


public class RegisterEventCommand implements Command<EventForm> {

    private EventForm form;

    public RegisterEventCommand(final EventForm form){
        super();
        this.form = form;
    }

    public EventForm getForm(){
        return this.form;
    };
}
