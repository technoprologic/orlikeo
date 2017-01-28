package umk.zychu.inzynierka.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import umk.zychu.inzynierka.controller.DTObeans.ChangePasswordForm;
import umk.zychu.inzynierka.controller.DTObeans.AccountForm;
import umk.zychu.inzynierka.controller.validator.ChangingPasswordFormValidator;
import umk.zychu.inzynierka.controller.validator.EditAccountFormValidator;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ChangingPasswordFormValidator changingPasswordValidator;

    @Autowired
    private EditAccountFormValidator editAccountFormValidator;

    @InitBinder("changePasswordForm")
    private void initBinde4PasswordForm(WebDataBinder binder) {
        binder.setValidator(changingPasswordValidator);
    }

    @InitBinder("editAccountForm")
    private void initBinder4AccountForm(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setValidator(editAccountFormValidator);
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String changePassword(@ModelAttribute @Valid ChangePasswordForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "password";
        } else {
            userService.changePassword(form);
            return "redirect:/account/profile/";
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editAccount(final @ModelAttribute @Valid AccountForm form, final BindingResult result, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "editAccount";
        } else {
            userService.updateUserDetails(form);
            String redirect = "redirect:/account/profile/";
            redirectAttributes.addFlashAttribute("updated", "updated");
            return redirect;
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editAccount(Map<String, Object> model, Principal principal) {
        User user = userService.getUser(principal.getName());
        AccountForm form = AccountForm.generateForm(user);
        model.put("editAccountForm", form);
        return "editAccount";
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String userPassword(Map<String, Object> model) {
        ChangePasswordForm form = new ChangePasswordForm();
        model.put("changePasswordForm", form);
        return "password";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String userProfile(ModelMap model, Principal principal) {
        User user = userService.getUser(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("self", true);
        return "profile";
    }

    @PreAuthorize(value = "hasRole(ROLE_USER)")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String removeAccount() {
        userService.removeAccount();
        return "redirect:/j_spring_security_logout";
    }
}