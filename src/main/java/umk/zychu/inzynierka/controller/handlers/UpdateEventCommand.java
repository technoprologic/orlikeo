package umk.zychu.inzynierka.controller.handlers;

import umk.zychu.inzynierka.controller.DTObeans.EventForm;

/**
 * Created by emag on 08.06.17.
 */
public class UpdateEventCommand implements Command<EventForm> {

    private final EventForm form;

    public UpdateEventCommand(EventForm form){
        super();
        this.form = form;
    }


    @Override
    public EventForm getForm() {
        return this.form;
    }
}
