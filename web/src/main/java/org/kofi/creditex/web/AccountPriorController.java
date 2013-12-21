package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.PriorRepaymentApplication;
import org.kofi.creditex.model.User;
import org.kofi.creditex.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/account/prior")
public class AccountPriorController {
    @Autowired
    private ApplicationService applicationService;
    @RequestMapping("")
    public String CreateApplication(){
        return "account_manager_application_prior_edit";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String CreateApplicationProcess(Principal principal,HttpSession session, @Valid @ModelAttribute PriorRepaymentApplication form, BindingResult result){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account";
        if(result.hasErrors()){
            return "redirect:/account/prior";
        }
        applicationService.RegisterPriorRepaymentApplicationByFormAndUsernameAndAccountManagerName(form, client.getUsername(), principal.getName());
        return "redirect:/account";
    }

    @RequestMapping("/list")
    public String ListPrior(@RequestParam boolean processed, HttpSession session, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account";
        List<PriorRepaymentApplication> applications = applicationService.GetPriorRepaymentApplicationByClientIdAndProcessed(client.getId(), processed);
        model.put("applications", applications);
        return "account_manager_application_prior_list_view";
    }

    @RequestMapping("/{id}/view")
    public String ViewPrior(@PathVariable long id, ModelMap model){
        model.put("application",applicationService.GetPriorRepaymentApplicationById(id));
        return "account_manager_application_prior_view";
    }

    @RequestMapping("/{id}/abort")
    public String AbortApplication(@PathVariable long id){
        applicationService.RemovePriorRepaymentApplicationWithId(id);
        return "redirect:/account";
    }

    @RequestMapping("/{id}/process")
    public String ProcessApplication(@PathVariable long id, ModelMap model){
        model.put("id", id);
        return "account_manager_prior_processing";
    }

    @RequestMapping("/{id}/register")
    public String RegisterApplication(@PathVariable long id){
        applicationService.FinalizePriorRepaymentApplication(id);
        return "redirect:/account";
    }
}
