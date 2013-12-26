package org.kofi.creditex.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.*;
import org.kofi.creditex.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private DayReportService dayReportService;

    @RequestMapping("")
    public String Main(@RequestParam(required = false) Boolean change,HttpSession session, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null || change!=null){
            return "account_manager";
        }
        Credit credit = creditService.findByUsernameAndRunning(client.getUsername(), true);
        Application creditApplication = applicationService.GetUnprocessedApplicationByUsername(client.getUsername());
        PriorRepaymentApplication priorRepaymentApplication = applicationService.GetUnprocessedPriorRepaymentApplicationByUsername(client.getUsername());
        ProlongationApplication prolongationApplication = applicationService.GetUnprocessedProlongationApplicationByUsername(client.getUsername());
        Notification notification = notificationService.GetLatestNotViewedNotificationByClientId(client.getId());
        model.put("credit", credit);
        model.put("creditApplication", creditApplication);
        model.put("priorRepaymentApplication", priorRepaymentApplication);
        model.put("prolongationApplication", prolongationApplication);
        model.put("notification", notification);
        return "account_manager_client";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String MainProcess(HttpSession session, @Valid @ModelAttribute UserData form, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            return "redirect:/account";
        }
        User client = userService.GetUserByUserDataValues(form);
        session.setAttribute("client", client);
        return "redirect:/account";
    }

    @RequestMapping("/change")
    public String ChangeUserData(HttpSession session){
        session.setAttribute("user_to_change_data", session.getAttribute("client"));
        return "redirect:/change_user_data/";
    }
}
