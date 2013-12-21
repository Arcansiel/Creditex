package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.Application;
import org.kofi.creditex.model.User;
import org.kofi.creditex.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/account/credit/application")
public class AccountCreditApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @RequestMapping("/")
    public String CreateApplication(){

    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String CreateApplicationProcess(){

    }

    @RequestMapping("/list")
    public String ListApplications(HttpSession session, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account";
        model.put("applications", applicationService.GetApplicationsByUsername(client.getUsername()));
        return "account_manager_application_credit_list_view";
    }

    @RequestMapping("/{id}/view")
    public String ViewApplication(@PathVariable long id, ModelMap model){
        model.put("Application", applicationService.GetCreditApplicationById(id));
        return "account_manager_application_credit_view";
    }

    @RequestMapping("/{id}/abort")
    public String AbortApplication(@PathVariable long id){
        applicationService.RemoveCreditApplicationWithId(id);
        return "redirect:/account";
    }

    @RequestMapping("/{id}/process")
    public String ProcessApplication(@PathVariable long id){

    }

    @RequestMapping("/{id}/register")
    public String RegisterApplication(@PathVariable long id){
        long creditId = applicationService.FinalizeCreditApplication(id);
        return "redirect:/account/credit/"+creditId+"/contract/";
    }
}
