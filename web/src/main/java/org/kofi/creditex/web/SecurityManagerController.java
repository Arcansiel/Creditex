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

    @RequestMapping(value = "/security_manager_appliances/", method = RequestMethod.GET)
    public String Security1(Model model){
        List<Application> applications = securityService.GetSecurityApplications();
        model.addAttribute("applications",applications);
        return "security_manager_appliances";
    }

    @RequestMapping(value = "/security_manager_appliances/", method = RequestMethod.POST)
    public String Security5(Model model, HttpSession session
                            ,@RequestParam("id")int id
                            ,@RequestParam("confirmation")int confirmation
                            ,@RequestParam("comment")String comment
    ){
        User security = null;
        if(security != null){
            if(securityService.ConfirmApplication(security.getId(),id,confirmation != 0,comment)){

            }
        }
        return "redirect:/security_manager_appliances/";
    }

    @RequestMapping(value = "/security_manager_credits_expired/", method = RequestMethod.GET)
    public String Security2(Model model){
        List<Credit> credits;
        credits = securityService.GetExpiredCredits(Dates.now());
        model.addAttribute("credits",credits);
        return "security_manager_credits_expired";
    }

    @RequestMapping(value = "/security_manager_credits_unreturned/", method = RequestMethod.GET)
    public String Security3(Model model){
        List<Credit> credits;
        credits = securityService.GetUnreturnedCredits(Dates.now());
        model.addAttribute("credits", credits);
        return "security_manager_credits_unreturned";
    }



    @RequestMapping(value = "/security_manager_appliance_check/{id}", method = RequestMethod.GET)
    public String Security4(Model model
                            ,@PathVariable("id")int id){
        //TODO
        return "security_manager_appliance_check";
    }








    @RequestMapping(value = "/security_manager/6", method = RequestMethod.GET)
    public String Security6(Model model){
        return "security_manager";
    }

    @RequestMapping(value = "/security_manager/7", method = RequestMethod.GET)
    public String Security7(Model model){
        return "security_manager";
    }

    @RequestMapping(value = "/security_manager/8", method = RequestMethod.GET)
    public String Security8(Model model){
        return "security_manager";
    }

    @RequestMapping(value = "/security_manager/9", method = RequestMethod.GET)
    public String Security9(Model model){
        return "security_manager";
    }

    @RequestMapping(value = "/security_manager/10", method = RequestMethod.GET)
    public String Security10(Model model){
        return "security_manager";
    }
}
