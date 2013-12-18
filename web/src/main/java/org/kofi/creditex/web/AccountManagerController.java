package org.kofi.creditex.web;

import org.joda.time.LocalDate;
import org.kofi.creditex.model.*;
import org.kofi.creditex.service.*;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    @Autowired
    private CreditexDateProvider dateProvider;


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
    public String ClientOperationsMain(HttpSession session, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        Credit credit = creditService.findByUsernameAndRunning(client.getUsername(), true);
        Application creditApplication = applicationService.GetUnprocessedApplicationByUsername(client.getUsername());
        PriorRepaymentApplication priorRepaymentApplication = applicationService.GetUnprocessedPriorRepaymentApplicationByUsername(client.getUsername());
        ProlongationApplication prolongationApplication = applicationService.GetUnprocessedProlongationApplicationByUsername(client.getUsername());
        model.put("credit", credit);
        model.put("creditApplication", creditApplication);
        model.put("priorRepaymentApplication", priorRepaymentApplication);
        model.put("prolongationApplication", prolongationApplication);
        return "account_manager_client";
    }

    @RequestMapping("/account_manager/client/credit/application/add/")
    public String ClientShowCreditApplicationForm(@RequestParam(required = false) Boolean isError,ModelMap model){
        model.put("products", productService.GetProductsByActive(true));
        if (isError!=null)
            model.put("isError", isError);
        return "account_manager_application_credit_edit";
    }

    @RequestMapping("/account_manager/client/credit/application/process/")
    public String ClientProcessCreditApplication(Principal principal,HttpSession session,@Valid @ModelAttribute CreditApplicationRegistrationForm form, BindingResult result){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        if (result.hasErrors())
            return "redirect:/account_manager/client/credit/application/add?isError=true/";
        if (applicationService.RegisterApplicationByFormAndUsernameAndAccountManagerName(form, client.getUsername(), principal.getName())!=null){
            return "redirect:/account_manager/client/credit/application/add?isError=true/";
        }
        return "redirect:/account_manager/client/";
    }

    @RequestMapping("/account_manager/client/credit/application/finalize/")
    public String ClientFinalizeCreditApplication(HttpSession session){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        long creditId = applicationService.FinalizeCreditApplication(client.getUsername());
        return "redirect:/account_manager/credit/"+creditId+"/contract/";
    }

    @RequestMapping("/account_manager/credit/{creditID}/contract/")
    public String ShowCreditContract(@PathVariable long creditID, ModelMap model){
        Credit credit = creditService.GetCreditById(creditID);
        LocalDate date = dateProvider.getCurrentDate();
        model.put("credit", credit);
        model.put("date",date);
        return "account_manager_contract";
    }

    @RequestMapping("/account_manager/client/prior/finalize/")
    public String ClientFinalizePriorRepaymentApplication(HttpSession session){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        applicationService.FinalizePriorRepaymentApplication(client.getUsername());
        return "redirect:/account_manager/client/";
    }

    @RequestMapping("/account_manager/client/credit/process/")
    public String ClientCreditApplicationProcess(HttpSession session,ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        Application application = applicationService.GetUnprocessedApplicationByUsername(client.getUsername());
        long[] mock = new long[3];
        List<Payment> payments = CreditCalculator.PaymentPlan(application, dateProvider.getCurrentSqlDate(), mock);
        model.put("id", application.getId());
        model.put("payments", payments);
        return "account_manager_credit_processing";
    }

    @RequestMapping("/account_manager/client/prolongation/finalize/")
    public String ClientFinalizeProlongationApplication(HttpSession session){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        applicationService.FinalizeProlongationApplication(client.getUsername());
        return "redirect:/account_manager/client/";
    }

    @RequestMapping("/account_manager/client/credit/application/view/")
    public String ClientViewCreditApplication(HttpSession session, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        model.put("application", applicationService.GetUnprocessedApplicationByUsername(client.getUsername()));
        return "account_manager_application_credit_view";
    }

    @RequestMapping("/account_manager/client/credit/application/abort/{id}/")
    public String ClientAbortCreditApplication(@PathVariable long id){
        applicationService.RemoveCreditApplicationWithId(id);
        return "redirect:/account_manager/client/";
    }

    @RequestMapping("/account_manager/client/prolongation/abort/{id}/")
    public String ClientAbortProlongationApplication(@PathVariable long id){
        applicationService.RemoveProlongationApplicationWithId(id);
        return "redirect:/account_manager/client/";
    }

    @RequestMapping("/account_manager/client/prior/abort/{id}/")
    public String ClientAbortPriorRepaymentApplication(@PathVariable long id){
        applicationService.RemovePriorRepaymentApplicationWithId(id);
        return "redirect:/account_manager/client/";
    }

    @RequestMapping("/account_manager/client/prolongation/view/")
    public String ClientViewProlongationApplication(HttpSession session, ModelMap map){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        map.put("application",applicationService.GetUnprocessedProlongationApplicationByUsername(client.getUsername()));
        return "account_manager_application_prolongation_view";
    }

    @RequestMapping("/account_manager/client/prior/view/")
    public String ClientViewPriorApplication(HttpSession session, ModelMap map){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        map.put("application", applicationService.GetUnprocessedPriorRepaymentApplicationByUsername(client.getUsername()));
        return "account_manager_application_prior_view";
    }

    @RequestMapping("/account_manager/client/prolongation/add/")
    public String ClientShowProlongationApplicationForm(){
        return "account_manager_application_prolongation_edit";
    }

    @RequestMapping("/account_manager/client/prior/add/")
    public String ClientShowPriorApplicationForm(){
        return "account_manager_application_prior_edit";
    }

    @RequestMapping("/account_manager/client/prolongation/process/")
    public String ClientRegisterProlongationApplicationForm(Principal principal,HttpSession session, @ModelAttribute ProlongationApplication form, BindingResult result, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        if(result.hasErrors()){
            return "redirect:/account_manager/client/credit/application/add/";
        }
        applicationService.RegisterProlongationApplicationByFormAndUsernameAndAccountManagerName(form, client.getUsername(), principal.getName());
        return "redirect:/account_manager/client/";
    }

    @RequestMapping("/account_manager/client/prior/process/")
    public String ClientRegisterPriorApplicationForm(Principal principal,HttpSession session, @ModelAttribute PriorRepaymentApplication form, BindingResult result){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        if(result.hasErrors()){
            return "redirect:/account_manager/client/credit/application/add/";
        }
        applicationService.RegisterPriorRepaymentApplicationByFormAndUsernameAndAccountManagerName(form, client.getUsername(), principal.getName());
        return "redirect:/account_manager/client/";
    }

    @RequestMapping("/account_manager/client/credit/application/list/")
    public String ClientShowCreditApplicationList(HttpSession session,ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        List<Application> applications = applicationService.GetApplicationsByUsername(client.getUsername());
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

    @RequestMapping("/account_manager/client/credit/list/")
    public String AccountManagerViewCreditList(HttpSession session, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        model.put("credits", creditService.findByUsername(client.getUsername()));
        return "account_manager_credit_list";
    }

    @RequestMapping("/account_manager/product/list/")
    public String AccountManagerViewProductList(HttpSession session, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account_manager/";
        model.put("products",productService.GetProductsByActive(true));
        return "account_manager_product_list";
    }
}