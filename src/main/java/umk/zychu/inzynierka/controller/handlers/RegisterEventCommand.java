package umk.zychu.inzynierka.controller.handlers;

import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;


public class RegisterEventCommand implements Command<RegisterEventForm> {

    private RegisterEventForm form;

    public RegisterEventCommand(final RegisterEventForm form){
        super();
        this.form = form;
    }

    public RegisterEventForm getForm(){
        return this.form;
    };
}
