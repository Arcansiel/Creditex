package org.kofi.creditex.web;



import org.kofi.creditex.model.*;
import org.kofi.creditex.service.SecurityService;
import org.kofi.creditex.web.model.ConfirmationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@Secured("ROLE_SECURITY_MANAGER")
public class SecurityManagerController {

    @Autowired
    SecurityService securityService;

    @RequestMapping("/security_manager/")
    public String MainSecurityManager(){
        return "security_manager";
    }

    @RequestMapping(value = "/security_manager/appliances/", method = RequestMethod.GET)
    public String Security1(Model model){
        List<Application> applications = securityService.GetSecurityApplications();
        model.addAttribute("applications",applications);
        return "security_manager_appliances";
    }

    @RequestMapping(value = "/security_manager/credits/expired/", method = RequestMethod.GET)
    public String Security2(Model model){
        List<Credit> credits;
        credits = securityService.GetExpiredCredits();
        model.addAttribute("credits",credits);
        return "security_manger_credits_expired";
    }

    @RequestMapping(value = "/security_manager/credits/unreturned/", method = RequestMethod.GET)
    public String Security3(Model model){
        List<Credit> credits;
        credits = securityService.GetUnreturnedCredits();
        model.addAttribute("credits", credits);
        return "security_manager_credits_unreturned";
    }


    @RequestMapping(value = "/security_manager/appliance/check/{id}", method = RequestMethod.GET)
    public String Security4(Model model
                            ,@PathVariable("id")long id){
        Application app = securityService.GetApplication(id);
        if(app == null){
            return "redirect:/security_manager/?error=no_application&info="+id;
        }
        long client_id = app.getClient().getId();
        model.addAttribute("application",app);
        List<Credit> credits = securityService.GetCurrentClientCredits(client_id);
        model.addAttribute("credits",credits);
        List<Credit> expired = securityService.GetClientExpiredCredits(client_id);
        model.addAttribute("expired",expired);
        long payments_count = securityService.GetClientPaymentsCount(client_id);
        long expired_payments_count = securityService.GetClientExpiredPaymentsCount(client_id);
        model.addAttribute("payments_count", payments_count);
        model.addAttribute("expired_payments_count", expired_payments_count);
        List<Credit> unreturned = securityService.GetClientUnreturnedCredits(client_id);
        model.addAttribute("unreturned",unreturned);
        List<PriorRepaymentApplication> priors = securityService.GetClientPriorRepaymentApplications(client_id);
        model.addAttribute("priors",priors);
        List<ProlongationApplication> prolongations = securityService.GetClientProlongationApplications(client_id);
        model.addAttribute("prolongations",prolongations);
        return "security_manager_appliance_check";
    }

    @RequestMapping(value = "/security_manager/appliance/check/outer/{id}", method = RequestMethod.GET)
    public String Security5(Model model
            ,@PathVariable("id")long id){
        Application app = securityService.GetApplication(id);
        if(app == null){
            return "redirect:/security_manager/?error=no_application&info="+id;
        }
        model.addAttribute("application",app);
        model.addAttribute("result","Информация из внешних источников о клиенте, подавшем заявку на кредит");
        return "security_manager_appliance_check_outer";
    }

    @RequestMapping(value = "/security_manager/appliance/confirm/{id}", method = RequestMethod.POST)
    public String Security6(Principal principal
                            ,@PathVariable("id")long id
                            ,@Valid @ModelAttribute ConfirmationForm form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/security_manager/?error=invalid_input_data";
        }
        int err;
        if((err = securityService.ConfirmApplication(principal.getName(),id,form.isAcceptance(),form.getComment())) == 0){
            return "redirect:/security_manager/?info=confirmation_completed";
        }else{
            return "redirect:/security_manager/?error=confirmation_failed&info="+err;
        }
    }



}
