package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.Application;
import org.kofi.creditex.model.Payment;
import org.kofi.creditex.model.User;
import org.kofi.creditex.service.ApplicationService;
import org.kofi.creditex.service.CreditCalculator;
import org.kofi.creditex.service.CreditexDateProvider;
import org.kofi.creditex.service.ProductService;
import org.kofi.creditex.web.model.CreditApplicationRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/account/credit/application")
public class AccountCreditApplicationController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private CreditexDateProvider dateProvider;
    @Autowired
    private ProductService productService;

    @RequestMapping("")
    public String CreateApplication(@RequestParam(required = false) Long productId, ModelMap model){
        if (productId==null){
            model.put("products",productService.GetProductsByActive(true));
            return "account_manager_application_credit_product_select";
        }
        model.put("product", productService.GetProductById(productId));
        return "account_manager_application_credit_edit";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String CreateApplicationProcess(@RequestParam long productId,Principal principal,HttpSession session,@Valid @ModelAttribute CreditApplicationRegistrationForm form, BindingResult result){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account";
        if (result.hasErrors())
            return "redirect:/account/credit/application?productId="+productId;
        form.setProductId(productId);
        if (applicationService.RegisterApplicationByFormAndUsernameAndAccountManagerName(form, client.getUsername(), principal.getName())!=null){
            return "redirect:/account/credit/application?productId="+productId;
        }
        return "redirect:/account";
    }

    @RequestMapping("/list")
    public String ListApplications(@RequestParam boolean processed,HttpSession session, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account";
        model.put("applications", applicationService.GetApplicationsByClientIdAndProcessed(client.getId(), processed));
        return "account_manager_application_credit_list_view";
    }

    @RequestMapping("/{id}/view")
    public String ViewApplication(@PathVariable long id, ModelMap model){
        model.put("application", applicationService.GetCreditApplicationById(id));
        return "account_manager_application_credit_view";
    }

    @RequestMapping("/{id}/abort")
    public String AbortApplication(@PathVariable long id){
        applicationService.RemoveCreditApplicationWithId(id);
        return "redirect:/account";
    }

    @RequestMapping("/{id}/process")
    public String ProcessApplication(@PathVariable long id, ModelMap model){
        Application application = applicationService.GetCreditApplicationById(id);
        long[] mock = new long[3];
        List<Payment> payments = CreditCalculator.PaymentPlan(application, dateProvider.getCurrentSqlDate(), mock);
        model.put("id", application.getId());
        model.put("payments", payments);
        return "account_manager_credit_processing";
    }

    @RequestMapping("/{id}/register")
    public String RegisterApplication(@PathVariable long id){
        long creditId = applicationService.FinalizeCreditApplication(id);
        return "redirect:/account/credit/"+creditId+"/contract";
    }
}
