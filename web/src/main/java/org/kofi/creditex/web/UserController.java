package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.User;
import org.kofi.creditex.model.UserData;
import org.kofi.creditex.service.DayReportService;
import org.kofi.creditex.service.UserService;
import org.kofi.creditex.web.model.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

@Controller
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DayReportService dayReportService;
    @RequestMapping("/change_user_data")
    public String ChangeUserData(ModelMap model, HttpSession session){
        User user = (User) session.getAttribute("user_to_change_data");
        user = userService.GetUserById(user.getId());
        if(user==null)
            return "redirect:/";
        model.put("data", user.getUserData());

        return "change_user_data";
    }
    @RequestMapping(value="/change_user_data", method = RequestMethod.POST)
    public String ChangeUserData(HttpSession session,@Valid @ModelAttribute UserData form, BindingResult result, ModelMap model){
        User user = (User) session.getAttribute("user_to_change_data");
        if(user==null)
            return "redirect:/";
        if(result.hasErrors()){
            model.put("data", user.getUserData());
            UserController.log.warn("Введено неверное значение: {}",result.getFieldErrors());
            model.put("hasError", "Введено неверное значение в одно из полей формы");
            return "change_user_data";
        }
        userService.ChangeUserDataByForm(form);
        return "redirect:/";
    }


    @RequestMapping("/register")
    public String ViewRegisterNewUser(){
        return "registration";
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String RegisterNewUser(ModelMap model,@Valid @ModelAttribute UserRegistrationForm form, BindingResult bindingResult) throws NoSuchAlgorithmException {
        String errorName="isError";
        String errorBinding = "Введено недопустимое значение в одно из числовых полей";
        String errorPassword = "Значения паролей не совпадают";
        String errorExistingUser = "Пользователь с таким именем уже существует";

        if (bindingResult.hasErrors()){
            model.put(errorName,errorBinding);
            return "registration";
        }
        if (!form.getPassword().equals(form.getRepeatPassword())){
            model.put(errorName, errorPassword);
            return "registration";
        }
        if(userService.GetUserByUsername(form.getUsername())!=null){
            model.put(errorName, errorExistingUser);
            return "registration";
        }
        userService.RegisterUserByForm(form);
        switch (form.getRole()){
            case "ROLE_CLIENT":
                dayReportService.IncClient();
                break;
            case "ROLE_ACCOUNT_MANAGER":
                dayReportService.IncAccountManager();
                break;
            case "ROLE_OPERATION_MANAGER":
                dayReportService.IncOperationManager();
                break;
            case "ROLE_SECURITY_MANAGER":
                dayReportService.IncSecurityManager();
                break;
            case "ROLE_COMMITTEE_MANAGER":
                dayReportService.IncCommitteeManager();
                break;
        }

        return "redirect:/";
    }

    @RequestMapping("/change_registration_data/")
    public String ShowChangeRegistrationData(@RequestParam(required = false) Boolean isError,Principal principal, ModelMap model){
        User user = userService.GetUserByUsername(principal.getName());
        model.put("user", user);
        if (isError!=null)
            model.put("isError", true);
        return "change_registration_data";
    }

    @RequestMapping(value = "/change_registration_data/", method = RequestMethod.POST)
    public String ChangeRegistrationData(@Valid @ModelAttribute UserRegistrationForm form, BindingResult result){
        if (result.hasErrors()){
            log.warn("Change registration data errors: {}", result.getFieldErrors());
            return "redirect://change_registration_data?isError=true/";
        }

        if (form.getChangePassword().equals(form.getChangeRepeatPassword()) && form.getChangePassword().length()>8 && form.getChangePassword().length()<46)
            form.setPassword(form.getChangePassword())
                    .setRepeatPassword(form.getRepeatPassword());
        else
            form.setPassword("")
                    .setRepeatPassword("");
        userService.ChangeRegistrationDataByForm(form);
        return "redirect:/j_spring_security_logout";
    }
}
