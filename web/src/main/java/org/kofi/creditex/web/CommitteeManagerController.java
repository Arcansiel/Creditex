package org.kofi.creditex.web;


import org.kofi.creditex.model.*;
import org.kofi.creditex.service.CommitteeService;
import org.kofi.creditex.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@Secured("ROLE_COMMITTEE_MANAGER")
public class CommitteeManagerController {

    @Autowired
    SecurityService securityService;

    @Autowired
    CommitteeService committeeService;

    @RequestMapping("/committee_manager/")
    public String MainCommitteeManager(){
        return "committee_manager";
    }

    @RequestMapping(value = {"/committee_manager/appliances/running/","/committee_manager/appliances/"}, method = RequestMethod.GET)
    public String Committee1(Model model){
        List<Application> applications = committeeService.GetVotingApplications(true);
        model.addAttribute("applications",applications);
        return "committee_manager_vote_running_list_view";
    }

    @RequestMapping(value = "/committee_manager/appliances/finished/", method = RequestMethod.GET)
    public String Committee2(Model model){
        List<Application> applications = committeeService.GetVotingApplications(false);
        model.addAttribute("applications",applications);
        return "committee_manager_vote_finished_list_view";
    }

    @RequestMapping(value = "/committee_manager/appliance/{id}", method = RequestMethod.GET)
    public String Committee3(Model model,
                             @PathVariable("id")long id){
        Application application = committeeService.GetApplication(id);
        if(application == null){
            return "redirect:/committee_manager/?error=no_application";
        }
        model.addAttribute("application",application);
        List<Vote> votes = committeeService.GetApplicationVotes(application.getId());
        if(votes != null){
            model.addAttribute("votes",votes);
        }
        if(application.isVotingClosed()){
            return "committee_manager_vote_finished_view";
        }else{
            long client_id = application.getClient().getId();
            List<Credit> credits = committeeService.GetClientCredits(client_id);
            model.addAttribute("credits",credits);
            List<PriorRepaymentApplication> priors =
                    securityService.GetClientPriorRepaymentApplications(client_id);
            model.addAttribute("priors",priors);
            List<ProlongationApplication> prolongations =
                    securityService.GetClientProlongationApplications(client_id);
            model.addAttribute("prolongations",prolongations);
            return "committee_manager_vote_running_view";
        }

    }

    @RequestMapping(value = "/committee_manager/appliance/vote/{id}", method = RequestMethod.POST)
    public String Committee4(Principal principal,
                             @PathVariable("id")long id,
                             @RequestParam("acceptance")boolean acceptance,
                             @RequestParam("comment")String comment){
        int err;
        if((err = committeeService.Vote(principal.getName(),id,acceptance,comment)) != 0){
            return "redirect:/committee_manager/?error=code_"+err;
        }else{
            return "redirect:/committee_manager/";
        }
    }

}
