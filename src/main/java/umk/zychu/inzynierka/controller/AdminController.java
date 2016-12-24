package umk.zychu.inzynierka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import umk.zychu.inzynierka.controller.DTObeans.OrlikForm;
import umk.zychu.inzynierka.controller.validator.OrlikFormValidator;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.service.OrlikService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
@SessionAttributes("orlikForm")
public class AdminController {

    @Autowired
    OrlikService orlikService;

    @Autowired
    OrlikFormValidator orlikFormValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(orlikFormValidator);
    }

    @RequestMapping(value = "/orliks", method = RequestMethod.GET)
    public String showOrliks(ModelMap model){
        List<Orlik> orliks = orlikService.findAll();
        model.addAttribute("orliks", orliks);
        return "orliks";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String initForm(@RequestParam(value = "orlikId", required = false) Integer orlikId,
                           @RequestParam(value = "confirm", required=false) Boolean saved,
                           ModelMap model,
                           SessionStatus sessionStatus){

        if(null == orlikId){
            model.addAttribute("orlikForm", new OrlikForm.Builder().build());
            model.addAttribute("creation", "true");
        }else{
            if(null != saved && saved) {
                model.addAttribute("saved", saved);
                model.addAttribute("orlikForm", (OrlikForm) model.get("orlikForm"));
                sessionStatus.setComplete();
            }else {
                Orlik orlik= orlikService.getOrlikById(orlikId);
                model.addAttribute("orlikForm", new OrlikForm.Builder(orlik).build());
            }
        }

        return "orlikEdit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String consumeForm(@ModelAttribute(value = "orlikForm") @Valid OrlikForm form,
                              BindingResult result,
                              RedirectAttributes redirectAtt){
        if(result.hasErrors()) {
            Integer id = form.getId();
            if(id != null) {
                redirectAtt.addAttribute("orlikId", form.getId());
            }
            return "orlikEdit";
        }else {
            form = orlikService.saveOrUpdateOrlik(form);
            redirectAtt.addAttribute("orlikId", form.getId());
            redirectAtt.addAttribute("confirm", true);
        }
        return "redirect:/admin/edit";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String removeOrlik(@RequestParam(value = "orlikRemoveId") Integer id){
        Orlik orlik = orlikService.getOrlikById(id);
        if(orlik != null){
            orlikService.delete(orlik);
        }
        return "redirect:/admin/orliks";
    }

    @ModelAttribute(value="orlikForm")
    public OrlikForm orlikForm(){
        return new OrlikForm.Builder().build();
    }
}
