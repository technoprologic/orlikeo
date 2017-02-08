package umk.zychu.inzynierka.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import umk.zychu.inzynierka.controller.DTObeans.AcceptEventForm;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;

import java.util.Map;
import java.util.Optional;

@Controller
public class JavaplannerController extends ServicesAwareController{

    @RequestMapping("/planner")
    public String planner(ModelMap model)
            throws Exception {
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<Orlik> orlikOpt = orlikService.getAnimatorOrlik(user);
        if (orlikOpt.isPresent()) {
            model.addAttribute("orlik", orlikOpt.get().getId());
        }
        return "javaplanner2";
    }

    @RequestMapping("/pane")
    public String animatorPane(Map<String, Object> model) {
        eventToApproveService.setNotificationsChecked();
        model.put("acceptEventForm", new AcceptEventForm());
        return "animatorPane";
    }

    @RequestMapping(value = "/pane/accept", method = RequestMethod.POST)
    public String acceptEvent(@RequestParam("ev") Integer id) {
        eventService.acceptEvent(id);
        return "redirect:/pane";
    }
}











