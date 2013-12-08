package org.kofi.creditex.web;

import org.kofi.creditex.model.User;
import org.kofi.creditex.service.ApplicationService;
import org.kofi.creditex.service.UserService;
import org.kofi.creditex.web.model.ClientSearchForm;
import org.kofi.creditex.web.model.CreditApplicationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Secured("ROLE_ACCOUNT_MANAGER")
public class AccountManagerController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationService applicationService;
    @RequestMapping("/account_manager/")
    public String MainAccountManager(){
        return "account_manager";
    }
    @RequestMapping(value = "/account_manager/", params = {"first", "last", "patronymic", "series", "number"})
    public String SelectClient(HttpSession session, @ModelAttribute ClientSearchForm form, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            model.put("isError", "Введено неверное значение в поле номера паспорта");
            return "account_manager";
        }
        session.setAttribute("client", userService.GetUserByUserDataValues(form.getFirst(), form.getLast(), form.getPatronymic(), form.getSeries(), form.getNumber()));
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
    public String ClientShowCreditApplicationList(HttpSession session,ModelMap model){
        User client = (User)session.getAttribute("client");
        List<CreditApplicationForm> applications = applicationService.GetCreditApplicationsInListByUsername (client.getUsername());
        if (applications==null)
            applications = new ArrayList<CreditApplicationForm>();
        model.put("applications", applications);
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
