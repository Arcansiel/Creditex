package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.ProlongationApplication;
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

@Slf4j
@Controller
@RequestMapping("/account/prolongation")
public class AccountProlongationController {
    @Autowired
    private ApplicationService applicationService;

    @RequestMapping("/")
    public String CreateApplication(){
        return "account_manager_application_prolongation_edit";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String CreateApplicationProcess(Principal principal, HttpSession session, @Valid @ModelAttribute ProlongationApplication form, BindingResult result){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account";
        if(result.hasErrors()){
            return "redirect:/account/prolongation";
        }
        applicationService.RegisterProlongationApplicationByFormAndUsernameAndAccountManagerName(form, client.getUsername(), principal.getName());
        return "redirect:/account";
    }

    @RequestMapping("/list")
    public String ListProlongations(@RequestParam boolean processed, HttpSession session,ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account";
        model.put("applications", applicationService.GetProlongationApplicationsByClientIdAndProcessed(client.getId(), processed));
        return "account_manager_application_prolongation_list_view";
    }

    @RequestMapping("/{id}/view")
    public String ViewProlongation(@PathVariable long id, ModelMap model){
        model.put("application", applicationService.GetProlongationApplicationById(id));
        return "account_manager_application_prolongation_view";
    }

    @RequestMapping("/{id}/abort")
    public String AbortApplication(@PathVariable long id){
        applicationService.RemoveProlongationApplicationWithId(id);
        return "redirect:/account";
    }

    @RequestMapping("/{id}/process")
    public String ProcessApplication(@PathVariable long id, ModelMap model){
        model.put("id", id);
        return "account_manager_prolongation_processing";
    }

    @RequestMapping("/{id}/register")
    public String RegisterApplication(@PathVariable long id){
        applicationService.FinalizeProlongationApplication(id);
        return "redirect:/account";
    }
}
