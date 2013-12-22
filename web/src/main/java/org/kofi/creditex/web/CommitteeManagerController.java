package org.kofi.creditex.web;


import org.kofi.creditex.model.*;
import org.kofi.creditex.service.CommitteeService;
import org.kofi.creditex.service.CreditService;
import org.kofi.creditex.service.SecurityService;
import org.kofi.creditex.service.UserService;
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
@Secured("ROLE_COMMITTEE_MANAGER")
public class CommitteeManagerController {

    @Autowired
    SecurityService securityService;

    @Autowired
    CommitteeService committeeService;

    @Autowired
    UserService userService;

    @Autowired
    CreditService creditService;

    private void AddInfoToModel(Model model, String error, String info){
        if(error != null){
            if(error.equals("no_application")){
                model.addAttribute("error","Заявка не найдена");
                if(info != null){
                    model.addAttribute("info","ID заявки: "+info);
                }
            }else if(error.equals("no_client")){
                model.addAttribute("error","Клиент не найден в системе");
                if(info != null){
                    model.addAttribute("info","ID клиента: "+info);
                }
            }else if(error.equals("invalid_input_data")){
                model.addAttribute("error","Введены некорректные данные");
                if(info != null){
                    model.addAttribute("info","Введены некорректные данные в поле "+info);
                }
            }else if(error.equals("vote_failed")){
                model.addAttribute("error","Ошибка голосования по заявке");
                if(info != null){
                    if(info.equals("-1")){
                        model.addAttribute("info","Сотрудник кредитного комитета отсутствует в системе");
                    }else if(info.equals("-2")){
                        model.addAttribute("info","Заявка не найдена");
                    }else if(info.equals("-3")){
                        model.addAttribute("info","Заявка не находится на стадии рассмотрения кредитным комитетом");
                    }else if(info.equals("-4")){
                        model.addAttribute("info","Голосование по заявке закрыто");
                    }
                }
            }
        }else if(info != null){
            if(info.equals("vote_completed")){
                model.addAttribute("info","Голос по заявке принят");
            }
        }
    }

    @RequestMapping("/committee_manager/")
    public String MainCommitteeManager(Model model
            ,@RequestParam(value = "error", required = false)String error
            ,@RequestParam(value = "info", required = false)String info
    ){
        AddInfoToModel(model,error,info);
        return "committee_manager";
    }

    @RequestMapping(value = {"/committee_manager/appliances/running/","/committee_manager/appliances/"}, method = RequestMethod.GET)
    public String Committee1(Model model){
        List<Application> applications = committeeService.GetApplicationsForVoting();
        model.addAttribute("applications",applications);
        return "committee_manager_vote_running_list_view";
    }

    @RequestMapping(value = "/committee_manager/appliances/finished/", method = RequestMethod.GET)
    public String Committee2(Model model){
        List<Application> applications = committeeService.GetVotingClosedApplications();
        model.addAttribute("applications",applications);
        return "committee_manager_vote_finished_list_view";
    }

    @RequestMapping(value = "/committee_manager/appliance/{id}", method = RequestMethod.GET)
    public String Committee3(Model model,
                             @PathVariable("id")long id){
        Application application = committeeService.GetApplication(id);
        if(application == null){
            return "redirect:/committee_manager/?error=no_application&info="+id;
        }
        model.addAttribute("application",application);
        List<Vote> votes = committeeService.GetApplicationVotes(application.getId());
        if(votes != null){
            model.addAttribute("votes",votes);
        }
        if(application.isVotingClosed()){
            return "committee_manager_vote_finished_view";
        }else{
            return "committee_manager_vote_running_view";
        }

    }

    @RequestMapping(value = "/committee_manager/appliance/vote/{id}", method = RequestMethod.POST)
    public String Committee4(Principal principal,
                             @PathVariable("id")long id
                             ,@Valid @ModelAttribute ConfirmationForm form, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "redirect:/committee_manager/?error=invalid_input_data&info="+bindingResult.getFieldError().getField();
        }
        int err;
        if((err = committeeService.Vote(principal.getName(),id,form.isAcceptance(),form.getComment())) != 0){
            return "redirect:/committee_manager/?error=vote_failed&info="+err;
        }else{
            return "redirect:/committee_manager/?info=vote_completed";
        }
    }


    @RequestMapping(value = "/committee_manager/client/{id}", method = RequestMethod.GET)
    public String Committee5(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/committee_manager/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        long client_id = client.getId();
        long payments_count = securityService.GetClientPaymentsCount(client_id);
        long expired_payments_count = securityService.GetClientExpiredPaymentsCount(client_id);
        model.addAttribute("payments_count", payments_count);
        model.addAttribute("expired_payments_count", expired_payments_count);

        return "committee_manager_client_view";
    }

    @RequestMapping(value = "/committee_manager/client/{id}/credits/all/", method = RequestMethod.GET)
    public String Committee53(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/committee_manager/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("credits",creditService.GetCreditsByUserId(client.getId()));

        return "committee_manager_client_credit_list";
    }

    @RequestMapping(value = "/committee_manager/client/{id}/credits/expired/", method = RequestMethod.GET)
    public String Committee54(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/committee_manager/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("credits",securityService.GetClientExpiredCredits(client.getId()));

        return "committee_manager_client_credit_list";
    }

    @RequestMapping(value = "/committee_manager/client/{id}/prolongations/", method = RequestMethod.GET)
    public String Committee55(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/committee_manager/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("prolongations",securityService.GetClientProlongationApplications(client.getId()));

        return "committee_manager_client_prolongations_list";
    }

    @RequestMapping(value = "/committee_manager/client/{id}/priors/", method = RequestMethod.GET)
    public String Committee56(Model model
            ,@PathVariable("id")long id
    ){
        User client = userService.GetUserById(id);
        if(client == null){
            return "redirect:/committee_manager/?error=no_client&info="+id;
        }
        model.addAttribute("client",client);
        model.addAttribute("priors",securityService.GetClientPriorRepaymentApplications(client.getId()));

        return "committee_manager_client_priors_list";
    }
}
