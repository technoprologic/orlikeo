package umk.zychu.inzynierka.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.OrlikService;
import umk.zychu.inzynierka.service.UserService;

import java.util.Optional;

/**
 * Created by emagdnim on 2015-10-09.
 */

public class AnimatorOrlikAspect  {

    @Autowired
    UserService userService;
    @Autowired
    OrlikService orlikService;

    @PreAuthorize(value = "hasRole(ROLE_ANIMATOR)")
    @ModelAttribute
    public void addAttributes(Model model) {
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<Orlik> orlikOpt = orlikService.getAnimatorOrlik(user);
        if(orlikOpt.isPresent()){
            model.addAttribute("orlik", orlikOpt.get().getId());
        }
    }
}
