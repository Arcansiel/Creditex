package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.Dates;
import org.kofi.creditex.model.*;
import org.kofi.creditex.service.SecurityService;
import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
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
                            ,@PathVariable("id")int id){
        Application app = securityService.GetApplication(id);
        if(app == null){
            return "redirect:/security_manager/";
        }
        model.addAttribute("application",app);
        List<Credit> credits = securityService.GetCurrentClientCredits(id);
        model.addAttribute("credits",credits);
        List<Credit> expired = securityService.GetClientExpiredCredits(id);
        model.addAttribute("expired",credits);
        List<Credit> unreturned = securityService.GetClientUnreturnedCredits(id);
        model.addAttribute("unreturned",unreturned);
        List<PriorRepaymentApplication> priors = securityService.GetClientPriorRepaymentApplications(id);
        model.addAttribute("priors",priors);
        List<ProlongationApplication> prolongations = securityService.GetClientProlongationApplications(id);
        model.addAttribute("prolongations",prolongations);
        return "security_manager_appliance_check";
    }

    @RequestMapping(value = "/security_manager/appliance/check/outer/{id}", method = RequestMethod.GET)
    public String Security5(Model model
            ,@PathVariable("id")int id){
        Application app = securityService.GetApplication(id);
        if(app == null){
            return "redirect:/security_manager/";
        }
        model.addAttribute("application",app);
        model.addAttribute("result","Информация из внешних источников о клиенте, подавшем заявку на кредит");
        return "security_manager_appliance_check_outer";
    }

    @RequestMapping(value = "/security_manager/appliance/confirm/{id}", method = RequestMethod.POST)
    public String Security6(Model model, Principal principal
                            ,@PathVariable("id")int id
                            ,@RequestParam("confirmation")boolean confirmation
                            ,@RequestParam("comment")String comment
    ){
        securityService.ConfirmApplication(principal.getName(),id,confirmation,comment);
        return "redirect:/security_manager/";
    }



}
