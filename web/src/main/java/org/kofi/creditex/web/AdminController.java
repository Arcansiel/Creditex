package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.service.DayReportService;
import org.kofi.creditex.service.UserService;
import org.kofi.creditex.web.model.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Secured("ROLE_ADMINISTRATOR")
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private DayReportService dayReportService;

    @RequestMapping("")
    public String MainPage(){
        return "administrator";
    }

    @RequestMapping("/register")
    public String ShowRegisterWorker(){
        return "administrator_register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String ProcessRegisterWorker(Principal principal, @ModelAttribute UserRegistrationForm form, BindingResult result, ModelMap model){
        String errorName="isError";
        String errorBinding = "Введено недопустимое значение в одно из числовых полей";
        String errorPassword = "Значения паролей не совпадают";
        String errorExistingUser = "Пользователь с таким именем уже существует";

        if (result.hasErrors()){
            model.put(errorName,errorBinding);
            return "administrator_register";
        }
        if (!form.getPassword().equals(form.getRepeatPassword())){
            model.put(errorName, errorPassword);
            return "administrator_register";
        }
        if(userService.GetUserByUsername(form.getUsername())!=null){
            model.put(errorName, errorExistingUser);
            return "administrator_register";
        }
        switch (form.getRole()){
            case "ROLE_ACCOUNT_MANAGER":
                dayReportService.IncAccountManager();
                form.setWorkPosition("Специалист по работе с клиентами");
                break;
            case "ROLE_OPERATION_MANAGER":
                dayReportService.IncOperationManager();
                form.setWorkPosition("Операционист");
                break;
            case "ROLE_SECURITY_MANAGER":
                dayReportService.IncSecurityManager();
                form.setWorkPosition("Специалист службы безопасности");
                break;
            case "ROLE_COMMITTEE_MANAGER":
                dayReportService.IncCommitteeManager();
                form.setWorkPosition("Член кредитного комитета");
                break;
            case "ROLE_HEAD":
                form.setWorkPosition("Глава кредитного отдела");
                break;
        }
        userService.RegisterUserByForm(form);
        return "redirect:/admin";
    }
}
