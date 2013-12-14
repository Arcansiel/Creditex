package org.kofi.creditex.web;

import org.kofi.creditex.model.*;
import org.kofi.creditex.service.ApplicationService;
import org.kofi.creditex.service.CreditService;
import org.kofi.creditex.service.ProductService;
import org.kofi.creditex.service.UserService;
import org.kofi.creditex.web.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@Secured("ROLE_ACCOUNT_MANAGER")
public class AccountManagerController {
    Logger log = LoggerFactory.getLogger(AccountManagerController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private CreditService creditService;
    @Autowired
    private ProductService productService;
    @RequestMapping("/account_manager/")
    public String MainAccountManager(){
        return "account_manager";
    }
    @RequestMapping(value = "/account_manager/process/")
    public String SelectClient(HttpSession session, @ModelAttribute UserData form, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            model.put("isError", "Введено неверное значение в поле номера паспорта");
            return "account_manager";
        }
        log.warn("Form values:"+form.toString());
        session.setAttribute("client", userService.GetUserByUserDataValues(form));
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
    @RequestMapping("/account_manager/client/credit/add/process/")
    public String ClientRegisterCreditApplicationForm(Principal principal,HttpSession session, @ModelAttribute CreditApplicationForm form, BindingResult result){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        if(result.hasErrors()){
            return "redirect:/account_manager/client/credit/add/";
        }
        applicationService.RegisterApplicationByFormAndUsernameAndAccountManagerName(form, client.getUsername(), principal.getName());
        return "redirect:/account_manager/client/";
    }
    @RequestMapping("/account_manager/client/prolongation/add/process/")
    public String ClientRegisterProlongationApplicationForm(Principal principal,HttpSession session, @ModelAttribute ProlongationApplication form, BindingResult result, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        if(result.hasErrors()){
            return "redirect:/account_manager/client/credit/add/";
        }
        applicationService.RegisterProlongationApplicationByFormAndUsernameAndAccountManagerName(form, client.getUsername(), principal.getName());
        return "redirect:/account_manager/client/";
    }
    @RequestMapping("/account_manager/client/prior/add/process/")
    public String ClientRegisterPriorApplicationForm(Principal principal,HttpSession session, @ModelAttribute PriorRepaymentApplication form, BindingResult result){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        if(result.hasErrors()){
            return "redirect:/account_manager/client/credit/add/";
        }
        applicationService.RegisterPriorRepaymentApplicationByFormAndUsernameAndAccountManagerName(form, client.getUsername(), principal.getName());
        return "redirect:/account_manager/client/";
    }

    @RequestMapping("/account_manager/client/credit/list/")
    public String ClientShowCreditApplicationList(HttpSession session,ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        List<Application> applications = applicationService.GetApplicationsByUsername (client.getUsername());
        if (applications==null)
            applications = new ArrayList<Application>();
        model.put("applications", applications);
        return "account_manager_application_credit_list_view";
    }
    @RequestMapping("/account_manager/client/prior/list/")
    public String ClientShowPriorApplicationList(HttpSession session,ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        List<PriorRepaymentApplication> applications = applicationService.GetPriorRepaymentApplicationsByUsername(client.getUsername());
        if (applications==null)
            applications = new ArrayList<PriorRepaymentApplication>();
        model.put("applications", applications);
        return "account_manager_application_prior_list_view";
    }
    @RequestMapping("/account_manager/client/prolongation/list/")
    public String ClientShowProlongationApplicationList(HttpSession session,ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        List<ProlongationApplication> applications = applicationService.GetProlongationApplicationsByUsername(client.getUsername());
        if (applications==null)
            applications = new ArrayList<ProlongationApplication>();
        model.put("applications", applications);
        return "account_manager_application_prolongation_list_view";
    }
    @RequestMapping("/account_manager/client/change_data/")
    public String ClientShowChangeClientData(HttpSession session){
        session.setAttribute("user_to_change_data", session.getAttribute("client"));
        return "redirect:/change_user_data/";
    }
    @RequestMapping("/account_manager/client/credit/view/{id}/")
    public String ClientViewCredit(@PathVariable int id, ModelMap model){
        model.put("credit", creditService.GetCreditById(id));
        return "account_manager_credit_view";
    }
    @RequestMapping("/account_manager/product/view/{id}/")
    public String AccountManagerViewProduct(@PathVariable long id, ModelMap model){
        model.put("product", productService.GetProductById(id));
        return "account_manager_product_view";
    }
}
