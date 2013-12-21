package org.kofi.creditex.web;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.Credit;
import org.kofi.creditex.model.User;
import org.kofi.creditex.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/account/credit")
public class AccountCreditController {
    @Autowired
    private CreditService creditService;
    @RequestMapping("/list")
    public String ListCredits(@RequestParam boolean running,HttpSession session, ModelMap model){
        User client = (User)session.getAttribute("client");
        if (client == null)
            return "redirect:/account/";
        List<Credit> credits = creditService.GetCreditsByUserIdAndRunning(client.getId(), running);
        model.put("credits", credits);
        return "account_manager_credit_list";
    }

    @RequestMapping("/{id}/view")
    public String ViewCredit(@PathVariable long id, ModelMap model){
        model.put("credit", creditService.GetCreditById(id));
        return "account_manager_credit_view";
    }

    @RequestMapping("/{id}/contract")
    public String ViewCreditContract(@PathVariable long id, ModelMap model){
        Credit credit = creditService.GetCreditById(id);
        model.put("credit", credit);
        return "account_manager_contract";

    }
}