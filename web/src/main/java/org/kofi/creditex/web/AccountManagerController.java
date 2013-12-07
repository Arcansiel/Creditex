package org.kofi.creditex.web;

import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@Secured("ROLE_ACCOUNT_MANAGER")
public class AccountManagerController {
    @Autowired
    private UserService userService;
    @RequestMapping("/account_manager/")
    public String MainAccountManager(){
        return "account_manager";
    }
    @RequestMapping(value = "/account_manager/", params = {"first", "last", "patronymic", "series", "number"})
    public String SelectClient(HttpSession session, @RequestParam String first, @RequestParam String last, @RequestParam String patronymic, @RequestParam String series, @RequestParam int number){
        session.setAttribute("client", userService.GetUserByUserDataValues(first, last, patronymic, series, number));
        return "redirect:/account_manager/client/";
    }
    @RequestMapping("/account_manager/client/")
    public String ClientOperationsMain(){
        return "account_manager_client";
    }
    @RequestMapping("/account_manager/client/credit/add/")
    public String ClientShowCreditApplicationForm(){
        return "account_manager_application_credit_edit";
    }
    @RequestMapping("/account_manager/client/prolongation/add/")
    public String ClientShowProlongationApplicationForm(){
        return "account_manager_application_prolongation_edit";
    }
    @RequestMapping("/account_manager/client/prior/add/")
    public String ClientShowPriorApplicationForm(){
        return "account_manager_application_prior_edit";
    }
    @RequestMapping("/account_manager/client/credit/list/")
    public String ClientShowCreditApplicationList(){
        return "account_manager_application_credit_list_view";
    }
    @RequestMapping("/account_manager/client/prior/list/")
    public String ClientShowPriorApplicationList(){
        return "account_manager_application_prior_list_view";
    }
    @RequestMapping("/account_manager/client/prolongation/list/")
    public String ClientShowProlongationApplicationList(){
        return "account_manager_application_prolongation_list_view";
    }
    @RequestMapping("/account_manager/client/change_data/")
    public String ClientShowChangeClientData(HttpSession session){
        session.setAttribute("user_to_change_data", session.getAttribute("client"));
        return "redirect:/change_user_data/";
    }

}
