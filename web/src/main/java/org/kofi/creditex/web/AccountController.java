package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.*;
import org.kofi.creditex.service.ApplicationService;
import org.kofi.creditex.service.CreditService;
import org.kofi.creditex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private CreditService creditService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String Main(HttpSession session, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null){
            return "account_manager";
        }
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

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String MainProcess(HttpSession session, @Valid @ModelAttribute UserData form, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            return "redirect:/account";
        }
        User client = userService.GetUserByUserDataValues(form);
        session.setAttribute("client", client);
        return "redirect:/account";

    }

}
